package no.bouvet.kpro.renderer.audio;

import java.io.File;
import java.util.*;
import no.bouvet.kpro.model.Part;
import no.bouvet.kpro.model.stigstest.Media;
import no.bouvet.kpro.model.stigstest.Event;
import no.bouvet.kpro.persistence.Storage;
import no.bouvet.kpro.renderer.*;


/**
 * 
 * @author Michael Stokes
 */
public class TopicMapInstructions extends Instructions {
	
	protected Media		_media;
	protected File		_basePath; //Not to be used
	protected HashMap<String, AudioSource> _sources = new HashMap<String, AudioSource>();
	
	/**
	 * @author Michael Stokes
	 */
	public TopicMapInstructions( Event mainEvent, File basePath ) throws Exception {
		
		_basePath = basePath;
        List<Event> parts = mainEvent.getChildren();

		Collections.sort( parts, new Comparator<Event>() {
			public int compare( Event a, Event b) {
				return a.getStartTime() - b.getStartTime();
			}  } );
		
		for ( Event part : parts ) {
			//addTopicMapInstruction( part ); TODO undo commenting!!!
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
	
	protected void addTopicMapInstruction( Part part ) throws Exception {
		
		int start		= (int)( (long)part.getStartTime() * 441 / 10 );
		int end			= (int)( (long)part.getStopTime() * 441 / 10 );
		String desc		= part.getDescription();
		String song		= extract( desc, "song" ).replace( '\\', '/' );
		int	cue			= (int)( Float.parseFloat( extract( desc, "cue" ) ) * 44100 );
		int	duration	= (int)( Float.parseFloat( extract( desc, "duration" ) ) * 44100 );
		
		AudioSource source = findSource( song );
		
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
	
	protected AudioSource findSource( String file ) throws Exception {
		
		File path = new File( _basePath, file );
		
		AudioSource source = _sources.get( path.getPath() );
		
		if ( source == null ) {
			source = AudioSourceFactory.load( path );
			_sources.put( path.getPath(), source );
		}
		
		return source;
	}
}
