package com.example.moodiary;

public class EntryActivity {
    private String  act_name;
    private Integer act_icon;

    public EntryActivity(String act_name, Integer act_icon) {
        this.act_name = act_name;
        this.act_icon = act_icon;
    }

    public String getAct_name() {
        return act_name;
    }

    public Integer getAct_icon() {
        return act_icon;
    }
}
