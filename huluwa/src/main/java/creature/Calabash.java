package creature;

import base.Position;
import ui.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/*
对葫芦娃及其对应的名称进行枚举
 */
enum Name {
    ONE("老大"),TWO("老二"),THREE("老三"),FOUR("老四"),FIVE("老五"),SIX("老六"),SEVEN("老七");

    private String name;

    private Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class Calabash  extends Creature {
    private Name name ;

    public Calabash(int index, Field field){
        super(field);
        this.mark = 0;

        this.name = Name.values()[index];
        this.power = 100;
        this.kill = 25;
        this.index = index+1;

        if(this.isLive == true){
            URL loc = this.getClass().getClassLoader().getResource(this.index+".png");
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
        else
            deadImg();
    }

    @Override
    public void move(){
        step(8);
        int nx = this.getPosition().getX() + stepRecord.getX();
        int ny = this.getPosition().getY() + stepRecord.getY();
        System.out.println(this.getName()+ nx + "  nnn  " + ny);
        this.setPosition(new Position(nx,ny));
        this.field.getWriteBar().appendMethodB(this.index-1 + " "
                +this.isLive() + " " + this.getPower() + " "
                + this.getPosition().getX() + " "+ this.getPosition().getY()+"\r\n");
    }

    public void deadImg(){
        if(this.isLive == false){
            URL loc = this.getClass().getClassLoader().getResource(this.index+"d.png");
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
    }

    public String getName() {
        return name.getName();
    }

    @Override
    public String toString() {
        return "Calabash{" +
                "name=" + name +
                ", position=" + position +
                '}';
    }

}

