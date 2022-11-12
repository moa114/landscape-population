package com.arbetsprov.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Managing the landscapes. Has a list of all landscapes.
 * Used by SCBReader
 */
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

    protected void createLandscape(String name, int scb_index) {
        if(!landscapeList.contains(getLandscape(name))){
            Landscape landscape=new Landscape(name, scb_index);
            landscapeList.add(landscape);
        }
    }

    public List<Landscape> getLandscapeList() {
        return landscapeList;
    }

    /**
     * Gives the landscape object with a certain name
     * @param name the name of the desired landscape
     * @return landscape object if one exists with the corresponding name, otherwise null
     */
    public Landscape getLandscape(String name) {
        for (Landscape l : landscapeList) {
            if (l.getName().equals(name))
                return l;
        }
      return null;
    }

}
