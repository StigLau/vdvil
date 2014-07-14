package no.bouvet.kpro.renderer.audio;

import java.io.*;
import java.nio.ShortBuffer;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.Obuffer;
import no.bouvet.kpro.renderer.OldRenderer;
import no.lau.vdvil.cache.FileRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The MP3Source class is an audio source implementation that reads audio data
 * from an MP3 file with the help of the jlayer library. The jlayer library is
 * an LGPL-licensed implementation of the MPEG audio codec.
 * 
 * Unfortunately the jlayer library does not support seeking to an arbitrary
 * point in the file: one can only decode from beginning to end. Seeking is
 * sometimes be approximated by linearly interpolating a byte position within
 * the source file, however this is not accurate enough for our purposes. The
 * AudioSource interface requiers that the audio source be able to deliver a
 * range of samples from a precise start time to a precise end time, on demand.
 * 
 * To meet this need, the MP3Source class first scans the MP3 file and builds an
 * MPEG frame index. Using this index it is able to seek precisely to a frame,
 * and then begin decoding from that point. Again, unfortunately jlayer does not
 * support any kind of flush functionality, so a new decoder instance is
 * required whenever a seek is performed. Creating a decoder instance is a
 * relatively heavy-weight operation, so some tricks are used to avoid it
 * wherever possible. Typically the MP3Source can reuse an existing decoder
 * instance by maintaining and shifting an internal sample cache in such a way
 * as to allow a new decode operation to continue where the previous one left
 * off. This internal sample cache also allows the MP3Source to more efficiently
 * meet incoming buffer requests that overlap.
 * 
 * This source only supports mono and stereo MPEG audio files with a sample rate
 * of 44100 Hz.
 * 
 * @author Michael Stokes
 */
public class MP3Source implements AudioSource {
	/**
	 * Some constant tables used for decoding MPEG audio frames
	 */
	protected final static int[] FREQUENCIES = new int[] { 11025, 12000, 8000,
			0, 0, 0, 0, 0, 22050, 24000, 16000, 0, 44100, 48000, 32000, 0 };
	protected final static int[] BITRATES = new int[] { 0, 0, 0, 0, 0, 32, 32,
			32, 32, 8, 64, 48, 40, 48, 16, 96, 56, 48, 56, 24, 128, 64, 56, 64,
			32, 160, 80, 64, 80, 40, 192, 96, 80, 96, 48, 224, 112, 96, 112,
			56, 256, 128, 112, 128, 64, 288, 160, 128, 144, 80, 320, 192, 160,
			160, 96, 352, 224, 192, 176, 112, 384, 256, 224, 192, 128, 416,
			320, 256, 224, 144, 448, 384, 320, 256, 160, 0, 0, 0, 0, 0 };

	protected File _file;
	protected RandomAccessFile _raf;

	protected int[] _frames;
	protected int _frameSize;
	protected int _frameCount;
	protected boolean _stereo = false;

	protected final static int BUFFER_DURATION = 5 * OldRenderer.RATE;
	protected ShortBuffer _buffer = ShortBuffer.allocate(BUFFER_DURATION * 2);
	protected MP3Output _output = new MP3Output(_buffer);
	protected int _bufferFrame = 0;
	protected int _bufferSize = 0;

	protected Decoder _decoder;
	protected Bitstream _bitstream;
	protected int _nextFrame;
    Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Constructs a new MP3Source, reading from file. The file will be opened
	 * immediately, and the MPEG frame index built. If the source is unable to
	 * understand the file, or if the file is not 44100 Hz, an exception will be
	 * thrown.
	 * 
	 * @param file
	 *            The file to be opened
	 * @throws IOException
	 *             if the file could not be opened, not be understood, or was of
	 *             the wrong format
	 * @author Michael Stokes
	 */
	public MP3Source(File file) throws IOException {
		log.debug("Opening " + file.toString() );

		// Open the file

		_file = file;
		_raf = new RandomAccessFile(file, "r");

		// Index the file, or throw an exception

		if (!index()) {
			close();
			throw new IOException("Not an MP3");
		}

		// Decode the first frame to make sure everything is working, or throw
		// an exception

		if (!decodeFrames(0, 1)) {
			close();
			throw new IOException("Not an MP3");
		}
	}

    public MP3Source(FileRepresentation fileRepresentation) throws IOException {
        this(fileRepresentation.localStorage());
    }

