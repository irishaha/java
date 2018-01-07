package creature;

import base.Position;
import ui.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Minion extends Creature  {
    private String  name ;

    public Minion(int num, Field field){
        super(field);
        this.mark = 1;
        this.name= "喽"+String.format("%02d",num);
        this.index = num+11;
        this.power = 65;
        this.kill = 18;
        if(this.isLive == true){
            URL loc = this.getClass().getClassLoader().getResource("11.png");
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
        else
            deadImg();
    }

    @Override
    public void move(){
        step(6);
        int nx = this.getPosition().getX() + stepRecord.getX();
        int ny = this.getPosition().getY() + stepRecord.getY();
        this.setPosition(new Position(nx,ny));
        this.field.getWriteBar().appendMethodB( this.index-1+ " "
                +this.isLive() + " " + this.getPower() + " "
                + this.getPosition().getX() + " "+ this.getPosition().getY()+"\r\n");
    }

    public void deadImg(){
        if(this.isLive == false){
            URL loc = this.getClass().getClassLoader().getResource("11d.png");
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
    }

    public String  getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Minion{" +
                "name=" + name +
                ", position=" + position +
                '}';
    }

}
