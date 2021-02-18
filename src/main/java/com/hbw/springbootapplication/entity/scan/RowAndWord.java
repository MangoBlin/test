package com.hbw.springbootapplication.entity.scan;


import java.io.Serializable;

public class RowAndWord implements Serializable{
    private String row;
    private String word;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
