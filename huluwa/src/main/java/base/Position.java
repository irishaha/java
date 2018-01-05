package base;
import com.sun.corba.se.spi.orbutil.fsm.State;
import creature.Creature;

//标记位置的状态
enum PositionState{
    EMPTY,CREATURE,CORPSE,OBSTACLE
}
public class Position <T extends Creature> {

    private int x;
    private int y;
    private T holder;
    PositionState state;

    public T getHolder() {
        if (this.holder == null)
            return null;
        else
            return holder;
    }

    public void setHolder(T holder) {
        this.holder = holder;
    }

    public void cleanHolder(){this.holder = null;}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPositon(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void Move(int x,int y){
        this.x += x;
        this.y += y;
    }
    public Position(int x,int y){
        super();
        this.x = x;
        this.y = y;
        this.holder = null;
        this.state = PositionState.EMPTY;
    }

    public PositionState getState() {
        return state;
    }

    public void setState(PositionState state) {

        this.state = state;
    }

    public Position(){
        this.x = -1;
        this.y = -1;
        this.holder = null;
        this.state = PositionState.EMPTY;
    }
}
