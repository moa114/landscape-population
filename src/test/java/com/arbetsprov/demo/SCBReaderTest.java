package com.arbetsprov.demo;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SCBReaderTest {
    List<Landscape> landscapeList;
    LandscapeHandler landscapeHandler;

    @Before
    public void before() throws IOException {
        SCBReader.getData();
        landscapeHandler=LandscapeHandler.getInstance();
        landscapeList=LandscapeHandler.getInstance().getLandscapeList();
    }

    @Test
    public void testRetrievesAllLandscapes() {
        List<String> landscapeNames=new ArrayList<>();
        for (Landscape l: landscapeList) {
            landscapeNames.add(l.getName());
        }

        String[] allLandscapeNames={"Lappland","Norrbotten","Västerbotten","Jämtland","Ångermanland","Härjedalen","Medelpad","Hälsingland","Gästrikland","Dalarna","Värmland"};    //should inlude all landscape names..
        for(String s: allLandscapeNames){
            assertTrue(landscapeNames.contains(s));
        }

        assertEquals(25,landscapeList.size());
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

        assertEquals(196012, norrbottenPopulation17);
        assertEquals(117688,jamtlandPopulation19);
        assertEquals(59249,gotlandPopulation18);
    }

    @Test
    public void testCalculatePercentageChange(){
        Landscape skane= landscapeHandler.getLandscape("Skåne");
        skane.setPopulation17(0);
        assertNull(skane.getPercentageChange17to19());

        skane.setPopulation17(10000);
        String skanePercentageChange= String.valueOf(skane.getPercentageChange17to19());
        assertEquals("13652.78",skanePercentageChange);

    }
}
