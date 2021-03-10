package com.jangzzang.calculatorpro;

import android.widget.EditText;

public class School_item {

    private String name;
    private String score1;
    private String score2;

    private int viewtype;

    public School_item(String name, String score1, String score2, int viewtype) {
        this.name = name;
        this.score1 = score1;
        this.score2 = score2;
        this.viewtype = viewtype;
    }

    public int getViewtype() {
        return viewtype;
    }

    public void setViewtype(int viewtype) {
        this.viewtype = viewtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }
}
