package com.example.leeseungchan.chulbalhama.VO;

import java.io.Serializable;

public class HabitsVO implements Serializable {
    int id;
    String habitName;
    String description;
    String prepare;
    int achievementRate;

    public HabitsVO(){}

    public HabitsVO(int id, String habitName, String habitDesc, String prepare, int achievementRate){
        this.id = id;
        this.habitName = habitName;
        this.description = habitDesc;
        this.prepare = prepare;
        this.achievementRate = achievementRate;
    }

    public int getId(){
        return id;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }
}