    /**
	 * Close the source and the underlying file.
	 * 
	 * @author Michael Stokes
	 */
	public void close() {
		try {
			// Close the bitstream

			if (_bitstream != null)
				_bitstream.close();

			// Close the file

			_raf.close();

			log.debug( "Closed " + _file.toString() );
		} catch (Exception e) {
			log.debug( "Exception closing: " + e.toString() );
		}
	}

	/**
	 * Gets the duration of the source audio, in number of samples. In this case
	 * it is equal to the number of frames times the number of samples per
	 * frame.
	 * 
	 * @return The duration of the audio source, in number of samples
	 * @author Michael Stokes
	 */
	public int getDuration() {
		return _frameCount * _frameSize;
	}

	/**
	 * Asks the audio source to decode or otherwise make available a period of
	 * audio data, starting at time and extending for duration samples. The
	 * source returns 16-bit stereo audio data in a ShortBuffer object. Each
	 * sample consists of two signed shorts. The current position of the
	 * ShortBuffer object should point to the first requested audio sample (the
	 * one identified by time), and may not necessarily fall at the start of the
	 * ShortBuffer. It is recommended that Source implementations exercise an
	 * audio caching scheme to avoid decoding the same data twice.
	 * 
	 * @param time
	 *            The first audio sample to make available, in samples
	 * @param duration
	 *            The number of audio samples to make available, in samples
	 * @return A ShortBuffer object that contains the requested audio data, with
	 *         the current position set to the first requested sample
	 * @author Michael Stokes
	 */
	public ShortBuffer getBuffer(int time, int duration) {
		// Convert the time period into a frame period (first and last)

		int reqFrame = time / _frameSize;
		int reqLast = (time + duration - 1) / _frameSize;

		// Check if the requested frame period is partially or completely
		// outside of the
		// cached frame period

		if ((reqFrame < _bufferFrame)
				|| (reqLast >= _bufferFrame + _bufferSize)) {
			// Check if there is any intersection between the requested period
			// and the cached period

			if ((reqFrame > _bufferFrame)
					&& (reqFrame < _bufferFrame + _bufferSize)) {
				// The end of the cached period is usable, so compact it back to
				// the start

				_buffer.position((reqFrame - _bufferFrame) * _frameSize * 2);
				_buffer.limit(_bufferSize * _frameSize * 2);
				_buffer.compact();
				_buffer.limit(BUFFER_DURATION * 2);

				// Update the cached buffer period

				_bufferSize = (_bufferFrame + _bufferSize) - reqFrame;
				_bufferFrame = reqFrame;
			} else {
				// There is no usable data in the cache, so empty it

				_bufferFrame = reqFrame;
				_bufferSize = 0;
			}

			// Calculate the frames to decode into the cache buffer

			int decodeFrame = _bufferFrame + _bufferSize;
			int decodeFrames = (BUFFER_DURATION / _frameSize) - _bufferSize;

			if ((decodeFrame + decodeFrames) > _frameCount)
				decodeFrames = (_frameCount - decodeFrame);

			if (decodeFrames > 0) {
				// Decode the frames and update the cache buffer period

				decodeFrames(decodeFrame, decodeFrames);
				_bufferSize += decodeFrames;
			}
		}

		// Position the buffer at the appropriate point within the cache

		_buffer.position((time - (_bufferFrame * _frameSize)) * 2);

		// Return the buffer

		return _buffer;
	}

