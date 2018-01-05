package ui;

import base.Position;
import creature.*;
import formation.Formation;
import formation.FormationFac;
import ioFile.ReadFile;
import ioFile.WriteFile;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


//开启观察者模式，对键盘相应事件进行观察
public class Field extends JPanel {
    private int width = 20,height = 20;                 //矩阵大小
    private final int OFFSET = 45;                      //布阵偏移量
    private final int MOVE = 30;                        //图像移动放大比例

    private State state = State.None;                   //游戏状态
    private ArrayList<Creature> huluwa = null;          //葫芦娃及爷爷战队
    private ArrayList<Creature> yaojing = null;         //妖精战队
    private ArrayList<Thread> threads = null;           //线程集
    private ArrayList<ArrayList<Position<Creature>>> positions; //collection实现矩阵
    protected WriteFile writeBar = new WriteFile();             //存档器
    protected ReadFile readBar = null;                          //读档器

    private int w = 1200,h = 780;                       //窗口大小
    private boolean completed = false;
    private boolean stop = false;                      //是否暂停
    private boolean restart =true;                     //是否开新局

    public Field() {
        addKeyListener(new TAdapter());
        //将控件设置成可获取焦点状态，默认是无法获取焦点的，只有设置成true，才能获取控件的点击事件
        setFocusable(true);
        initWorld();
    }

    public final void initWorld() {

        int x = OFFSET;
        int y = OFFSET;
        initMatrix(20,20);

        state = State.Start;

        huluwa = new ArrayList<Creature>();
        yaojing = new ArrayList<Creature>();
        threads = new ArrayList<Thread>();

        for(int i = 0 ; i < 7;i++){
            this.huluwa.add(i,new Calabash(i,this));
            System.out.println(this.huluwa.get(i).getName());
        }
        this.huluwa.add(7,new Grandpa(this));

        this.yaojing.add(0,new Magnate(0,this));
        this.yaojing.add(1,new Magnate(1,this));
        for(int i = 2 ;i < 8;i++){
            this.yaojing.add(i,new Minion(i-2,this));
        }

        StandFormation( new Position<Creature>(15,6),-1,this.yaojing);
        StandFormation( new Position<Creature>(5,6),1,this.huluwa);

        if(state ==State.Start) {
            for (int i = 0; i < huluwa.size(); i++) {
                writeBar.appendMethodB(i + " "
                        + huluwa.get(i).isLive() + " " + huluwa.get(i).getPower() + " "
                        + huluwa.get(i).getPosition().getX() + " " + huluwa.get(i).getPosition().getY() + "\r\n");
            }
            for (int j = 0; j < yaojing.size(); j++) {
                writeBar.appendMethodB(8 + j + " "
                        + yaojing.get(j).isLive() + " " + yaojing.get(j).getPower() + " "
                        + yaojing.get(j).getPosition().getX() + " " + yaojing.get(j).getPosition().getY() + "\r\n");
            }
        }

    }

    public void initMatrix(int width, int height){
        this.width = width;
        this.height = height;
        this.positions = new ArrayList<ArrayList<Position<Creature>>>(this.width);

        for(int i = 0 ; i < this.width ; i++){
            ArrayList<Position<Creature>> temp = new ArrayList<Position<Creature>>();
            for(int j = 0 ; j < this.height; j++) {
                temp.add(j,new Position<Creature>(i,j));
            }
            this.positions.add(i,temp);
        }

    }

    public void StandFormation( Position head,int dir,ArrayList<Creature>creature){

        FormationFac formation = new FormationFac();
        ArrayList<Creature> temp = new ArrayList<Creature>();
        Random random = new Random();
        int a=random.nextInt(7);
        System.out.println("formation"+a);
        Formation tempForm = formation.create(a,head,dir);

        for(int i = 0 ; i < creature.size();i++){
            creature.get(i).setPosition(tempForm.getPos().get(i));
            System.out.println(creature.get(i).getPosition().getX() + "  "+creature.get(i).getPosition().getY());
            this.positions.get(creature.get(i).getPosition().getX()).get(creature.get(i).getPosition().getY()).setHolder(creature.get(i));
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        buildWorld(g);
    }

    public void buildWorld(Graphics g) {

        URL loc = this.getClass().getClassLoader().getResource("bg.jpg");
        ImageIcon iia = new ImageIcon(loc);
        g.drawImage(iia.getImage(),0,0,this.w,this.h,iia.getImageObserver());

        if(state == State.Over && stop == false){
            gameOver();
        }
        for (int i = this.huluwa.size()-1; i >= 0 ; i--) {
            Creature item = (Creature) huluwa.get(i);

            if(restart) {
                item.setRecordStart(new Position(item.getPosition().getX(), item.getPosition().getY()));
                g.drawImage(item.getImage(), item.getRecordStart().getX()*OFFSET, item.getRecordStart().getY()*OFFSET, this);
            }
            else
                g.drawImage(item.getImage(),
                        item.getRecordStart().getX()*OFFSET+(item.getPosition().getX()-item.getRecordStart().getX())*MOVE,
                        item.getRecordStart().getY()*OFFSET+(item.getPosition().getY()-item.getRecordStart().getY())*MOVE, this);

            if (completed) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }

        for (int i = this.yaojing.size()-1; i >= 0 ; i--) {
            Creature item = (Creature) yaojing.get(i);

            if(restart) {
                item.setRecordStart(new Position(item.getPosition().getX(), item.getPosition().getY()));
                g.drawImage(item.getImage(), item.getRecordStart().getX()*OFFSET, item.getRecordStart().getY()*OFFSET, this);
            }
            else
                g.drawImage(item.getImage(),
                        item.getRecordStart().getX()*OFFSET+(item.getPosition().getX()-item.getRecordStart().getX())*MOVE,
                        item.getRecordStart().getY()*OFFSET+(item.getPosition().getY()-item.getRecordStart().getY())*MOVE, this);

            if (completed) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (completed) {
                return;
            }
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_P) {
                if(state == State.Playing)
                    gameStop();

            } else if (key == KeyEvent.VK_C) {
                if(state == State.Playing)
                    gameContinue();
            } else if (key == KeyEvent.VK_SPACE) {
                gameStart();
            } else if (key == KeyEvent.VK_R) {
                if(state != State.Playing)
                    restartLevel();
            }else if(key == KeyEvent.VK_L){
                gameReplay();

            }else if(key == KeyEvent.VK_N){
                if(restart == true)
                    restart = false;
                if(state == State.Replaying){
                    if(!readBar.active()){
                        gameOver();
                        state = State.None;
                    }
                }
            }
            repaint();
        }
    }

