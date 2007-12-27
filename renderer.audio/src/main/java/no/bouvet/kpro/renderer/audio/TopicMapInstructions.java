package no.bouvet.kpro.renderer.audio;

import java.io.File;
import java.util.*;
import no.bouvet.kpro.model.stigstest.Media;
import no.bouvet.kpro.model.stigstest.Event;
import no.bouvet.kpro.renderer.*;


/**
 * 
 * @author Michael Stokes
 */
public class TopicMapInstructions extends Instructions {
	
	protected Media		_media;
	protected HashMap<String, AudioSource> _sources = new HashMap<String, AudioSource>();
	
	public TopicMapInstructions( Event mainEvent) throws Exception {
		
        List<Event> parts = mainEvent.getChildren();

        Collections.sort( parts, new Comparator<Event>() {
			public int compare( Event a, Event b) {
                return (int) (a.getStartTime() - b.getStartTime());
            }  } );
		
		for ( Event part : parts ) {
            addTopicMapInstruction( part );
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
	
	protected void addTopicMapInstruction( Event part ) throws Exception {

        double startTime = part.getStartTime();
        double stopTime = part.getStartTime() + part.getLength();

        int start		= (int)startTime * 441 / 10;
		int end			= (int)stopTime * 441 / 10;
		String desc		= part.getDescription();
		int	cue			= 0;
		//int	cue			= (int)( Float.parseFloat( extract( desc, "cue" ) ) * 44100 );
		int	duration	= (int)( stopTime - startTime * 44100 );
		
		AudioSource source = findSource(part.getMedia());
		
		AudioInstruction instruction = new AudioInstruction( start, end, source, cue, duration );
		
		if ( extract( desc, "rate" ) != null )
		{
			float rate = Float.parseFloat( extract( desc, "rate" ) );
			instruction.setConstantRate( rate );
		}
		else
		{
			float rate1 = Float.parseFloat( extract( desc, "rate1" ) );
			float rate2 = Float.parseFloat( extract( desc, "rate2" ) );
			instruction.setInterpolatedRate( rate1, rate2 );
		}
		
		if ( extract( desc, "volume" ) != null )
		{
			float volume = Float.parseFloat( extract( desc, "volume" ) );
			instruction.setConstantVolume( volume );
		}
		else
		{
			float volume1 = Float.parseFloat( extract( desc, "volume1" ) );
			float volume2 = Float.parseFloat( extract( desc, "volume2" ) );
			instruction.setInterpolatedVolume( volume1, volume2 );
		}
		
		append( instruction );
	}
	
	protected static String extract( String from, String key ) {
		int pos = from.indexOf( key + "=\"" );
		if ( pos < 0 ) return null;
		String value = from.substring( pos + key.length() + 2 );
		pos = value.indexOf( '\"' );
		return value.substring( 0, pos );
	}
	
	protected AudioSource findSource( Media media ) throws Exception {
		
		File path = new File(media.getSubjectLocator());
		
		AudioSource source = _sources.get( path.getPath() );
		
		if ( source == null ) {
			source = AudioSourceFactory.load( path );
			_sources.put( path.getPath(), source );
		}
		
		return source;
	}
}
