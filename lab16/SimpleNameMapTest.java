import static org.junit.Assert.*;
import org.junit.Test;

public class SimpleNameMapTest {
    @Test
    public void overallTest(){
        SimpleNameMap s = new SimpleNameMap();
        s.put("Yuchen", "Ji");
        s.put("Daniel", "Yun");
        s.put("Wei","Li");
        s.put("Yichen", "Lv");
        assertTrue(s.containsKey("Yuchen"));
        assertEquals("Yun", s.get("Daniel"));
        s.put("Yuchen", "Song");
        assertEquals("Song", s.get("Yuchen"));
        s.remove("Wei");
        assertEquals(3, s.size());
    }
}
