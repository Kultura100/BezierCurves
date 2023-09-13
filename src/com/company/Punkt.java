package com.company;

public class Punkt {

    public double x;
    public double y;
    public Punkt(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    public String toString()
    {
        return ("x: "+x + " y: "+y);
    }
    public boolean equals (Object o) {
        Punkt tmp = (Punkt) o;
        return tmp.x == x;
    }

}
