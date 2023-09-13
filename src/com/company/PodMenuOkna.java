package com.company;

import javax.swing.*;

public class PodMenuOkna extends JPopupMenu{
    JMenuItem anItem;
    //JMenuItem anItem3;
    public PodMenuOkna() {
        anItem = new JMenuItem("Dodaj Punkt");
        //anItem3 = new JMenuItem("Usun Punkt");
        add(anItem);
        //add(anItem3);
    }
}
