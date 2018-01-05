package formation;

import base.Position;

public class Changshe extends Formation {
    public Changshe(Position head,int dir){
        this.head.setPositon(head.getX(),head.getY());
        this.pos.add(0,new Position(head.getX()-0*dir,head.getY()+0));
        this.pos.add(1,new Position(head.getX()-0*dir,head.getY()+2));
        this.pos.add(2,new Position(head.getX()-0*dir,head.getY()-2));
        this.pos.add(3,new Position(head.getX()-0*dir,head.getY()+4));
        this.pos.add(4,new Position(head.getX()-0*dir,head.getY()-4));
        this.pos.add(5,new Position(head.getX()-0*dir,head.getY()+6));
        this.pos.add(6,new Position(head.getX()-0*dir,head.getY()-6));
        this.pos.add(7,new Position(head.getX()-0*dir,head.getY()+8));
    }
}
