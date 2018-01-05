package formation;

import base.Position;
import creature.Creature;

import java.util.ArrayList;

public abstract class Formation {
    protected Position<Creature> head = new Position<Creature>();
    protected ArrayList<Position> pos = new ArrayList<Position>();
    public ArrayList<Position> getPos() {
        return pos;
    }
    public Formation(){

    }
}
