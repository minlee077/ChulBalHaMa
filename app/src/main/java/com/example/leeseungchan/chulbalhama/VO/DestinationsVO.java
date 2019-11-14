package com.example.leeseungchan.chulbalhama.VO;

import com.example.leeseungchan.chulbalhama.Adpater.DestinationAdapter;

public class DestinationsVO {

    int id;
    String coordinate;
    String time;
    String destinationName;

    public DestinationsVO(){};

    public String getDestinationName(){
        return destinationName;
    }

    public void setDestinationName(String name){
        destinationName = name;
    }

}