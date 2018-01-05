package creature;

import org.junit.Test;
import ui.Field;

import static org.junit.Assert.*;

public class CreatureTest {
    @Test
    public void judgemove() throws Exception {
        Field field = new Field();
        field.initWorld();
        Creature creature = field.getHuluwa().get(0);
        Creature temp = creature.judgemove();
        boolean t = true;
        assertEquals(t,temp.isLive());
        assertNotEquals("class creature.Calabash",temp.getClass());
        assertNotEquals("class creature.Grandpa",temp.getClass());
    }

}