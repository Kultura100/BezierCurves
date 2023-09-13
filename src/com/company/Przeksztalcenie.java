package com.company;

import java.lang.Math;
public class Przeksztalcenie
{
    double[][] macierz;
    public Przeksztalcenie()
    {
        macierz = new double[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                macierz[i][j] = 0;
        macierz[0][0] = 1;
        macierz[1][1] = 1;
        macierz[2][2] = 1;
    }
    public void obroc(double kat)
    {
        double[][] macierztmp = new double[3][3];
        kat = Math.toRadians(kat);
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                macierztmp[i][j] = 0;
        macierztmp[0][0] = Math.cos(kat);
        macierztmp[0][1] = Math.sin(kat);
        macierztmp[0][2] = 0;
        macierztmp[1][0] = -1*Math.sin(kat);
        macierztmp[1][1] = Math.cos(kat);
        macierztmp[1][2] = 0;
        macierztmp[2][2] = 1;
        multiplyMain(macierztmp);
    }

    public void skaluj(double x, double y)
    {
        double[][] macierztmp = new double[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                macierztmp[i][j] = 0;
        macierztmp[0][0] = x;
        macierztmp[1][1] = y;
        macierztmp[2][2] = 1;

        multiplyMain(macierztmp);
    }

    public void przesun(double x, double y)
    {
        double[][] macierztmp = new double[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                macierztmp[i][j] = 0;
        macierztmp[0][0] = 1;
        macierztmp[1][1] = 1;
        macierztmp[2][0] = x;
        macierztmp[2][1] = y;
        macierztmp[2][2] = 1;

        multiplyMain(macierztmp);
    }

    public Punkt transformacja(Punkt punkt)
    {
        double[] wektor = new double[3];
        wektor[0] = punkt.x;
        wektor[1] = punkt.y;
        wektor[2] = 1;
        punkt.x = wektor[0] * macierz[0][0] + wektor[1] * macierz[1][0] + wektor[2] * macierz[2][0];
        punkt.y = wektor[0] * macierz[0][1] + wektor[1] * macierz[1][1] + wektor[2] * macierz[2][1];
        punkt.x = (int) punkt.x;
        punkt.y = (int) punkt.y;
        return punkt;
    }

    private void multiplyMain(double[][] dru)
    {
        double[][] resultat = new double[3][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                resultat[i][j] = 0;

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++)
                    resultat[i][j] += macierz[i][k] * dru[k][j];

        macierz = resultat;
/*
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
                System.out.print(macierz[i][j]+" ");
            System.out.println();
        }
        */
    }
}
