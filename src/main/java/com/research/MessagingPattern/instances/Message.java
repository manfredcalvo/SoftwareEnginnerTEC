package com.research.MessagingPattern.instances;

import org.javatuples.Pair;

import java.io.Serializable;

/**
 * Created by mcalvo on 05/09/15.
 */
public class Message implements Serializable{

    private int limit;
    private Pair<Integer, Integer> coordinate;

    public Message(){

    }

    public Message(int limit, Pair<Integer, Integer> coordinate) {
        this.limit = limit;
        this.coordinate = coordinate;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Pair<Integer, Integer> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Pair<Integer, Integer> coordinate) {
        this.coordinate = coordinate;
    }
}
