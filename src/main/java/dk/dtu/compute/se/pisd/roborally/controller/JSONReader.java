package dk.dtu.compute.se.pisd.roborally.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {


    private JSONObject jsonSpaces;
    private JSONArray spacesArray;

    /**
     * Contructor for the json reader
     *
     * @param path The file path
     * @author Sandie Petersen
     */
    public JSONReader(String path) {

        try {
            String jsonContent = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
            jsonSpaces = new JSONObject(jsonContent);
            spacesArray = jsonSpaces.getJSONArray("spaces");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public JSONArray getJsonSpaces() {
        return spacesArray;
    }


}
