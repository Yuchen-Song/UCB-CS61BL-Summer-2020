import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MeasurementTest {

    // TODO: Add additional JUnit tests for Measurement.java here.
    @Test
    public void testDefaultConstructor() {
        Measurement m = new Measurement();
        assertTrue(m.getFeet() == 0);
        assertTrue(m.getInches() == 0);
    }

    @org.junit.Test
    public void testFeetGivenConstructor() {
        int f = 1;
        Measurement m = new Measurement(f);
        assertTrue(m.getFeet() == f);
        assertTrue(m.getInches() == 0);
    }

    @org.junit.Test
    public void testAllInfoConstructor() {
        int f = 1;
        int i = 13;
        Measurement m = new Measurement(f,i);
        assertTrue(m.getFeet() == 2);
        assertTrue(m.getInches() == 1);
    }

    @org.junit.Test
    public void testPlus() {
        Measurement m1 = new Measurement(2,11);
        Measurement m2 = new Measurement(2,11);
        Measurement mPlus = m1.plus(m2);
        assertTrue(mPlus.getFeet() == 5);
        assertTrue(mPlus.getInches() == 10);
    }

    @org.junit.Test
    public void testMinus() {
        Measurement m1 = new Measurement(2,1);
        Measurement m2 = new Measurement(1,11);
        Measurement mMinus = m1.minus(m2);
        assertTrue(mMinus.getFeet() == 0);
        assertTrue(mMinus.getInches() == 2);
    }

    @org.junit.Test
    public void testMultiple() {
        for(int i=0; i<100; i++){
            for(int j=1; j<100; j++){
                Measurement m = new Measurement(i,j);
                for(int t=0; t<100; t++){
                    Measurement mo = m.multiple(t);
                    int test=i*t + j*t/12;
                    //assertEquals(m.getFeet(),test);
                    //assertEquals(m.getInches(),(j*t)%12);
                    assertTrue(mo.getFeet() == (i*t + j*t/12));
                    assertTrue(mo.getInches() == j*t%12);
                }
            }
        }
        /*Measurement m = new Measurement(0,7);
        Measurement m1 = m.multiple(3);
        assertTrue(m1.getFeet() == 1);
        assertTrue(m1.getInches() == 9);*/
    }

    @org.junit.Test
    public void testString() {
        Measurement m = new Measurement(3,7);
        assertEquals(m.toString(),"3\'7\"");
    }
}