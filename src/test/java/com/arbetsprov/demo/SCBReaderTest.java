package com.arbetsprov.demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SCBReaderTest {
    List<Landscape> landscapeList;
    List<String> landscapeNames;
    LandscapeHandler landscapeHandler;

    @Before
    public void before() throws IOException {
        landscapeList=SCBReader.getData();
        landscapeNames=new ArrayList<>();
        landscapeHandler=LandscapeHandler.getInstance();
        for (Landscape l: landscapeList) {
            landscapeNames.add(l.getName());
        }
    }

    @Test
    public void testRetrievesAllLandscapes() {
        String[] all_landscape_name={"Lappland","Norrbotten","Västerbotten","Jämtland","Ångermanland","Härjedalen","Medelpad","Hälsingland","Gästrikland","Dalarna","Värmland"};        //should inlude all landscape names..
        for(String s: all_landscape_name){
            assertTrue(landscapeNames.contains(s));
        }
        assertEquals(landscapeList.size(),25);
    }

    //should be tested more exhaustive
    @Test
    public void testAddsCorrectPopulationData(){
        Landscape norrbotten= landscapeHandler.getLandscape("Norrbotten");
        int norrbottenPopulation17=norrbotten.getPopulation17();
        Landscape jamtland= landscapeHandler.getLandscape("Jämtland");
        int jamtlandPopulation19=jamtland.getPopulation19();
        Landscape gotland= landscapeHandler.getLandscape("Gotland");
        int gotlandPopulation18=gotland.getPopulation18();

        assertEquals(norrbottenPopulation17,196012);
        assertEquals(jamtlandPopulation19,117688);
        assertEquals(gotlandPopulation18,59249);


    }
}
