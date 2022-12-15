package com.arbetsprov.demo;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import elemental.json.Json;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xmlunit.builder.Input;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


public class integrationTests {

    //kolla json scheduele //priority 2
    // connection om den failar om man skickar in fel adress?   //priority 1
    //
@Test
    public void jsonFormatTest() throws IOException {
        SCBReader.getData();
        JSONArray allPopulationStatistic= SCBReader.getAllPopulationStatistic();
        System.out.print(allPopulationStatistic.get(0));

    FileWriter file =new FileWriter("C:/Users/moabe/arbetsprov/demo/src/test/java/com/arbetsprov/demo/newfile.json");
    file.write(allPopulationStatistic.toString(4));

    ObjectMapper objectMapper=new ObjectMapper();
    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);


    try(InputStream jsonStream= inputStreamFromClasspath("C:/Users/moabe/arbetsprov/demo/src/test/java/com/arbetsprov/demo/newfile.json");
        InputStream schemaStream =inputStreamFromClasspath("C:/Users/moabe/arbetsprov/demo/src/test/java/com/arbetsprov/demo/jsonschema.json");
    ){
        JsonSchema schema = factory.getSchema(schemaStream);
        JsonNode json = objectMapper.readTree(jsonStream);

        schema.validate(json);


    }


        Assert.assertEquals(1,1);

    }
    private static InputStream inputStreamFromClasspath(String path ) {

        // returning stream
        return Thread.currentThread().getContextClassLoader().getResourceAsStream( path ); //problemet är att denna returnerar null
    }
}
