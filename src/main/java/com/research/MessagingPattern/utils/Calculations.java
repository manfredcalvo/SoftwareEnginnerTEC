package com.research.MessagingPattern.utils;


import hep.aida.bin.DynamicBin1D;

/**
 * Created by raquelrodriguezchaves on 21/10/15.
 */
public class Calculations {

    private  int k; // k IS THE NUMBER OF SAMPLES
    private  int n; // n IS THE NUMBER OF OBSERVATIONS

    public Calculations(int k, int n){
        this.n = n;
        this.k = k;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getN() {
        return this.n;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getK() {
        return this.k;
    }

    public double internalEstimateVariance(double variance1,double variance2) {
        double internalEstVarianc = (variance1 + variance2)/k;
        return internalEstVarianc;
    }


    public double varianceArithmeticMeans (double variance1,double variance2, double internalEstVarianc) {
        double value1 = (variance1 - internalEstVarianc);
        double value2 = (variance2 - internalEstVarianc);
        double result = ((value1 * value1) + (value2 * value2))/(k-1);
        return result;
    }

    public double intermediateEstVariance(double varianceArithmeticMeans) {
        double result = n * varianceArithmeticMeans;
        return result;
    }

    public int gradosLibertadNumerador() {
        return k - 1;
    }

    public int gradosLibertadDenominador() {
        return k*(n- 1);
    }


    public double FDePrueba(double intermediateEstVariance, double internalEstimateVariance) {
        double FPrueba = 0;
        if (internalEstimateVariance > 0){
            FPrueba = intermediateEstVariance / internalEstimateVariance;
        }
        return FPrueba;
    }

}
