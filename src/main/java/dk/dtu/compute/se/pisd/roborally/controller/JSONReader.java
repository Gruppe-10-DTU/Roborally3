package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JSONReader {


    private JSONObject jsonSpaces;
    private JSONArray spacesArray;

    public JSONReader(String path) {

        try {
            String jsonContent = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
            jsonSpaces = new JSONObject(jsonContent);
            spacesArray = jsonSpaces.getJSONArray("spaces");
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public JSONArray getJsonSpaces () {
        return spacesArray;
    }







}
