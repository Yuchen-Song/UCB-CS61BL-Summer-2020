import org.junit.Test;
import static org.junit.Assert.*;

public class IntArrayListStepperTest {

    @Test
    public void stepByZeroTest() {
        IntArrayList lst = new IntArrayList();
        lst.setList(1,2,3,4);

        IntArrayListStepper stepper = new IntArrayListStepper(lst, 0);
        assertEquals(stepper.get(0), 1);
        assertEquals(stepper.get(1), 1);
        assertEquals(stepper.get(2), 1);
        assertEquals(stepper.get(3), 1);
        assertEquals(stepper.get(50), 1);
    }

    @Test
    public void stepByOneTest() {
        IntArrayList lst = new IntArrayList();
        lst.setList(1,2,3,4);

        IntArrayListStepper stepper = new IntArrayListStepper(lst, 1);
        assertEquals(stepper.get(0), 1);
        assertEquals(stepper.get(1), 2);
        assertEquals(stepper.get(2), 3);
        assertEquals(stepper.get(3), 4);
    }

    @Test
    public void stepByOneNegativeTest() {
        IntArrayList lst = new IntArrayList();
        lst.setList(1,2,3,4);

        IntArrayListStepper stepper = new IntArrayListStepper(lst, 1);
        assertEquals(stepper.get(-4), 1);
        assertEquals(stepper.get(-3), 2);
        assertEquals(stepper.get(-2), 3);
        assertEquals(stepper.get(-1), 4);
    }

    @Test
    public void stepByThreeTest() {
        IntArrayList lst = new IntArrayList();
        lst.setList(1,2,3,4,5,6,7,8,9);

        IntArrayListStepper stepper = new IntArrayListStepper(lst, 3);
        assertEquals(stepper.get(0), 1);
        assertEquals(stepper.get(1), 4);
        assertEquals(stepper.get(2), 7);
    }

    @Test
    public void stepByThreeNegativeTest() {
        IntArrayList lst = new IntArrayList();
        lst.setList(1,2,3,4,5,6,7,8,9);

        IntArrayListStepper stepper = new IntArrayListStepper(lst, 3);
        assertEquals(stepper.get(-3), 3);
        assertEquals(stepper.get(-2), 6);
        assertEquals(stepper.get(-1), 9);
    }
}