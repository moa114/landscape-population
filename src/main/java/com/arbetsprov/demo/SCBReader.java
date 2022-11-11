package com.arbetsprov.demo;

import org.json.*;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class SCBReader {

    private static JSONObject json_population_statistic;
    private static JSONObject json_landscape_codes;
    private static final LandscapeHandler landscape_handler=LandscapeHandler.getInstance();
    private static final DecimalFormat df = new DecimalFormat("0.00");


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

        String population_statistic=read_request(con);
        json_population_statistic=new JSONObject(population_statistic); // {"key":["101","2018"],"values":["1359800"]}


        HttpsURLConnection con2 = (HttpsURLConnection) url.openConnection();
        con2.setRequestMethod("GET");
        String landscape_codes=read_request(con2);

        json_landscape_codes= new JSONObject(landscape_codes);
        create_landscape_objects();

        landscape_handler.calculatePercentageChange();

        return landscape_handler.getLandscapeList();
    }


    private static String read_request(HttpsURLConnection con) throws IOException {
        String s;

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            s = response.toString();

        }
    return s;
    }

    private static void create_landscape_objects() {
        JSONArray landscapes= json_landscape_codes.getJSONArray("variables").getJSONObject(0).getJSONArray("valueTexts");
        for (int i = 5; i < landscapes.length(); i++) {
            if( String.valueOf(landscapes.get(i)).indexOf('(') ==-1 && !String.valueOf(landscapes.get(i)).contains("Unknown") ) {
                int scb_index=retrieve_scb_index(i);
                Landscape landscape=new Landscape(String.valueOf(landscapes.get(i)), scb_index);
                landscape_handler.getLandscapeList().add(landscape);
                enter_population_statistic(landscape,scb_index);
            }
        }
    }

    private static int retrieve_scb_index( int index){
        JSONArray landscape_index_scb= json_landscape_codes.getJSONArray("variables").getJSONObject(0).getJSONArray("values"); //funkar!!! alla index
        return Integer.parseInt((String)landscape_index_scb.get(index));
    }

    private static void enter_population_statistic(Landscape landscape,int index){
        JSONArray all_population_statistic= json_population_statistic.getJSONArray("data");
        for(int i =0; i<all_population_statistic.length();i++){
            JSONArray landscape_key= (JSONArray) json_population_statistic.getJSONArray("data").getJSONObject(i).get("key");
            if(Integer.parseInt( (String) landscape_key.get(0))==index){
                JSONArray landscape_value= (JSONArray) json_population_statistic.getJSONArray("data").getJSONObject(i).get("values");
                if((landscape_key.get(1)).equals("2017")){
                landscape.setPopulation17(Integer.parseInt((String) landscape_value.get(0)));
                }
                else if(landscape_key.get(1).equals("2018")) {
                    landscape.setPopulation18(Integer.parseInt((String) landscape_value.get(0)));
                }
                else if(landscape_key.get(1).equals("2019")) {
                    landscape.setPopulation19(Integer.parseInt((String) landscape_value.get(0)));
                }
            }
        }
    }


}
