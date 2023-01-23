package com.yelp.supportclasses;

import javax.swing.*;

public class NewFrame extends JFrame{

    JFrame mainFrame;
    JScrollPane mainScrollPAne;
    JTable ourTable;

    public NewFrame() {
        mainFrame = new JFrame();
        mainScrollPAne = new JScrollPane();
        ourTable = new JTable();

    }

    public void makeAndDisplayTable(String[] columns, String[][] rowsData, String title)
    {
        mainScrollPAne= new JScrollPane();
        add(mainScrollPAne);
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(title);

        ourTable = new JTable(rowsData, columns) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        } ;

        ourTable.setFocusable(false);

        mainScrollPAne.setViewportView(ourTable);
        mainScrollPAne.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPAne.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    }

}
