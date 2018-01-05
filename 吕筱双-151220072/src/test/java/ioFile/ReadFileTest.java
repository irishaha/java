package ioFile;

import org.junit.Test;
import ui.Field;

import static org.junit.Assert.*;

public class ReadFileTest {

    @Test
    public void initYesterdayWorld() throws Exception {
        Field field = new Field();
        ReadFile readFile  = new ReadFile(field);

        assertEquals(100,field.getHuluwa().get(0).getPower());
        Boolean t = true;
        assertEquals(t,field.getHuluwa().get(0).isLive());
        assertNotNull(field.getHuluwa().get(0).getPosition().getX());
        assertNotNull(field.getHuluwa().get(0).getPosition().getY());

        assertEquals(65,field.getYaojing().get(3).getPower());
        assertEquals(t,field.getHuluwa().get(3).isLive());
        assertNotNull(field.getHuluwa().get(3).getPosition().getX());
        assertNotNull(field.getHuluwa().get(3).getPosition().getY());
    }

}