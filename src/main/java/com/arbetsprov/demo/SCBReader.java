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


public class SCBReader {

    private static JSONObject jsonPopulationStatistic;
    private static JSONObject jsonLandscapeCodes;
    private static final LandscapeHandler landscapeHandler=LandscapeHandler.getInstance();


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
        jsonPopulationStatistic=new JSONObject(populationStatistic); // {"key":["101","2018"],"values":["1359800"]}


        HttpsURLConnection con2 = (HttpsURLConnection) url.openConnection();
        con2.setRequestMethod("GET");
        String landscapeCodes=readRequest(con2);

        jsonLandscapeCodes= new JSONObject(landscapeCodes);
        enterLandscapes();

        landscapeHandler.calculatePercentageChange();

        return landscapeHandler.getLandscapeList();
    }


    private static String readRequest(HttpsURLConnection con) throws IOException {
        String s;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            s = response.toString();
        }
    return s;
    }

    private static void enterLandscapes() {
        JSONArray landscapes= jsonLandscapeCodes.getJSONArray("variables").getJSONObject(0).getJSONArray("valueTexts");
        for (int i = 5; i < landscapes.length(); i++) {
            if( String.valueOf(landscapes.get(i)).indexOf('(') ==-1 && !String.valueOf(landscapes.get(i)).contains("Unknown") ) {
                int scbIndex=retrieveScbIndex(i);
                String landscapeName=String.valueOf(landscapes.get(i));
                landscapeHandler.createLandscape(landscapeName, scbIndex);
                enterPopulationStatistic(landscapeHandler.getLandscape(landscapeName), scbIndex);
            }
        }
    }

    private static int retrieveScbIndex(int index){
        JSONArray scbIndices= jsonLandscapeCodes.getJSONArray("variables").getJSONObject(0).getJSONArray("values");
        return Integer.parseInt((String) scbIndices.get(index));
    }

    private static void enterPopulationStatistic(Landscape landscape, int index){
        JSONArray allPopulationStatistic= jsonPopulationStatistic.getJSONArray("data");
        for(int i =0; i<allPopulationStatistic.length();i++){
            JSONArray landscapeKey= (JSONArray) jsonPopulationStatistic.getJSONArray("data").getJSONObject(i).get("key");
            if(Integer.parseInt( (String) landscapeKey.get(0))==index){
                JSONArray landscapeValue= (JSONArray) jsonPopulationStatistic.getJSONArray("data").getJSONObject(i).get("values");
                if((landscapeKey.get(1)).equals("2017")){
                landscape.setPopulation17(Integer.parseInt((String) landscapeValue.get(0)));
                }
                else if(landscapeKey.get(1).equals("2018")) {
                    landscape.setPopulation18(Integer.parseInt((String) landscapeValue.get(0)));
                }
                else if(landscapeKey.get(1).equals("2019")) {
                    landscape.setPopulation19(Integer.parseInt((String) landscapeValue.get(0)));
                }
            }
        }
    }


}
