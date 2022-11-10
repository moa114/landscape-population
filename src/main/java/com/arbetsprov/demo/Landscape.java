package com.arbetsprov.demo;

import javax.annotation.Nonnull;

public class Landscape {


    private int population17;
    private int population18;
    private int population19;
    private final String name;
    private int scb_index;
    private double percentage_change_17_19;

    public Landscape(@Nonnull String name, @Nonnull int index) {
        this.name = name;
        this.scb_index=index;
    }

    public Integer getPopulation17() {
        return population17;
    }

    public void setPopulation17(Integer population17) {
        this.population17 = population17;
    }

    public Integer getPopulation18() {
        return population18;
    }

    public void setPopulation18(Integer population18) {
        this.population18 = population18;
    }

    public Integer getPopulation19() {
        return population19;
    }

    public void setPopulation19(Integer population19) {
        this.population19 = population19;
    }

    public int getScb_index() {
        return scb_index;
    }
    public double getPercentage_change_17_19() {
        return percentage_change_17_19;
    }

    public void setPercentage_change_17_19(double percentage_change_17_19) {
        this.percentage_change_17_19 = percentage_change_17_19;
    }
    @Nonnull
    public String getName() {
        return name;
    }




}
