package no.bouvet.kpro.signal;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CounterTest {

    @Test
    public void testCounter() {
        Counter a, b;
        a = new Counter();
        b = new Counter();
        a.valueChanged.connect(b, "setValue(int)");
        a.setValue(12);
        assertEquals(12, a.getValue().intValue());
        assertEquals(12, b.getValue().intValue());
        b.setValue(48);     
        assertEquals(12, a.getValue().intValue());
        assertEquals(48, b.getValue().intValue());
    }
}

