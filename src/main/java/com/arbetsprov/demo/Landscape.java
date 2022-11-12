package com.arbetsprov.demo;

import javax.annotation.Nonnull;

/**
 * Represents a landscape with a name, an index in the SCB database, and population statistics for year 2017,2018 and 2019.
 * It also has a derived percentage change from year 2017 to 2019.
 * Used by LandscapeHandler
 */
public class Landscape {

    private final String name;
    private final int scbIndex;
    //the below variables are non-primitive since I want them to be able to be null and not 0 when empty, since 0 has another meaning in the data
    private Integer population17;
    private Integer population18;
    private Integer population19;
    private Double percentageChange17to19;

    public Landscape(@Nonnull String name, int index) {
        this.name = name;
        this.scbIndex=index;
    }

    public Integer getPopulation17() { return population17; }

    public int getScbIndex() { return scbIndex;}

    public void setPopulation17(Integer population17) {
        this.population17 = population17;
        setPercentageChange17to19();
    }

    public Integer getPopulation18() {
        return population18;
    }

    public void setPopulation18(Integer population18) {
        this.population18 = population18;
    }

    public Integer getPopulation19() { return population19;}

    public void setPopulation19(Integer population19) {
        this.population19 = population19;
        setPercentageChange17to19();
    }

    public Double getPercentageChange17to19() {
        return percentageChange17to19;
    }

    /**
     * Calculates and sets the percentage change in population based on data from year 2017 and 2019.
     * This method is called every time the related data variables are updated.
     * If any of the needed population data fields is null, the percentage change is null as well.
     * If population for year 2017 (the denominator) is 0, the change is null.
     */
    public void setPercentageChange17to19() {
        if(population17 != null && population19 !=null && population17!=0) {
            double d = (double) population19/ population17;
            percentageChange17to19 = (double) Math.round(d * 100) / 100;
        }
        else {
            percentageChange17to19 = null;
        }
    }

    @Nonnull
    public String getName() {
        return name;
    }

}
