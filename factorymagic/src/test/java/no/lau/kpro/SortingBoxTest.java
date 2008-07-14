package no.lau.kpro;

import org.testng.annotations.*;
import no.lau.kpro.domain.Topic;
import no.lau.kpro.model.*;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 13, 2008
 */
public class SortingBoxTest {

    @Test
    public void testFindsCorrectObjectFromSimpleStore() {
        SimpleStore pileOfCrud = new SimpleStore();
        pileOfCrud.putObjectIntoTopicMap(new PinkSquare(new Topic("1")));

        System.out.println("pileOfCrud.findTopicsByClass(PinkSquare.class).size() = " + pileOfCrud.findTopicsExtendingClass(PinkSquare.class).size());
        System.out.println("pileOfCrud.findTopicsByClass(Square.class).size() = " + pileOfCrud.findTopicsExtendingClass(Square.class).size());

    }

    @Test
    public void testAwesomeSortingBox() {
        SortingBox sortingBox = new SortingBox();
        sortingBox.addReceptor(new SquareReceptor());
        sortingBox.addReceptor(new CircleReceptor());

        //Pile of Crud is stuff that needs to be sorted
        SimpleStore pileOfCrud = new SimpleStore();
        pileOfCrud.putObjectIntoTopicMap(new Pink(new Topic("1")));
        pileOfCrud.putObjectIntoTopicMap(new PinkSquare(new Topic("pinkSquare")));
        pileOfCrud.putObjectIntoTopicMap(new PinkSquare(new Topic("pinkSquare2")));
        pileOfCrud.putObjectIntoTopicMap(new YellowCircle(new Topic("yellowCircle")));

        //Make the sorting happen!
        sortingBox.sort(pileOfCrud);
    }

    //TODO To make sorting box faster - make own sorter that does an @Instanceof Pink.class
}
