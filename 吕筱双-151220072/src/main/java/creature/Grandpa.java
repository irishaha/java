package creature;

import base.Position;
import ui.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Grandpa extends Creature{
    private String name;

    public Grandpa(Field field){
        super(field);
        this.name= "爷爷";
        this.power = 30;
        this.kill = 5;
        this.position = null;
        this.mark = 0;
        this.index = 8;

        if(this.isLive == true){
            URL loc = this.getClass().getClassLoader().getResource("8.png");
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
        else
            deadImg();
    }

    @Override
    public void move(){
        step(3);
        int nx = this.getPosition().getX() + stepRecord.getX();
        int ny = this.getPosition().getY() + stepRecord.getY();
        this.setPosition(new Position(nx,ny));
        this.field.getWriteBar().appendMethodB("7" + " "
                +this.isLive() + " " + this.getPower() + " "
                + this.getPosition().getX() + " "+ this.getPosition().getY()+"\r\n");
    }

    public void deadImg(){
        if(this.isLive == false){
            URL loc = this.getClass().getClassLoader().getResource("8d.png");
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
    }

    @Override
    public String getName() {
        return name;
    }

}

