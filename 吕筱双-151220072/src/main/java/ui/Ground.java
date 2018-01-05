package ui;

import javax.swing.*;

public final class Ground extends JFrame {

    private final int OFFSET = 30;
    public Ground() {
        InitUI();
    }

    public void InitUI() {
        Field field = new Field();
        Menu menu = new Menu(field,this);
        setJMenuBar(menu.getMenuBar());
        add(field);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth(),
                field.getBoardHeight());
        setLocationRelativeTo(null);
        setTitle("葫芦娃大战妖精");
    }
}