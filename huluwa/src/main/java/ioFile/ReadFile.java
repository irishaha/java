package ioFile;

import base.Position;
import ui.Field;

import java.awt.*;
import java.io.*;

public class ReadFile{
    private FileReader fileReader = null;
    private BufferedReader bufferedReader = null;
    private Field field;
    private String fileName;
    public ReadFile(Field field){
        openFile();
        this.field = field;
        initYesterdayWorld();


    }
    public void openFile(){
        FileDialog fileDialog;
        do{
            fileDialog = new FileDialog(new Frame(),"复盘文档",FileDialog.LOAD);
            fileDialog.setVisible(true);
            this.fileName = fileDialog.getFile();
        }while (fileName == null);

        try{
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void initYesterdayWorld(){
        String  temp = null;
        for(int i = 0;i < 16;i++){
            try {
                temp = bufferedReader.readLine();
                String []result = temp.split(" ");
                System.out.println(result[0]+ " "+result[1]+"  " +result[2]+" "+ result[3]);
                int threadnum = Integer.parseInt(result[0]);
                boolean liveState = Boolean.parseBoolean(result[1]);
                int powNum = Integer.parseInt(result[2]);
                int posx = Integer.parseInt(result[3]);
                int posy = Integer.parseInt(result[4]);
                if(threadnum <= 7){
                    this.field.getHuluwa().get(i).setLive(liveState);
                    this.field.getHuluwa().get(i).initPower(powNum);
                    this.field.getHuluwa().get(i).setPosition(new Position(posx,posy));
                }
                else if(threadnum <= 15){
                    this.field.getYaojing().get(i-8).setLive(liveState);
                    this.field.getYaojing().get(i-8).initPower(powNum);
                    this.field.getYaojing().get(i-8).setPosition(new Position(posx,posy));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean active(){
        String temp = null;
        try {
            temp = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(temp == null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        String []result = temp.split(" ");
        System.out.println(result[0]+ " "+result[1]+"  " +result[2]+" "+ result[3]);
        int threadnum = Integer.parseInt(result[0]);
        boolean liveState = Boolean.parseBoolean(result[1]);
        int powNum = Integer.parseInt(result[2]);
        int posx = Integer.parseInt(result[3]);
        int posy = Integer.parseInt(result[4]);
        if(threadnum <= 7){
            this.field.getHuluwa().get(threadnum).setLive(liveState);
            this.field.getHuluwa().get(threadnum).initPower(powNum);
            this.field.getHuluwa().get(threadnum).setPosition(new Position(posx,posy));
        }
        else if(threadnum <= 15){
            this.field.getYaojing().get(threadnum-8).setLive(liveState);
            this.field.getYaojing().get(threadnum-8).initPower(powNum);
            this.field.getYaojing().get(threadnum-8).setPosition(new Position(posx,posy));
        }
        return true;
    }


}