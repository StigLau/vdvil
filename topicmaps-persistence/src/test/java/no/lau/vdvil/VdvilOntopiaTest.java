package no.lau.vdvil;

import de.topicmapslab.aranuka.Configuration;
import de.topicmapslab.aranuka.Session;
import de.topicmapslab.aranuka.connectors.IProperties;
import de.topicmapslab.aranuka.exception.*;
import de.topicmapslab.aranuka.tinytim.connectors.TinyTiMConnector;
import no.lau.vdvil.model.Address;
import no.lau.vdvil.model.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

public class VdvilOntopiaTest {
    static Configuration conf = new Configuration(TinyTiMConnector.class);
    // get the session
    Session session;

    @BeforeClass
    public static void beforeClass() throws TopicMapException, BadAnnotationException, ClassNotSpecifiedException, NoSuchMethodException {
        // add classes to map
		//conf.addClass(Person.class);

        // set base lacotor which identifies the topic map in the engine
		conf.setProperty(IProperties.BASE_LOCATOR, "http://aranuka.example.org/");

		// set filename for de/serialisation
 	 	conf.setProperty(IProperties.FILENAME, "topicmaps-persistence/src/test/resources/topicmaps/vdvil.xtm");

		// add prefix used in QNames of annotations
		conf.addPrefix("ex", "http://aranuka.example.org/");
		conf.addPrefix("onto", "http://psi.ontopedia.net/");


        conf.addName("onto:opera", "Opera");

        // add some names to the used types
		conf.addName("ex:person", "Person");
		conf.addName("ex:firstname", "First Name");
		conf.addName("ex:lastname", "Surname");
		conf.addName("ex:lives", "lives");
		conf.addName("ex:habitant", "Habitant");
		conf.addName("ex:place", "Place");

		conf.addName("ex:address", "Address");
		conf.addName("ex:zipcode", "Zip Code");
		conf.addName("ex:street", "Street");
		conf.addName("ex:city", "City");
		conf.addName("ex:number", "House Number");
    }

    @Test
    public void testSomeShite() throws TopicMapIOException, TopicMapException, BadAnnotationException, ClassNotSpecifiedException, NoSuchMethodException, TopicMapInconsistentException {
        session = conf.getSession(false);

        Person p = createPerson();

        //Person p = new Person();
        p.setId("ex:max");
        p.setFirstName("Max");
        p.setLastName("Powers");

        //session.persist(p);

        //Write some operas
        //Opera newOpera = new Opera();
        //newOpera.setId("Wootsie");
        //session.persist(newOpera);


        // try getting all persons in the topic map
		Set<Object> persons = session.getAll(Person.class);

		// persons.size() == 0 at the first start 1 else
		System.out.println("Persons found: " + persons.size());

        //System.out.println("session.getAll(Opera.class).size() = " + session.getAll(Opera.class).size());


		//session.flushTopicMap();
    }

    private static Person createPerson() {
        Address a = new Address();
        a.setId(1);
        a.setZipCode("00815");
        a.setCity("Example City");
        a.setStreet("Example Street");
        a.setNumber("1");

        Person p = new Person();
        p.setId("ex:max");
        p.setFirstName("Max");
        p.setLastName("Powers");
        p.setAddress(a);

        return p;
    }

}