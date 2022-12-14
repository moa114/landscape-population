package com.arbetsprov.demo;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;

import java.net.URL;
import java.util.Set;


public class integrationTests {

    @Test(priority = 2)
    public void jsonFormatTest() throws IOException {
        SCBReader.getData();
        String validationmsg="There is validation errors";

        createDataFile();

        ObjectMapper objectMapper=new ObjectMapper();
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);


        try(InputStream jsonStream=  new FileInputStream("src/test/java/com/arbetsprov/demo/newfile.json");
            InputStream schemaStream =new FileInputStream("src/test/java/com/arbetsprov/demo/jsonschema.json");
        ){
            JsonSchema schema = factory.getSchema(schemaStream);
            JsonNode json = objectMapper.readTree(jsonStream);

            Set<ValidationMessage> validationResult = schema.validate( json );
            if (validationResult.isEmpty()) {
                validationmsg= "There is no validation errors" ;
            } else {
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
            }
        }
        Assert.assertEquals(validationmsg,"There is no validation errors");

    }

    void createDataFile() throws IOException {
        JSONArray allPopulationStatistic= SCBReader.getAllPopulationStatistic();

        FileWriter file =new FileWriter("src/test/java/com/arbetsprov/demo/newfile.json");
        file.write(allPopulationStatistic.toString(4));
        try {
            FileWriter fw = new FileWriter("src/test/java/com/arbetsprov/demo/newfile.json",true); //the true will append the new data
            fw.write("]}]");
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    @Test(priority = 1)
    void testURLConnection() throws Exception {
        URL url = new URL("https://api.scb.se/OV0104/v1/doris/en/ssd/START/BE/BE0101/BE0101A/FolkmangdDistrikt");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.connect();
        Assert.assertEquals(HttpsURLConnection.HTTP_OK, con.getResponseCode());
    }

}
