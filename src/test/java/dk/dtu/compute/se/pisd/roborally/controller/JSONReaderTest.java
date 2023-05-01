package dk.dtu.compute.se.pisd.roborally.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {

    @Test
    void controllerTest() {
        JSONReader reader = new JSONReader("/Users/sandiepetersen/Documents/DTU/2. Semester/Programmering/Roborally3_2/src/main/resources/boards/RiskyCrossing.json");

        JSONArray result = reader.getJsonSpaces();

        assertEquals("3", result.getJSONObject(0).getString("x"));
        assertEquals("0", result.getJSONObject(0).getString("y"));
        assertEquals("Empty", result.getJSONObject(0).getString("Type"));
    }
}