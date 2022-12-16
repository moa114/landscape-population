package com.arbetsprov.demo;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class unitTests {

    @Test(priority = -3, groups = {"group2"} )
    void testCreateLandscape() {
        LandscapeHandler landscapeHandler = LandscapeHandler.getInstance();
        landscapeHandler.createLandscape("Hittapå", 2000);
        List<Landscape> landscapeList = landscapeHandler.getLandscapeList();
        Assert.assertTrue(landscapeList.contains(landscapeHandler.getLandscape("Hittapå")));
    }

    @Test (priority = -2, groups = {"group2"})
    void testCorrectPopulationData() throws IOException, JSONException {
        LandscapeHandler landscapeHandler = LandscapeHandler.getInstance();
        landscapeHandler.createLandscape("Hittapå", 2000);

        String populatondata = Files.readString(Path.of("src/test/java/com/arbetsprov/demo/dummydata.txt"));
        landscapeHandler.enterPopulationStatistic(landscapeHandler.getLandscape("Hittapå"), new JSONArray(populatondata));
        int pop = landscapeHandler.getLandscape("Hittapå").getPopulation17();
        Assert.assertEquals(pop, 1342388);

    }

    @Test (priority = -2,groups = {"group2"} )
    void testGetLandscape() {
        LandscapeHandler landscapeHandler = LandscapeHandler.getInstance();
        landscapeHandler.createLandscape("Hittapå", 2000);
        Landscape madeUp = landscapeHandler.getLandscape("Hittapå");
        Assert.assertEquals(madeUp.getScbId(),2000);

    }

}
