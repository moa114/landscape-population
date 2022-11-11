package com.arbetsprov.demo;

import java.util.ArrayList;
import java.util.List;

public class LandscapeHandler {
    private static LandscapeHandler singleInstance;
    private final List<Landscape> landscapeList;

    private LandscapeHandler() {
        this.landscapeList = new ArrayList<>();
    }

    /**
     * Gets the instance of the LandscapeHandler which handles all landscapes
     *
     * @return the single instance of the LandscapeHandler
     */
    public static LandscapeHandler getInstance() {
        if (singleInstance == null)
            singleInstance = new LandscapeHandler();
        return singleInstance;
    }

    public List<Landscape> getLandscapeList() {
        return landscapeList;
    }

    protected void calculatePercentageChange(){
        for(Landscape l: landscapeList){
            double d= (double) l.getPopulation19()/l.getPopulation17();
            l.setPercentage_change_17_19((double) Math.round(d*100)/100);
        }
    }

    public Landscape getLandscape(String name) {
        for (Landscape l : landscapeList) {
            if (l.getName().equals(name))
                return l;
        }
        System.out.println("invalid name");
        return null;
    }

}
