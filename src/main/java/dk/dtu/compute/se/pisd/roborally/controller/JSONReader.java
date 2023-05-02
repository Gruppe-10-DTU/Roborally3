package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.*;

import com.google.gson.stream.JsonReader;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.*;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Pit;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.PriorityAntenna;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.RebootToken;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Card;
import dk.dtu.compute.se.pisd.roborally.model.Cards.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.Cards.DamageCard;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {


    private JSONObject jsonSpaces;
    private JSONArray spacesArray;
    private static Gson gson;

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
    public static String saveGame(GameController gameController){
        RuntimeTypeAdapterFactory<Space> spaceRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(
                        Space.class)
                .registerSubtype(Space.class)
                .registerSubtype(BoardLaser.class)
                .registerSubtype(Checkpoint.class)
                .registerSubtype(Conveyorbelt.class)
                .registerSubtype(Energy.class)
                .registerSubtype(FastConveyorbelt.class)
                .registerSubtype(Gear.class)
                .registerSubtype(Push.class)
                .registerSubtype(Pit.class)
                .registerSubtype(PriorityAntenna.class)
                .registerSubtype(RebootToken.class);

        RuntimeTypeAdapterFactory<Card> cardRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Card.class, "cardtype")
                .registerSubtype(CommandCard.class)
                .registerSubtype(DamageCard.class);

        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapterFactory(spaceRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(cardRuntimeTypeAdapterFactory)
                .setPrettyPrinting();
        gson = gsonBuilder.create();
        return gson.toJson(gameController.board);
    }
    public static Board loadGame(String filename){
        RuntimeTypeAdapterFactory<Space> spaceRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(
                        Space.class)
                .registerSubtype(Space.class)
                .registerSubtype(BoardLaser.class)
                .registerSubtype(Checkpoint.class)
                .registerSubtype(Conveyorbelt.class)
                .registerSubtype(Energy.class)
                .registerSubtype(FastConveyorbelt.class)
                .registerSubtype(Gear.class)
                .registerSubtype(Push.class)
                .registerSubtype(Pit.class)
                .registerSubtype(PriorityAntenna.class)
                .registerSubtype(RebootToken.class);

        RuntimeTypeAdapterFactory<Card> cardRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Card.class, "cardtype")
                .registerSubtype(CommandCard.class)
                .registerSubtype(DamageCard.class);
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapterFactory(spaceRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(cardRuntimeTypeAdapterFactory)
                .setPrettyPrinting();
        gson = gsonBuilder.create();
        try {
            JsonReader jsonReader = new JsonReader(new FileReader("test.json"));
            return gson.fromJson(jsonReader, Board.class);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    public JSONArray getJsonSpaces() {
        return spacesArray;
    }


}
