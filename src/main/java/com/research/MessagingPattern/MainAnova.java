package com.research.MessagingPattern;

import com.research.MessagingPattern.utils.Calculations;
import com.research.MessagingPattern.utils.Sample;

/**
 * Created by raquelrodriguezchaves on 23/10/15.
 */
public class MainAnova {

    public static void main(String []args) {
        String direccionPattern = "/Users/raquelrodriguezchaves/resultsPattern10MilV1.csv";
        String direccionNoPattern = "/Users/raquelrodriguezchaves/resultsNoPattern10MilV1.csv";
        Sample pattern = new Sample(direccionPattern);
        Sample noPattern = new Sample(direccionNoPattern);

        int samples = 2;
        int observations = 100;
        Calculations calculus = new Calculations(samples,observations);
        double internalEstimateVariance = calculus.internalEstimateVariance(pattern.variance(), noPattern.variance());
        double varianceArithmeticMeans = calculus.varianceArithmeticMeans
                (pattern.variance(), noPattern.variance(), internalEstimateVariance);
        double intermediateEstVariance = calculus.intermediateEstVariance(varianceArithmeticMeans);
        double FDePrueba = calculus.FDePrueba(intermediateEstVariance,internalEstimateVariance);
        System.out.println("F de prueba: " + FDePrueba);
        System.out.println("Grados de libertad numerador: " +calculus.gradosLibertadNumerador());
        System.out.println("Grados de libertad denominador: " +calculus.gradosLibertadDenominador());


    }

}
