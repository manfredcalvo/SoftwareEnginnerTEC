package com.research.MessagingPattern.instances;

import org.javatuples.Pair;

import java.io.Serializable;

/**
 * Created by mcalvo on 05/09/15.
 */
public class Result implements Serializable {

    private Pair<Integer, Integer> coordinate;
    private double value;

    public Result(){

    }

    public Result(Pair<Integer, Integer> coordinate, double value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public Pair<Integer, Integer> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Pair<Integer, Integer> coordinate) {
        this.coordinate = coordinate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getX(){
        return coordinate.getValue0();
    }
    public int getY(){

        return coordinate.getValue1();

    }




}
