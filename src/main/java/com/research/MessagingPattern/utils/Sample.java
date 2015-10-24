package com.research.MessagingPattern.utils;

import hep.aida.bin.DynamicBin1D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by raquelrodriguezchaves on 21/10/15.
 */
public class Sample {

    private DynamicBin1D values;

    public Sample(String fileName){
        values = new DynamicBin1D();
        this.readFile (fileName);
    }

    public double variance() {
        return values.variance();
    }

    public void setValues(DynamicBin1D values) {
        this.values = values;
    }

    public DynamicBin1D getValues() {
        return values;
    }


    public void readFile(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName));){
            String linea = reader.readLine();
            double value;
            while(linea!=null){
                value =  Double.parseDouble(linea);
                values.add(value);
                linea= reader.readLine();
            }
        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }
    }


}

