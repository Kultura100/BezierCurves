package com.company;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JTextField;

public class MenuOkna extends JMenuBar
{
    final JMenu operacje = new JMenu("Bezier");
    final JMenu Mobrot = new JMenu("Obracanie");
    final JMenu Mscalowanie = new JMenu("Zmiana Rozmiaru");
    final JMenu Mprzesuniecie = new JMenu("Przesuniecie");

    final JLabel bezier = new JLabel("Bezier t: ");
    final JTextField t = new JTextField(3);

    final JLabel obrot = new JLabel("Stopnie: ");
    final JTextField stopnie = new JTextField(3);

    final JLabel skalowanie = new JLabel("x: ");
    final JTextField skal_x = new JTextField(3);
    final JLabel skalowanie_2 = new JLabel(" y: ");
    final JTextField skal_y = new JTextField(3);

    final JLabel przesuniecie = new JLabel("x: ");
    final JTextField przes_x = new JTextField(3);
    final JLabel przesuniecie_2 = new JLabel(" y: ");
    final JTextField przes_y = new JTextField(3);

    final JMenuItem linie = new JMenuItem("Dorysuj linie łamane");
    final JMenuItem liniebezier = new JMenuItem("Dorysuj linie łamane i Beziera");

    final JMenu tablica = new JMenu("Lista punktów");
    final JMenuItem usun = new JMenuItem("Usuń");
    final JMenuItem usunjedno = new JMenuItem("Usuń wybrany z listy");
    final JMenu opcje = new JMenu("Opcje");
    final JMenuItem Wyjdz = new JMenuItem("Wyjscie");
    public MenuOkna()
    {
        JPanel rozklad = new JPanel();
        rozklad.add(bezier);
        rozklad.add(t);
        JPanel rozklad_2 = new JPanel();
        rozklad_2.add(obrot);
        rozklad_2.add(stopnie);
        JPanel rozklad_3 = new JPanel();
        rozklad_3.add(skalowanie);
        rozklad_3.add(skal_x);
        rozklad_3.add(skalowanie_2);
        rozklad_3.add(skal_y);
        JPanel rozklad_4 = new JPanel();
        rozklad_4.add(przesuniecie);
        rozklad_4.add(przes_x);
        rozklad_4.add(przesuniecie_2);
        rozklad_4.add(przes_y);
        operacje.add(rozklad);
        operacje.addSeparator();
        operacje.add(linie);
        operacje.add(liniebezier);
        Mobrot.add(rozklad_2);
        Mscalowanie.add(rozklad_3);
        Mprzesuniecie.add(rozklad_4);
        add(operacje);
        add(Mobrot);
        add(Mscalowanie);
        add(Mprzesuniecie);
        tablica.add(usun);
        tablica.add(usunjedno);
        add(tablica);
        opcje.add(Wyjdz);
        add(opcje);
    }
}

