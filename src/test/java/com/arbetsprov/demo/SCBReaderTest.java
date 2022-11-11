package com.arbetsprov.demo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SCBReaderTest {
    List<Landscape> landscapeList;
    List<String> landscape_names;

    @Before
    public void before() throws IOException {
        landscapeList=SCBReader.getData();
        landscape_names=new ArrayList<>();

        for (Landscape l: landscapeList) {
            landscape_names.add(l.getName());
        }
    }

    @Test
    public void test_retrieves_all_landscapes() {
        String[] all_landscape_name={"Lappland","Norrbotten","Västerbotten","Jämtland","Ångermanland","Härjedalen","Medelpad","Hälsingland","Gästrikland","Dalarna","Värmland"};        //should inlude all landscape names..
        for(String s: all_landscape_name){
            assertTrue(landscape_names.contains(s));
        }
        assertEquals(landscapeList.size(),25);
    }

    @Test
    public void test_adds_correct_population_data(){


    }
}