	/**
	 * Build an index of the MPEG audio frames in the file.
	 * 
	 * @return true if the file was indexed successfully
	 * @author Michael Stokes
	 */
	protected boolean index() {
		// Work with 8K blocks for speed

		byte[] buffer = new byte[8192];

		int offset = 0; // Position in buffer
		int available = 0; // Bytes in buffer
		int header = 0; // Current sliding sync word

		boolean sync = false; // Not in sync

		// Initialize the index

		_frames = null;
		_frameSize = 0;
		_frameCount = 0;

		// Catch I/O exceptions

		try {
			// Start scanning the file with a virtual position of -3, because 3
			// bytes will be
			// required before sync word acquisition

			for (int position = -3, skip = 0;; position++) {
				// If the buffer is empty, fill it

				if (offset >= available) {
					offset = 0;
					available = _raf.read(buffer);

					if (available < 1)
						break;
				}

				// Slide a new byte into the sync word

				header = (header << 8) | (buffer[offset++] & 0xFF);

				if (skip > 0) {
					// The scanner is numb
					skip--;
					continue;
				} else if ((header & 0xFFE00000) != 0xFFE00000) {
					if (0 == position) {
						if ((header & 0xFFFFFF00) == 0x49443300) {
							if ((available - offset) >= 6) {
								int b1 = buffer[offset + 2] & 127;
								int b2 = buffer[offset + 3] & 127;
								int b3 = buffer[offset + 4] & 127;
								int b4 = buffer[offset + 5] & 127;
								skip = (b1 << 21) | (b2 << 14) | (b3 << 7) | b4;
							}
						} else if ((header & 0xFFFFFEEE) == 0x4D4A53A9) { //TODO should perhaps be .equals
							if ((available - offset) >= 4) {
								int b1 = buffer[offset + 0] & 255;
								int b2 = buffer[offset + 1] & 255;
								int b3 = buffer[offset + 2] & 255;
								int b4 = buffer[offset + 3] & 255;
								skip = (b1 << 24) | (b2 << 16) | (b3 << 8) | b4;
							}
						}
					} else if (sync && (position > 0)) {
						sync = false;
					}

					continue;
				}

				// OK, the sync word is valid, lets decode the MPEG frame header

				int version = ((header & 0x00180000) >>> 19);
				int layer = ((header & 0x00060000) >>> 17);
				int bitIndex = ((header & 0x0000F000) >>> 12);
				int freqIndex = ((header & 0x00000C00) >>> 10);
				int channels = ((header & 0x000000C0) >>> 6);
				int padding = (0 != (header & 0x0200)) ? 1 : 0;
				int bitColumn = 0;

				if (version == 3) {
					switch (layer) {
					case 1:
						bitColumn = 2;
						break;
					case 2:
						bitColumn = 1;
						break;
					case 3:
						bitColumn = 0;
						break;
					}
				} else {
					bitColumn = (layer == 3) ? 3 : 4;
				}

				int bitrate = BITRATES[(bitIndex * 5 + bitColumn)
						% BITRATES.length] * 1000;
				int frequency = FREQUENCIES[(freqIndex & 3) + (version & 3) * 4];
				int samples = (layer == 3) ? 384 : 1152;

				if (frequency < 1)
					continue;
				int size = (layer == 3) ? (12 * bitrate / frequency + padding) * 4
						: (144 * bitrate / frequency + padding);
				if (size < 0)
					continue;

				// Make sure the frequency is what we want

				if (frequency != OldRenderer.RATE) {
					log.debug( "File is " + frequency + " Hz, this source is " + OldRenderer.RATE + " Hz only, falling back to default" );
					return false;
				}

				// Detect stereo

				if (channels != 3)
					_stereo = true;

				// Acquire and lock the frame size (number of samples per frame)

				if (0 == _frameSize) {
					_frameSize = samples;
				} else if (_frameSize != samples) {
					log.debug("File is inconsistent" );
					return false;
				}

				// Grow the frame index

				if ((_frames == null) || (_frameCount >= _frames.length)) {
					int[] frames = new int[_frameCount + 1024];
					if (_frames != null)
						System.arraycopy(_frames, 0, frames, 0, _frameCount);
					_frames = frames;
				}

				// Append to the frame index

				_frames[_frameCount++] = position;

				// Numb the scanner until the end of the frame payload

				skip = size - 1;
				sync = true;
			}
		} catch (Exception e) {
			log.debug("Exception indexing: " + e.toString() );
			return false;
		}

		log.debug("We will require a minimum of 10 index frames before we decide that the file is valid");

		if (_frameCount > 10) {
			// Notify the user
			float seconds = (float)( _frameCount * _frameSize ) / OldRenderer.RATE;
			log.debug( "Indexed " + _frameCount + " frames, duration is " + seconds + "s" );
			return true;
		} else {
			log.debug( "No MP3 frames detected" );
			return false;
		}
	}

