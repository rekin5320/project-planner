package pw.pap;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class AppTest {
    @Test
    public void sample_test_correct() {
        assertEquals(1, 1);
    }

    @Test
    public void sample_test_bad() {
        assertEquals(1, 2);
    }
}
