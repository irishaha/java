package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Menu {
    private Field field = null;
    private Ground ground = null;
    JMenuBar menuBar = new JMenuBar();
    private JMenu menu1 = null;
    private JMenu menu2 = null;
    private JMenuItem menuItem11 = null,menuItem12 = null,menuItem13 = null,menuItem14 = null;
    private JMenuItem menuItem21 = null,menuItem22 = null,menuItem23 = null;

    public Menu( Field field,Ground ground){
        this.field = field;
        this.ground = ground;

        //创建并添加各菜单，注意：菜单的快捷键是同时按下Alt键和字母键，方法setMnemonic('F')是设置快捷键为Alt +Ｆ
        this.menu1 = new JMenu("游戏(G)");
        this.menu2 = new JMenu("选项(E)");
        menu1.setMnemonic('G');
        menu2.setMnemonic('E');
        menuBar.add(menu1);
        menuBar.add(menu2);

        this.menuItem11 = new JMenuItem("开始游戏");
        this.menuItem12 = new JMenuItem("读档回放");
        this.menuItem13 = new JMenuItem("规则说明");
        this.menuItem14 = new JMenuItem("退出游戏");
        menuItem11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_MASK));
        menuItem12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        menuItem13.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        menuItem14.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        menu1.add(menuItem11);
        menu1.add(menuItem12);
        menu1.add(menuItem13);
        menu1.add(menuItem14);

        this.menuItem21 = new JMenuItem("暂停");
        this.menuItem22 = new JMenuItem("继续");
        this.menuItem23 = new JMenuItem("变换阵型");
        menuItem21.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        menuItem22.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        menu2.add(menuItem21);
        menu2.add(menuItem22);
        menu2.add(menuItem23);

        addMenuListenner();
    }
    public void addMenuListenner(){

        menuItem11.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                field.gameStart();
                field.repaint();
            }
        });
        menuItem12.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                field.gameReplay();
                field.repaint();
            }
        });
        menuItem13.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                field.helpFile();
            }
        });
        menuItem14.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                field.exit();
            }
        });

        menuItem21.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    field.gameStop();

            }
        });
        menuItem22.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                field.gameContinue();
            }
        });
        menuItem23.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(field.getState() != State.Playing){
                    field.restartLevel();
                    field.repaint();
                }
            }
        });
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
