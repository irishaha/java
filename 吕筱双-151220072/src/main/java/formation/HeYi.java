package formation;

import base.Position;

public class HeYi extends Formation {
    public HeYi(Position head,int dir){
        this.head.setPositon(head.getX(),head.getY());
        this.pos.add(0,new Position(head.getX()-0*dir,head.getY()+0));
        this.pos.add(1,new Position(head.getX()-1*dir,head.getY()+1));
        this.pos.add(2,new Position(head.getX()-1*dir,head.getY()-1));
        this.pos.add(3,new Position(head.getX()-2*dir,head.getY()+2));
        this.pos.add(4,new Position(head.getX()-2*dir,head.getY()-2));
        this.pos.add(5,new Position(head.getX()-3*dir,head.getY()+3));
        this.pos.add(6,new Position(head.getX()-3*dir,head.getY()-3));
        this.pos.add(7,new Position(head.getX()-4*dir,head.getY()+4));
    }
}
