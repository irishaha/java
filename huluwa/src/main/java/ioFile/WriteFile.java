package ioFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class WriteFile{
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    String fileName = null;
    public WriteFile(){
        isExit();
    }
    public void  isExit(){
        Random rand = new Random();
        fileName = rand.nextInt(20)+".txt";
        File file=new File(fileName);
        while (file.exists())
        {
            fileName = rand.nextInt(20)+".txt";
            file=new File(fileName);
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void appendMethodB(String content) {
            try {
                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                fileWriter = new FileWriter(fileName, true);
                fileWriter.write(content);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void close(){
        if(fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}