    public void restartLevel() {
        if(state == State.Playing ||state == State.Over){
            System.out.println("over!!!!!!!!!!!!!!!!");

            for(int i = 0 ; i < threads.size();i++){
                threads.get(i).interrupt();
            }
            //writeBar.close();
        }

        this.positions.clear();
        this.huluwa.clear();
        this.yaojing.clear();
        this.threads.clear();
        stop = false;
        initWorld();

        // this.writeBar = new WriteFile();
        if (completed) {
            completed = false;
        }
    }

    synchronized public void gameOver(){
        String temp = "平局\n";
        for(int i = 0 ; i < threads.size();i++){
            threads.get(i).interrupt();
        }
        System.out.println("over!!!!!!!!!!!!!!!!");
        writeBar.close();

        stop = true;
        //restart = true;
        int i;
        for(i = 0 ; i< huluwa.size();i++){
            if(huluwa.get(i).isLive()) {
                temp = "葫芦娃胜利\n";
                break;
            }
        }
        if(i >= huluwa.size())
            temp = "妖精胜利\n";
        String message = temp + "游戏结束";

        entry(message);
    }

    public void entry(String message){
        Object[] options = {"新游戏/回放","退出"};
        int res = JOptionPane.showOptionDialog(null, message,
                "是否开始新游戏", JOptionPane.YES_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if(res == 0){
            restart = true;
            this.positions.clear();
            this.huluwa.clear();
            this.yaojing.clear();
            this.threads.clear();
            stop = false;
            writeBar = new WriteFile();
            initWorld();
            repaint();
        }
        else if(res == 1){
            System.exit(0);
        }
    }

    public void exit(){
        gameStop();
        Object[] options = {"我错了 送小fa","残忍退出"};
        int res = JOptionPane.showOptionDialog(null, "您真的要退出吗QAQ",
                "是否开始新游戏", JOptionPane.YES_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if(res == 0){
            if(state == State.Over ||state == State.None)
                restartLevel();
            else {
                gameContinue();
            }
        }
        else if(res == 1){
            System.exit(0);
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void helpFile(){
        gameStop();
        JOptionPane.showMessageDialog(null, "r 变换阵型\r\n空格 开始游戏\r\np 暂停游戏\n" +
                        "c 继续游戏\r\nl 读档\r\nn 推进读档",
                "游戏说明", JOptionPane.INFORMATION_MESSAGE);
        this.setVisible(true);
        gameContinue();
    }

    public void gameStart() {
        restart = false;
        state = State.Playing;
        int i;

        for( i = 0 ;i < huluwa.size();i++) {
            threads.add(new Thread(huluwa.get(i)));
            threads.get(i).start();
        }
        for(int j = 0 ;j < yaojing.size();j++) {
            threads.add(new Thread(yaojing.get(j)));
            System.out.println("start !!!!!!");
            threads.get(i+j).start();
        }
    }

    public void gameStop() {
        stop = true;
    }

    public void gameReplay() {
        state = State.Replaying;
        restart = true;
        readBar = new ReadFile(Field.this);
    }

    public void gameContinue() {
        stop = false;
        for(int i = 0 ; i < threads.size();i++){
            if(threads.get(i).isAlive() &&  threads.get(i).isInterrupted()){
                threads.get(i).start();
            }
        }
    }

    public ArrayList<Creature> getYaojing() {
        return yaojing;
    }

    public ArrayList<Creature> getHuluwa() {

        return huluwa;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ArrayList<ArrayList<Position<Creature>>> getPositions() {
        return positions;
    }

    public WriteFile getWriteBar() {
        return writeBar;
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    public State getState() {
        return state;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

}
