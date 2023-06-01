package dk.dtu.compute.se.pisd.roborally.controller;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {

    @Test
    void controllerTest() {
        JSONReader reader = new JSONReader("src/main/resources/boards/RiskyCrossing.json");

        JSONArray result = reader.getJsonSpaces();

        assertEquals(10, result.getJSONObject(0).get("x"));
        assertEquals(5, result.getJSONObject(0).get("y"));
        assertEquals("Checkpoint", result.getJSONObject(0).get("type"));
    }
}