package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class PanelGraficzny extends JPanel
{
    BufferedImage plotno;
    ArrayList<Punkt> listaPunktow;
    Przeksztalcenie trans;
    final public DefaultListModel<String> l1 = new DefaultListModel<>();
    final ArrayList<Color> kolorki = new ArrayList<>();
    JLabel macierz = new JLabel("<html>Macierz:<br/>1.0 0.0 0.0<br/>0.0 1.0 0.0<br/>0.0 0.0 1.0</html>");
    int Licznikkoloru=0;
    boolean zmiensrodek=true;
    public PanelGraficzny()
    {
        super();
        macierz.setFont(macierz.getFont().deriveFont(12f));
        macierz.setHorizontalAlignment(JLabel.RIGHT);
        macierz.setVerticalAlignment(JLabel.TOP);
        macierz.setForeground(Color.BLACK);
        add(macierz);
        setLayout(new GridLayout(2,1));
        ustawRozmiar(new Dimension(850,600));
        listaPunktow = new ArrayList<>();
        trans = new Przeksztalcenie();
        wypelnijkolory();
        wyczysc();
    }
    public void dodajPunkt(int x, int y,int index)
    {
        Punkt nowyPunkt = new Punkt(x, y);
        if(index > 0)
        listaPunktow.add(index,nowyPunkt);
        else  listaPunktow.add(nowyPunkt);
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.blue);
        Line2D.Double line = new Line2D.Double(x, y, x, y);
        g.draw(line);
        przenumeruj();
        tmp=centroid();

        if(index > 0)
            l1.add(index,"Punkt["+nowyPunkt+"]");
        else l1.addElement("Punkt["+nowyPunkt+"]");
        repaint();
    }

    public void EdytujPunkt(Punkt tmp, int index)
    {
        if(index != -1) listaPunktow.set(index,tmp);
        przenumeruj();
        l1.remove(index);
        l1.add(index,"P"+index+"["+tmp+"]");
        linia(true);
    }
    public boolean jestnapunkcie(int x, int y){
        for (int i = 0; i < transformacja().size(); i++) {
            if(((int)transformacja().get(i).x) == x && ((int)transformacja().get(i).y == y)) return true;
        }
        return false;
    }

    public void przenumeruj(){
        for (int i = 0; i < l1.size(); i++) {
            String tmp = l1.get(i);
            tmp = tmp.replaceAll("\\bPunkt\\b","P"+i);
            tmp = tmp.replaceAll("\\bP.\\b","P"+i);
            l1.setElementAt(tmp,i);
        }
    }

    public void usunPunkt(int x, int y)
    {
        if(jestnapunkcie(x,y)) {
            Punkt nowyPunkt = new Punkt(x, y);
            Graphics2D g = (Graphics2D) plotno.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(4));
            g.setColor(Color.red);
            Line2D.Double line = new Line2D.Double(x, y, x, y);
            g.draw(line);

            for (int i = 0; i < listaPunktow.size(); i++) {
                if (nowyPunkt.equals(listaPunktow.get(i))) {
                    listaPunktow.remove(new Punkt(x, y));
                    l1.remove(i);
                }
            }
            repaint();
        }
    }
    public void usun(){
        listaPunktow.clear();
        trans = new Przeksztalcenie();
    }

    public void usunPunktz(int index){
        listaPunktow.remove(index);
    }
    Punkt tmp;
    public ArrayList<Punkt> transformacja()
    {
        if(zmiensrodek){
            tmp = centroid();}
        ArrayList<Punkt> resultat = new ArrayList<>();
        for (int i = 0; i < listaPunktow.size(); i++) {
            Punkt current = listaPunktow.get(i);
            Punkt newPoint = new Punkt(current.x, current.y);
                newPoint = trans.transformacja(newPoint);
            resultat.add(newPoint);
        }
        for (int i = 0; i < resultat.size(); i++) {
        l1.set(i,"P"+i+"["+resultat.get(i)+"]");
        }
        return resultat;
    }

    ArrayList<Punkt> listP;

    public void bezier(double deltat,boolean tmps) throws IOException {
        listP = transformacja();
        ArrayList<Punkt> resultat = new ArrayList<>();
        if(listP.size() == 0) return;

        ArrayList<Punkt> listQ = new ArrayList<>();
        if(tmps) wyczysc();
        double m;

        for(double t = 0; t < 1; t+=deltat)
        {
            ArrayList<Punkt> listR = new ArrayList<>();
            for(int i = 0 ; i< listP.size(); i++){
                Punkt tmp = new Punkt(listP.get(i).x, listP.get(i).y);
                listR.add(tmp);
            }
            m = listP.size();
            while(m > 0)
            {
                listQ.clear();
                for(int i = 0; i < m-1; i++)
                {
                    Punkt tmp = new Punkt(0, 0);
                    tmp.x = listR.get(i).x + t * (listR.get(i+1).x - listR.get(i).x);
                    tmp.y = listR.get(i).y + t * (listR.get(i+1).y - listR.get(i).y);
                    listQ.add(tmp);
                }
                m = m - 1;
                for(int i = 0; i < m; i++)
                {
                    Punkt tmp = new Punkt(listQ.get(i).x, listQ.get(i).y);
                    listR.set(i, tmp);
                }
            }
            resultat.add(listR.get(0));
            listQ.clear();
        }

        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.black);

        for(int i = 1; i < resultat.size(); i++)
        {
            Punkt previous = resultat.get(i-1);
            Punkt current = resultat.get(i);

            Line2D.Double line = new Line2D.Double(current.x, current.y, previous.x, previous.y);
            g.draw(line);
            rysujpunktxy((int)current.x,(int)current.y,Color.magenta);
        }
        final BufferedImage image = ImageIO.read(new File("krzyzyk.png"));

        g.drawImage(image,(int)resultat.get(0).x-5,(int)resultat.get(0).y-5,null);
       g.drawImage(image,(int)resultat.get(resultat.size()-1).x-5,(int)resultat.get(resultat.size()-1).y-5,null);
        repaint();
        //listP.clear();
        //listR.clear();
        resultat.clear();
    }
    public void rysujosie(){
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.black);
        Line2D.Double line = new Line2D.Double(plotno.getWidth()/2, 0, plotno.getWidth()/2, plotno.getHeight());
        Line2D.Double line2 = new Line2D.Double(0, plotno.getHeight()/2, plotno.getWidth(), plotno.getHeight()/2);
        g.draw(line);
        g.draw(line2);
    }

    public void rysujobraz(String sciezka,int x , int y) throws IOException {
        linia(true);
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final BufferedImage image = ImageIO.read(new File(sciezka));
        g.drawImage(image,x,y,null);
        repaint();
    }

    public void rysujpunkty(ArrayList<Punkt> tmp){
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.green);
        for (int i = 0; i < tmp.size(); i++) {
            Punkt punkt = tmp.get(i);
            Line2D.Double line = new Line2D.Double(punkt.x, punkt.y, punkt.x, punkt.y);
            g.draw(line);

        }
        for (int i = 0; i < tmp.size(); i++) {
            Punkt punkt = tmp.get(i);
            g.setColor(Color.black);
            g.drawString("P"+i,(float)punkt.x+1,(float)punkt.y+1);
        }
        repaint();
    }

    public void rysujpunkt(int index,Color tmp){

        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(10));
        g.setColor(tmp);

        for (int i = 0; i < listaPunktow.size(); i++) {
            if(index == i) {
                Line2D.Double line = new Line2D.Double(listaPunktow.get(i).x, listaPunktow.get(i).y, listaPunktow.get(i).x, listaPunktow.get(i).y);
                g.draw(line);
            }
        }
        repaint();
    }

    public void rysujpunktxy(int x, int y,Color tmp){

        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(4));//10 ale ustawie na testy
        g.setColor(tmp);
                Line2D.Double line = new Line2D.Double(x, y, x, y);
                g.draw(line);
        repaint();
    }

    public void wypelnijkolory(){
        kolorki.add(Color.BLUE);
        kolorki.add(Color.ORANGE);
        kolorki.add(Color.magenta);
        kolorki.add(Color.cyan);
        kolorki.add(Color.PINK);
    }
    public boolean sprawdznalinii(Point A, Point B, Point C) {
        if (A.x == C.x) return B.x == C.x;
        if (A.y == C.y) return B.y == C.y;
        return (A.x - C.x)*(A.y - C.y) == (C.x - B.x)*(C.y - B.y);
    }

    public void rysujmacierz(){
        String zlepka="<html>Macierz:<br/>";
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++) {
                BigDecimal bd = new BigDecimal(trans.macierz[i][j]).setScale(2, RoundingMode.HALF_UP);
                zlepka += bd.doubleValue() + " ";
            }
            zlepka+="<br/>";
        }
        zlepka+="</html>";
        macierz.setText(zlepka);
    }

    public void usunmacierz(){
        macierz.setText("<html>Macierz:<br/>0.0 0.0 0.0<br/>0.0 0.0 0.0<br/>0.0 0.0 0.0</html>");
    }

    public Punkt centroid()  {
        double centroidX = 0, centroidY = 0;

        for(Punkt obecny : listaPunktow) {
            centroidX += obecny.x;
            centroidY += obecny.y;
        }
        return new Punkt(centroidX / listaPunktow.size(), centroidY / listaPunktow.size());
    }

    public void linia(boolean tmp)
    {
        ArrayList<Punkt> listaWejsciowa = transformacja();
        if(listaWejsciowa.size() ==0) return;
        if (tmp) wyczysc();
        rysujosie();
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(2));
       // if (Licznikkoloru == 5)
            Licznikkoloru=2;
        g.setColor(kolorki.get(Licznikkoloru));

        for(int i = 1; i < listaWejsciowa.size(); i++)
        {
            Punkt previous = listaWejsciowa.get(i-1);
            Punkt current = listaWejsciowa.get(i);

            Line2D.Double line = new Line2D.Double(current.x, current.y, previous.x, previous.y);
            g.draw(line);
        }
        rysujpunkty(transformacja());
        rysujpunktxy((int)centroid().x,(int)centroid().y,Color.red);
        przenumeruj();
        repaint();
    }

    public void wyczysc()
    {
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        setBorder(BorderFactory.createLineBorder(Color.black));
        rysujosie();
        repaint();
    }

    public void ustawRozmiar(Dimension r)
    {
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);
        setMaximumSize(r);
        setMinimumSize(new Dimension(800,570));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(plotno, 0, 0, this);
        rysujosie();
    }
}
