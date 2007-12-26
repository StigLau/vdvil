package no.bouvet.kpro.persistence;

import no.bouvet.topicmap.query.StandardTopicParameter;
import no.bouvet.topicmap.query.TologQuery;
import no.bouvet.topicmap.query.ITopicParameter;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.core.TopicMapUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.DeclarationContextIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;


public class VDvilTopicMapIntegrationTest {


    static VaudevilleTopicMap tm;

    private static ITopicParameter EVENT = new StandardTopicParameter(VaudevilleTopicType.EVENT);
    private static ITopicParameter MEDIA = new StandardTopicParameter(VaudevilleTopicType.MEDIA);
    private static ITopicParameter WHOLE = new StandardTopicParameter(VaudevilleTopicType.WHOLE);
    private static ITopicParameter PART = new StandardTopicParameter(VaudevilleTopicType.PART);

    @BeforeClass
    public static void setUp(){
        tm = new VaudevilleTopicMap("musikk.xtm");
    }

    @Test
    public void testGetSingleTopic() {
        TopicDAO topicDAO = tm.getTopicDAOByPSI("https://wiki.bouvet.no/snap-rythm_is_a_dancer");
        assertEquals("Snap - Rhythm is a Dancer.mp3", topicDAO.getName());
    }

    @Test
    public void testPartWholeAssociation() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.PART_WHOLE, PART, WHOLE);
        List resutl = tm.queryForList(tologQuery, WHOLE);
        assertEquals(2, resutl.size());
    }
    
    @Test
    public void testMediaEventAssociation() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.MEDIA_EVENT, MEDIA, EVENT);
        List resutl = tm.queryForList(tologQuery, MEDIA);
        assertEquals(1, resutl.size());
    }

    @Test
    public void testSomething() throws InvalidQueryException {
        VaudevilleTopicMap tm2 = new VaudevilleTopicMap("opera.ltm");
        String tologQuery2 = "vdvil:media-event($MEDIA : vdvil:media, $EVENT : vdvil:event)?";
        String tologQuery = "instance-of($MEDIA, %EVENT%)?";
        String fieldName = "EVENT";
        Map arguments = new HashMap();
        //arguments.put("EVENT", tm.getTopicDAOByPSI("https://wiki.bouvet.no/snap-rythm_is_a_dancer").getTopicIF());
        arguments.put("EVENT", tm.getTopicDAOByPSI("http://en.wikipedia.org/wiki/Puccini").getTopicIF());
        List result = new ArrayList();

        DeclarationContextIF dcontext = QueryUtils.parseDeclarations(tm2.getInterface(), prefixes);

        QueryProcessorIF queryProcessor = QueryUtils.getQueryProcessor(tm2.getInterface());
        QueryResultIF queryResult = queryProcessor.execute(tologQuery, arguments, dcontext);

        while (queryResult.next()) {
            result.add(queryResult.getValue(fieldName));
        }





        assertEquals(1, result.size());
    }


    static String prefixes = "  using ont   for i\"http://psi.ontopia.net/#\""
			+ "  using onto  for i\"http://psi.ontopia.net/ontology/\""
			+ "  using purl  for i\"http://purl.org/dc/elements/1.1/\""
			+ "  using xtm   for i\"http://www.topicmaps.org/xtm/1.0/core.xtm#\""
			+ "  using tech  for i\"http://www.techquila.com/psi/thesaurus/#\""
			+ "  import \"http://psi.ontopia.net/tolog/string/\" as str "
            + "  using vdvil  for i\"https://wiki.bouvet.no/\"";

}
