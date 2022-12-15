package com.arbetsprov.demo;

import org.json.*;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.nio.charset.StandardCharsets;

import java.util.List;

/**
 * Reads information from the SCB database and enters it to this application.
 * Has one JSONArray with the population statistic for every year mapped to unique ID for every landscape.
 * To map which landscape name belongs to which ID, it also retrieves a JSONObject jsonLandscapeCodes with both ID's and landscape names
 * Used by MainView
 */
public class SCBReader {

    private static JSONArray allPopulationStatistic;
    private static JSONObject jsonLandscapeCodes;
    private static final LandscapeHandler landscapeHandler=LandscapeHandler.getInstance();


    /**
     * gets data from scb through HTTPs Requests.
     * Uses the landscapeHandler to create landscape objects
     * @return list of landscapes
     */
    protected static List<Landscape> getData() throws IOException {

        URL url= new URL("https://api.scb.se/OV0104/v1/doris/en/ssd/START/BE/BE0101/BE0101A/FolkmangdDistrikt");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\n" +
                "  \"query\": [\n" +
                "    {\n" +
                "      \"code\": \"Region\",\n" +
                "      \"selection\": {\n" +
                "        \"filter\": \"vs:ELandskap\",\n" +
                "        \"values\": [\n" +
                "          \"101\",\n" +
                "          \"102\",\n" +
                "          \"103\",\n" +
                "          \"104\",\n" +
                "          \"105\",\n" +
                "          \"106\",\n" +
                "          \"107\",\n" +
                "          \"108\",\n" +
                "          \"109\",\n" +
                "          \"110\",\n" +
                "          \"211\",\n" +
                "          \"212\",\n" +
                "          \"213\",\n" +
                "          \"214\",\n" +
                "          \"215\",\n" +
                "          \"217\",\n" +
                "          \"316\",\n" +
                "          \"318\",\n" +
                "          \"319\",\n" +
                "          \"320\",\n" +
                "          \"321\",\n" +
                "          \"322\",\n" +
                "          \"323\",\n" +
                "          \"324\",\n" +
                "          \"325\",\n" +
                "          \"999\"\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"Tid\",\n" +
                "      \"selection\": {\n" +
                "        \"filter\": \"item\",\n" +
                "        \"values\": [\n" +
                "          \"2017\",\n" +
                "          \"2018\",\n" +
                "          \"2019\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"response\": {\n" +
                "    \"format\": \"json\"\n" +
                "  }\n" +
                "}";

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        String populationStatistic=readRequest(con);
        JSONObject jsonPopulationStatistic=new JSONObject(populationStatistic);
        allPopulationStatistic= jsonPopulationStatistic.getJSONArray("data"); // {"key":["101","2018"],"values":["1359800"]}

        HttpsURLConnection con2 = (HttpsURLConnection) url.openConnection();
        con2.setRequestMethod("GET");
        String landscapeCodes=readRequest(con2);

        jsonLandscapeCodes= new JSONObject(landscapeCodes);
        enterLandscapes();

        return landscapeHandler.getLandscapeList();
    }

    public static JSONArray getAllPopulationStatistic(){
        return allPopulationStatistic;
    }

    /**
     * makes a request to scb
     * @param con the https url connection to specified URL
     * @return the response (data) as a string
     */
    private static String readRequest(HttpsURLConnection con) throws IOException {
        String data;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            data = response.toString();
        }
    return data;
    }

    /**
     * Sort out the landscape names from the list of landscapes and municipalities and creates landscapes objects with their associated data
     */
    private static void enterLandscapes() {
        JSONArray landscapes= jsonLandscapeCodes.getJSONArray("variables").getJSONObject(0).getJSONArray("valueTexts");
        //a landscape's ID and name is at the same index in the json object but in different arrays ("valueTexts" and "values")
        for (int i = 5; i < landscapes.length(); i++) {
            if( String.valueOf(landscapes.get(i)).indexOf('(') ==-1 && !String.valueOf(landscapes.get(i)).contains("Unknown") ) {
                int scbID=retrieveScbID(i);
                String landscapeName=String.valueOf(landscapes.get(i));
                landscapeHandler.createLandscape(landscapeName, scbID);
                landscapeHandler.enterPopulationStatistic(landscapeHandler.getLandscape(landscapeName),allPopulationStatistic);
            }
        }
    }

    private static int retrieveScbID(int index){
        JSONArray scbIDs= jsonLandscapeCodes.getJSONArray("variables").getJSONObject(0).getJSONArray("values");
        return Integer.parseInt((String) scbIDs.get(index));
    }



}
