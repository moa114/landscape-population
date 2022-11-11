package com.arbetsprov.demo;

import javax.annotation.Nonnull;

public class Landscape {

    private int population17;
    private int population18;
    private int population19;
    private final String name;
    private final int scbIndex;
    private double percentageChange17to19;

    public Landscape(@Nonnull String name, int index) {
        this.name = name;
        this.scbIndex=index;
    }

    public Integer getPopulation17() { return population17; }

    public int getScbIndex() { return scbIndex;}

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

    public double getPercentageChange17to19() {
        return percentageChange17to19;
    }

    public void setPercentageChange17to19(double percentageChange17to19) {
        this.percentageChange17to19 = percentageChange17to19;
    }
    @Nonnull
    public String getName() {
        return name;
    }




}
