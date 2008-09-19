package no.lau.kpro.topicmap;

import org.testng.annotations.*;
import no.lau.kpro.domain.TopicIdentifyable;

/**
 * @author <a href="mailto:stig@lau.no">Stig Lau</a>
 * @since Jul 31, 2008
 */
public class TopicMapReaderTest {
    SortingBoxTopicMap topicMap;

    @BeforeClass
    public static void setUp() {

    }

    @Test
    public void testGetOperasByPuccini() {
        

    //Find Topics in the current context (time frame, audible, rendrable)
        //Current timeframe = Beats 0 - 128
        topicMap = new SortingBoxTopicMap("opera.ltm");
        TopicIdentifyable result = topicMap.getTopicsByTimeFrame(0, 128);

        System.out.println("result.getTopic().getId() = " + result.getTopic().getId());

    //Get Topic Types of each Topic
        //Search for audibles
    //Find Receptors for the Topic Types

    //Inroduce Topics to Receptors


        
    }
}