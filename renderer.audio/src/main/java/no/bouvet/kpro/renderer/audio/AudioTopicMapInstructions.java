package no.bouvet.kpro.renderer.audio;

import java.io.File;
import java.util.*;
import java.net.URI;
import no.bouvet.kpro.model.Media;
import no.bouvet.kpro.model.Event;
import no.bouvet.kpro.model.stigstest.TopicMapEvent;
import no.bouvet.kpro.model.stigstest.TopicMapMedia;
import no.bouvet.kpro.renderer.*;
import no.bouvet.kpro.TopicMapUtil;


/**
 * 
 * @author Michael Stokes
 */
public class AudioTopicMapInstructions extends Instructions {

	//protected Media		_media;
	protected HashMap<String, AudioSource> _sources = new HashMap<String, AudioSource>();

	public AudioTopicMapInstructions(Event mainEvent) {

        List<Event> parts = mainEvent.getChildren();

        TopicMapUtil.sortEventsByTime(parts);

        for ( Event part : parts ) {
            addTopicMapInstruction(part);
        }
	}

    public void close() {
		empty();
	}

	@Override
	public void empty() {
		super.empty();

		for ( AudioSource source : _sources.values() ) {
			source.close();
		}

		_sources.clear();
	}

	protected void addTopicMapInstruction( Event part ) {
        TopicMapEvent tmEvent = (TopicMapEvent) part;
        double startTime = part.getStartTime();
        double stopTime = part.getStartTime() + part.getLength();

        int start		= (int)startTime * 441 / 10;
		int end			= (int)stopTime * 441 / 10;
		//String desc		= part.getDescription();
		int	cue			= 0;
		//int	cue			= (int)( Float.parseFloat( extract( desc, "cue" ) ) * 44100 );
		int	duration	= (int)( part.getLength() * 44100 );

		AudioSource source = findSource(part.getMedia());
		
		AudioInstruction instruction = new AudioInstruction( start, end, source, cue, duration );
		
		if ( tmEvent.getRate() != null )
		{
			float rate = tmEvent.getRate();
			instruction.setConstantRate( rate );
		}
		/* TODO What is this for?
		else
		{
			float rate1 = Float.parseFloat( extract( desc, "rate1" ) );
			float rate2 = Float.parseFloat( extract( desc, "rate2" ) );
			instruction.setInterpolatedRate( rate1, rate2 );
		}*/
		
		if ( tmEvent.getVolume() != null )
		{
			float volume = tmEvent.getVolume();
			instruction.setConstantVolume( volume );
		}/* Todo same suspicious code - possibly for interpolating two songs
		else
		{
			float volume1 = Float.parseFloat( extract( desc, "volume1" ) );
			float volume2 = Float.parseFloat( extract( desc, "volume2" ) );
			instruction.setInterpolatedVolume( volume1, volume2 );
		}*/
		
		append( instruction );
	}

	protected static String extract( String from, String key ) {
		int pos = from.indexOf( key + "=\"" );
		if ( pos < 0 ) return null;
		String value = from.substring( pos + key.length() + 2 );
		pos = value.indexOf( '\"' );
		return value.substring( 0, pos );
	}

	protected AudioSource findSource( Media media )  {
        TopicMapMedia tmMedia = (TopicMapMedia) media;
        try {
            File path = new File(new URI(tmMedia.getSubjectLocator()));

            AudioSource source = _sources.get( path.getPath() );

            if ( source == null ) {
                source = AudioSourceFactory.load( path );
                _sources.put( path.getPath(), source );
            }
            return source;
        } catch (Exception exception)
        {
            throw new RuntimeException("Accessing media failed", exception);
        }
    }
}
