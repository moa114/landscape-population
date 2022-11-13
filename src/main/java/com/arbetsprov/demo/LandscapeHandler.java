package com.arbetsprov.demo;

import org.json.JSONArray;

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

    /**
     * enter population statistic for year 2017, 2018 and 2019 for a landscape
     * @param landscape the landscape to enter statistic for
     */
    protected void enterPopulationStatistic(Landscape landscape, JSONArray allPopulationStatistic ){
        for(int i =0; i<allPopulationStatistic.length();i++){
            JSONArray landscapeKey= (JSONArray) allPopulationStatistic.getJSONObject(i).get("key");
            if(Integer.parseInt( (String) landscapeKey.get(0))==landscape.getScbId()){
                JSONArray landscapeValue= (JSONArray) allPopulationStatistic.getJSONObject(i).get("values");
                if((landscapeKey.get(1)).equals("2017")){
                    landscape.setPopulation17(Integer.parseInt((String) landscapeValue.get(0)));
                }
                else if(landscapeKey.get(1).equals("2018")) {
                    landscape.setPopulation18(Integer.parseInt((String) landscapeValue.get(0)));
                }
                else if(landscapeKey.get(1).equals("2019")) {
                    landscape.setPopulation19(Integer.parseInt((String) landscapeValue.get(0)));
                    return;
                }
            }
        }
    }

}
