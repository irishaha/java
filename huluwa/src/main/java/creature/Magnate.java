package creature;

import base.Position;
import ui.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/*
对妖精头领及其对应的名称进行枚举
 */
enum MagnateName {
    scorpion("蝎子"),Snake("蛇精");

    private String name;

    private MagnateName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class Magnate extends Creature{
    private MagnateName magnateName ;

    public Magnate(int index, Field field){
        super(field);
        this.magnateName = MagnateName.values()[index];
        this.index = index+9;
        this.mark = 1;
        this.power = 100;
        this.kill = 45;
        if(this.isLive == true){
            URL loc = this.getClass().getClassLoader().getResource(this.index +".png");
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
        else
            deadImg();
    }

    @Override
    public void move(){
        step(12);
        int nx = this.getPosition().getX() + stepRecord.getX();
        int ny = this.getPosition().getY() + stepRecord.getY();
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
        return magnateName.getName();
    }

    public void printName() {
        System.out.print(this.magnateName.getName());
    }

    @Override
    public String toString() {
        return "Magnate{" +
                "magnateName=" + magnateName +
                ", position=" + position +
                '}';
    }

}



