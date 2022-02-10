package no.bouvet.kpro.renderer.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.URL;
import java.nio.ShortBuffer;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MP3SourceTest {
	private MP3Source source;

	@BeforeEach
	public void setUp() throws Exception {
		URL url = getClass().getResource("/test.mp3");
		source = new MP3Source(new File(url.getFile()));
	}

	@AfterEach
	public void tearDown() {
		source.close();
	}

	@Test
	public void testGetDuration() {
		assertEquals(46080, source.getDuration());
	}

	@Test
	public void testGetBuffer() throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		DataOutputStream filter = new DataOutputStream(output);

		for (int time = 0, remaining = source.getDuration(); remaining > 0;) {
			int packet = Math.min(remaining, 1000);
			ShortBuffer input = source.getBuffer(time, packet);

			time += packet;
			remaining -= packet;

			while (packet-- > 0) {
				filter.write(input.get());
				filter.write(input.get());
			}
		}

		String md5 = DigestUtils.md5Hex(output.toByteArray());
		assertEquals("d11e62df06d57b8b24e11770919a0e5e", md5);
	}
}