	/**
	 * Decode one or more frames from the file, using jlayer.
	 * 
	 * @param frame
	 *            the first frame to decode
	 * @param frames
	 *            the number of frames to decode
	 * @return true if the frames were decoded successfully
	 * @author Michael Stokes
	 */
	protected boolean decodeFrames(int frame, int frames) {
		// Catch decoding exceptions

		try {
			// If there is no decoder yet, or if we need to decode a frame that
			// is not the
			// next frame that the existing decoder is expecting to decode, then
			// we will need
			// to create a new decoder.

			if ((_decoder == null) || (frame != _nextFrame)) {
				// Close the previous bitstream reader

				if (_bitstream != null)
					_bitstream.close();

				_decoder = null;
				_bitstream = null;

				// Seek the file to the location indicated by the MPEG audio
				// frame index

				_raf.seek(_frames[_nextFrame = frame]);

				// Create a new bitstream reader and decoder

				_bitstream = new Bitstream(new MP3Input(_raf));
				_decoder = new Decoder();
				_decoder.setOutputBuffer(_output);
			}

			// Calculate the output position in the sample cache buffer

			int offset = _bufferSize * _frameSize;

			// Decode the frames

			for (; frames > 0; frames--) {
				// Read the next MPEG audio header

				Header header = _bitstream.readFrame();
				if (header == null)
					break;

				// Decode the audio frame

				_output.setOffset(offset);

				if (null == _decoder.decodeFrame(header, _bitstream))
					break;

				_bitstream.closeFrame();

				// Increment the output offset and the next frame pointer

				offset += _frameSize;
				_nextFrame++;
			}

			// Check if there was a decode error

			if (frames > 0) {
				log.debug("A frame did not decode" );
				_nextFrame = -1;
			}

			return (_nextFrame != frame);
		} catch (Exception e) {
			log.debug("Exception decoding: " + e.toString() );
		}

		return false;
	}

	/**
	 * The MP3Input class implements an InputStream that reads from the current
	 * position in a RandomAccessFile.
	 * 
	 * @author Michael Stokes
	 */
	protected class MP3Input extends InputStream {
		protected RandomAccessFile _raf;

		/**
		 * Construct a new MP3Input
		 * 
		 * @param raf
		 *            The RandomAccessFile to read from
		 * @author Michael Stokes
		 */
		public MP3Input(RandomAccessFile raf) {
			_raf = raf;
		}

		/**
		 * Read a byte from the file.
		 * 
		 * @returns The byte that was read
		 * @author Michael Stokes
		 */
		@Override
		public int read() throws IOException {
			return _raf.read();
		}

		/**
		 * Read into a byte array from the file.
		 * 
		 * @param b
		 *            The byte array to read into
		 * @returns The number of bytes read
		 * @author Michael Stokes
		 */
		@Override
		public int read(byte[] b) throws IOException {
			return _raf.read(b);
		}

		/**
		 * Read into a range of a byte array from the file.
		 * 
		 * @param b
		 *            The byte array to read into
		 * @param off
		 *            The first array index to read into
		 * @param len
		 *            The number of bytes to read
		 * @returns The number of bytes read
		 * @author Michael Stokes
		 */
		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			return _raf.read(b, off, len);
		}
	}

	/**
	 * The MP3Output class extends jlayer's Obuffer class to provide a decoded
	 * audio sync that writes to the MP3Source's sample cache.
	 * 
	 * @author Michael Stokes
	 */
	protected class MP3Output extends Obuffer {
		protected ShortBuffer _target;
		protected int _offset1;
		protected int _offset2;

		/**
		 * Create a new MP3Output that writes to the given ShortBuffer.
		 * 
		 * @param target
		 *            The ShortBuffer to write to
		 * @author Michael Stokes
		 */
		public MP3Output(ShortBuffer target) {
			_target = target;
		}

		/**
		 * Set the offset (in samples) within the output buffer where new
		 * samples should be written.
		 * 
		 * @param offset
		 *            The offset, in number of samples, where new samples should
		 *            be written
		 * @author Michael Stokes
		 */
		public void setOffset(int offset) {
			_offset1 = offset * 2;
			_offset2 = _offset1 + 1;
		}

		/**
		 * Append a new sample from the given channel.
		 * 
		 * @param channel
		 *            The channel number (0 for left, 1 for right)
		 * @param value
		 *            The 16-bit sample value
		 * @author Michael Stokes
		 */
		@Override
		public void append(int channel, short value) {
			switch (channel) {
			case 0:
				// Store the sample at the left-channel offset
				_target.put(_offset1, value);
				// If mono, store the sample at the corresponding right-channel
				// offset too
				if (!_stereo)
					_target.put(_offset1 + 1, value);
				// Increment the left-channel offset
				_offset1 += 2;
				break;

			case 1:
				// Store the sample at the right-channel offset
				_target.put(_offset2, value);
				// Increment the right-channel offset
				_offset2 += 2;
				break;
			}
		}

		@Override
		public void clear_buffer() {
		}

		@Override
		public void close() {
		}

		@Override
		public void set_stop_flag() {
		}

		@Override
		public void write_buffer(int blah) {
		}
	}
}
