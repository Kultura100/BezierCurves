package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class MojeOkno extends JFrame implements ActionListener, MouseListener, MouseMotionListener, ListSelectionListener
{
    final PanelGraficzny panel = new PanelGraficzny();
    final MenuOkna menu = new MenuOkna();
    final JList<String> list = new JList<>(panel.l1);
    JButton b1 = new JButton("Tryb Edycji");
    JLabel napis = new JLabel("Edytowanie: ");
    JLabel napisx = new JLabel("x:");
    JTextField polex = new JTextField(3);
    JLabel napisy = new JLabel("y: ");
    JTextField poley = new JTextField(3);
    JButton b2 = new JButton("Zmien Aktualny");
    JLabel napis2 = new JLabel("Dodaj w miejsce:");
    JLabel napis3 = new JLabel("Indeks:");
    JTextField polex2 = new JTextField(3);
    JTextField poley2 = new JTextField(3);
    JTextField poleindex = new JTextField(3);
    JButton b3 = new JButton("Dodaj");
    JScrollPane scrollPane = new JScrollPane();
    PodMenuOkna menuu = new PodMenuOkna();
    boolean trybedytowania;
    Punkt edytowany;
    int indekss=-1;
    boolean wskazano=false;
    boolean zakaztrybu=false;
    public MojeOkno() {
        super("Grafika komputerowa Krzywe Beziera");
        trybedytowania=false;
        menuu.anItem.addActionListener(this);
        //menuu.anItem3.addActionListener(this);
        setPreferredSize(new Dimension(1024,670));
        JPanel roboczy = new JPanel();
        JPanel roboczy2 = new JPanel();
        roboczy2.setLayout(new FlowLayout());
        roboczy.setLayout(new BoxLayout(roboczy,BoxLayout.PAGE_AXIS));
        roboczy.setMinimumSize(new Dimension(150,0));
        list.setLayoutOrientation(JList.VERTICAL);
        list.addListSelectionListener(this);
        scrollPane.setViewportView(list);
        polex.setMaximumSize(new Dimension(40,20));
        roboczy.add(scrollPane);
        roboczy2.add(b1);
        roboczy2.add(napis);
        roboczy2.add(napisx);
        roboczy2.add(polex);
        roboczy2.add(napisy);
        roboczy2.add(poley);
        roboczy2.add(b2);
        roboczy2.add(napis2);
        roboczy2.add(napisx);
        roboczy2.add(polex2);
        roboczy2.add(napisy);
        roboczy2.add(poley2);
        roboczy2.add(napis3);
        roboczy2.add(poleindex);
        roboczy2.add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        napis.setVisible(false);
        polex.setVisible(false);
        napisx.setVisible(false);
        napisy.setVisible(false);
        poley.setVisible(false);
        napis2.setVisible(false);
        poley2.setVisible(false);
        polex2.setVisible(false);
        poleindex.setVisible(false);
        napis3.setVisible(false);
        b2.setVisible(false);
        b3.setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable (false);
        setJMenuBar(menu);
        JSplitPane podzielenie = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel,roboczy);
        JSplitPane podzielenied = new JSplitPane(JSplitPane.VERTICAL_SPLIT,podzielenie,roboczy2);
        podzielenied.setPreferredSize(new Dimension(200,200));
        podzielenie.setDividerSize(0);
        podzielenied.setDividerSize(0);
        this.getContentPane().add(podzielenied);
        ustawNasluchZdarzen();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void trybedycjioff(){
        trybedytowania = false;
        napis.setText("Edytowanie: brak");
        polex.setText("");
        poley.setText("");
        indekss = -1;
        napis.setVisible(false);
        polex.setVisible(false);
        poley.setVisible(false);
        napisx.setVisible(false);
        napisy.setVisible(false);
        napis2.setVisible(false);
        poley2.setVisible(false);
        polex2.setVisible(false);
        poleindex.setVisible(false);
        napis3.setVisible(false);
        b2.setVisible(false);
        b3.setVisible(false);
    }

    private void ustawNasluchZdarzen()
    {
        menu.t.addActionListener(this);
        menu.stopnie.addActionListener(this);
        menu.skal_x.addActionListener(this);
        menu.skal_y.addActionListener(this);
        menu.przes_x.addActionListener(this);
        menu.przes_y.addActionListener(this);
        menu.linie.addActionListener(this);
        menu.usun.addActionListener(this);
        menu.usunjedno.addActionListener(this);
        menu.liniebezier.addActionListener(this);
        menu.Wyjdz.addActionListener(this);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object zrodlo = e.getSource();
        if(zrodlo == menu.t)
        {
            trybedycjioff();
            try{
                if(menu.t.getText().equals(""))
                    panel.bezier(0.01,true);
                else
                {
                    //panel.wyczysc();
                    panel.bezier(Double.parseDouble(menu.t.getText()),true);
                }
            }catch (NumberFormatException | IOException ev) {
                JOptionPane.showMessageDialog(null, "Napisz poprawnie wartosci!", "Bląd", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (zrodlo == menu.liniebezier)
        {
            trybedycjioff();
            if(menu.t.getText().equals("")) {
                try {
                    panel.linia(false);
                    panel.bezier(0.01, false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
                else {
                try {
                    panel.linia(false);
                    panel.bezier(Double.parseDouble(menu.t.getText()),false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                }
        }
        else if(zrodlo == menu.stopnie)
        {
            try
            {
                double a = Double.parseDouble(menu.stopnie.getText());
                panel.trans.obroc(a);
                panel.linia(false);
                //panel.l1.clear();
                panel.rysujmacierz();
                zakaztrybu=true;
            }
            catch (NumberFormatException ev)
            {
                JOptionPane.showMessageDialog(null, "Napisz poprawnie wartosci!", "Bląd", JOptionPane.ERROR_MESSAGE);
            }

        }
        else if(zrodlo == menu.skal_y || zrodlo == menu.skal_x)
        {
            try
            {
                panel.trans.skaluj(Double.parseDouble(menu.skal_x.getText()), Double.parseDouble(menu.skal_y.getText()));
                panel.linia(false);
                panel.rysujmacierz();
                zakaztrybu=true;
            }
            catch (NumberFormatException ev)
            {
                JOptionPane.showMessageDialog(null, "Napisz poprawnie wartosci!", "Bląd", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(zrodlo == menu.przes_y || zrodlo == menu.przes_x)
        {
            try
            {
                panel.trans.przesun(Double.parseDouble(menu.przes_x.getText()), Double.parseDouble(menu.przes_y.getText()));
                panel.linia(false);
                //panel.l1.clear();
                panel.rysujmacierz();
                zakaztrybu=true;
            }
            catch (NumberFormatException ev)
            {
                JOptionPane.showMessageDialog(null, "Napisz poprawnie wartosci!", "Bląd", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(zrodlo == menu.linie)
        {
            panel.linia(true);
        }
        else if(zrodlo == menu.usun)
        {
            panel.wyczysc();
            panel.usun();
            panel.l1.clear();
            panel.usunmacierz();
            trybedytowania=false;
                    napis.setText("Edytowanie: brak");
            polex.setText("");
            poley.setText("");
            indekss=-1;
            zakaztrybu=false;
        }
        else if(zrodlo == menu.usunjedno)
        {
            if(list.getSelectedIndex() != -1) {
                panel.usunPunktz(list.getSelectedIndex());
                panel.l1.remove(list.getSelectedIndex());
                panel.rysujpunkt(list.getSelectedIndex(),Color.red);
                panel.linia(true);
            }
        }
        else if(zrodlo == menu.Wyjdz) System.exit(0);
        else if (zrodlo == b1) {
            if(!zakaztrybu) {
                napis.setVisible(true);
                polex.setVisible(true);
                poley.setVisible(true);
                napisx.setVisible(true);
                napisy.setVisible(true);
                napis2.setVisible(true);
                poley2.setVisible(true);
                polex2.setVisible(true);
                poleindex.setVisible(true);
                napis3.setVisible(true);
                b2.setVisible(true);
                b3.setVisible(true);
                if (trybedytowania) {
                     trybedycjioff();
                } else trybedytowania = true;
            }
        }
        else if(zrodlo == b2){
            try{
                panel.EdytujPunkt(new Punkt(Double.parseDouble(polex.getText()), Double.parseDouble(poley.getText())), indekss);
            }
            catch (NumberFormatException ev)
        {
            JOptionPane.showMessageDialog(null, "Napisz poprawnie wartosci!", "Bląd", JOptionPane.ERROR_MESSAGE);
        }
        }
        else if(zrodlo == b3){
            try{
                panel.dodajPunkt(Integer.parseInt(polex2.getText()),Integer.parseInt(poley2.getText()),Integer.parseInt(poleindex.getText()));
                panel.linia(true);
            }
            catch (NumberFormatException ev)
            {
                JOptionPane.showMessageDialog(null, "Napisz poprawnie wartosci!", "Bląd", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(zrodlo == menuu.anItem)
        {
            panel.dodajPunkt((int)edytowany.x, (int)edytowany.y, 0);
        }

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(!zakaztrybu)
        if (e.isPopupTrigger())
            doPop(e);

        if(!trybedytowania) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                panel.dodajPunkt(e.getX(), e.getY(), 0);
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                panel.usunPunkt(e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(trybedytowania) {
            polex2.setText(String.valueOf(e.getX()));
            poley2.setText(String.valueOf(e.getY()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(wskazano) wskazano=false;
        if(!zakaztrybu)
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e) {
        PodMenuOkna menu = menuu;
        menu.show(e.getComponent(), e.getX(), e.getY());
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if(trybedytowania) {
            if (wskazano) {
                for (int i = 0; i < panel.transformacja().size(); i++) {
                    if (panel.transformacja().get(i).x == e.getX() && panel.transformacja().get(i).y == e.getY()) {
                        indekss = i;
                        panel.zmiensrodek=true;
                        break;
                    }
                }

                panel.zmiensrodek = false;
                panel.EdytujPunkt(new Punkt(e.getX(), e.getY()), indekss);
            }
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        edytowany = new Punkt(e.getX(),e.getY());
        if(trybedytowania) {
            if (panel.jestnapunkcie(e.getX(), e.getY())) {
                for (int i = 0; i < panel.transformacja().size(); i++) {
                    if ((int)panel.transformacja().get(i).x == e.getX() && (int)panel.transformacja().get(i).y == e.getY()) {
                        indekss = i;
                        wskazano=true;
                        napis.setText("Edytowanie: P" + i + "[" + panel.transformacja().get(i) + "]");
                        polex.setText(String.valueOf(panel.transformacja().get(i).x));
                        poley.setText(String.valueOf(panel.transformacja().get(i).y));
                        panel.rysujpunktxy(e.getX(), e.getY(), Color.magenta);
                    }
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()){
            JList source = (JList)e.getSource();
            int indeks = source.getSelectedIndex();
            if(indeks != -1){
                try {
                    panel.rysujobraz("pierscien.png",(int)panel.transformacja().get(indeks).x-7,(int)panel.transformacja().get(indeks).y-7);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}

