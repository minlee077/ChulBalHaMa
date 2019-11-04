package com.example.leeseungchan.chulbalhama.VO;

public class HabitsVO {
    int id;
    String habitName;
    String description;
    String prepare;
    int achievementRate;

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
