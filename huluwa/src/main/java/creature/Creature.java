package creature;

import base.Position;
import ui.Field;
import ui.State;

import java.awt.*;
import java.util.Random;

public abstract class  Creature implements Runnable{
    protected Field field;               //战场索引

    protected int mark = -1;             //所属战队

    protected boolean isLive = true;    //生命状态
    protected int power;                 //体力
    protected int kill;                  //杀伤力
    protected int index;                 //图片索引
    protected Position position;         //所在坐标
    protected Position stepRecord;       //步伐记录（大小，方向）
    protected Position recordStart;      //原始位置记录，用于布阵
    private Image image;                  //个人形象

    public Creature(Field field) {
        this.field = field;
    }

    synchronized public void run() {
        //强调生物的状态必须是存活的，防止在线程运行中生物被打死所以要锁定
        if(this.isLive == true) {
            while (!Thread.interrupted()&&this.isLive()&&this.field.getState() == State.Playing) {
                Random rand = new Random();

                this.move();
                System.out.println("creature" + this.getName() + this.getPosition().getX() + "  " + this.getPosition().getY());
                try {

                    Thread.sleep(rand.nextInt(1000) + 1000);

                    this.field.repaint();

                } catch (Exception e) {

                }
                //此处处理游戏暂停时线程暂停
                synchronized (this) {
                    while (this.field.isStop()) {
                        Thread.interrupted();
                    }
                }
            }
        }
    }

    //对生物进行移动
    abstract void move();

    //判断最近的存活的敌人
    public Creature judgemove() {
        int mindis = 100000;
        Creature target = null;

        if (this.isLive) {
            if (this.mark == this.field.getYaojing().get(0).getMark()) {

                for (int i = 0; i < this.field.getHuluwa().size(); i++) {
                    Creature temp = this.field.getHuluwa().get(i);
                    if (temp.isLive()) {
                        int dx = (this.position.getX() - temp.getPosition().getX()) * (this.position.getX() - temp.getPosition().getX());
                        int dy = (this.position.getY() - temp.getPosition().getY()) * (this.position.getY() - temp.getPosition().getY());
                        if (dx + dy < mindis) {
                            mindis = dx + dy;
                            target = temp;
                        }
                    }
                }
            } else {
                for (int i = 0; i < this.field.getYaojing().size(); i++) {
                    Creature temp = this.field.getYaojing().get(i);
                    if (temp.isLive()) {
                        int dx = (this.position.getX() - temp.getPosition().getX()) * (this.position.getX() - temp.getPosition().getX());
                        int dy = (this.position.getY() - temp.getPosition().getY()) * (this.position.getY() - temp.getPosition().getY());
                        if (dx + dy < mindis) {
                            mindis = dx + dy;
                            target = temp;
                        }
                    }
                }
            }
        }
        return target;
    }

    //读档时对体力进行更新
    public void initPower(int power){
        this.power = power;
        System.out.println(this.getName()+"power"+this.power +"live"+this.isLive);
        if(this.power <= 0) {
            this.isLive = false;
            deadImg();
        }
    }

    //打斗过程中对体力进行更新
    public void setPower(int power) {
        this.power -= power;

        if(this.power <= 0) {
            this.isLive = false;
            deadImg();
            this.field.getThreads().get(this.index-1).interrupt();
            this.field.getWriteBar().appendMethodB(this.index-1 + " "
                    +this.isLive() + " " + this.getPower() + " "
                    + this.getPosition().getX() + " "+ this.getPosition().getY()+"\r\n");
        }
        System.out.println(this.getName()+"power"+this.power +"live"+this.isLive);
    }

    //生成对于当下标记敌人应该买进的步伐，取最大步伐和二者距离中较小的一个，作为随机步伐的上限
    public void step(int stp){
        Creature target = null;
        synchronized (this) {
            target= this.judgemove();
        }
        Random rand = new Random();
        if(target == null) {
            this.field.setState(State.Over);
            stepRecord = new Position(0, 0);
            return;
        }
        if(isLive) {
            synchronized (this) {
                int stepx, stepy;
                int dx = target.getPosition().getX() - this.position.getX();
                int dy = target.getPosition().getY() - this.position.getY();
                int dirx = dx < 0 ? -1 : 1;
                int diry = dy < 0 ? -1 : 1;
                if (Math.abs(dx) + Math.abs(dy) < 3) {
                    System.out.println(target.getName() + "target" + target.getPosition().getX() + "  " + target.getPosition().getY());
                    target.setPower(rand.nextInt(this.kill));
                    stepRecord = new Position(0, 0);
                } else {
                    do {
                        if (Math.abs(dx) > stp)
                            stepx = rand.nextInt(stp) * dirx;
                        else
                            stepx = rand.nextInt(Math.abs(dx) + 1) * dirx;
                        if (Math.abs(dy) > stp)
                            stepy = rand.nextInt(stp) * diry;
                        else
                            stepy = rand.nextInt(Math.abs(dy) + 1) * diry;
                    } while (!this.isEmpty(new Position(stepx, stepy)));

                    System.out.println(this.getName() + stepx + "  " + stepy);
                    stepRecord = new Position(stepx, stepy);
                }
            }
        }
    }

    //由于一个坐标只能站一个生物或尸体，对当下坐标是否空判断
    public boolean isEmpty(Position temp){
        for(int i = 0 ;i<this.field.getHuluwa().size();i++){
            Creature t = this.field.getHuluwa().get(i);
            if(t.getPosition().getX() == temp.getX() && t.getPosition().getY() == temp.getY())
                return false;
        }
        for(int i = 0 ;i<this.field.getYaojing().size();i++){
            Creature t = this.field.getYaojing().get(i);
            if(t.getPosition().getX() == temp.getX() && t.getPosition().getY() == temp.getY())
                return false;
        }
        return true;
    }

    public int getMark() {
        return mark;
    }

    public boolean isLive() {
        return isLive;
    }

    public Position getRecordStart() {
        return recordStart;
    }

    public void setRecordStart(Position recordStart) {
        this.recordStart = recordStart;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public Position getPosition(){return position;}

    public void setPosition(Position position){
        this.position = position;
        position.setHolder(this);
    }

    abstract public void deadImg();

    abstract public String getName();

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getPower() {
        return power;
    }

}
