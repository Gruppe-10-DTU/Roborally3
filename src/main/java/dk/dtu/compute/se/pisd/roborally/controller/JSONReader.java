package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.*;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Pit;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.PriorityAntenna;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.RebootToken;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Card;
import dk.dtu.compute.se.pisd.roborally.model.Cards.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.Cards.DamageCard;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {


    private JSONObject jsonSpaces;
    private JSONArray spacesArray;
    private static Gson gson = setupGson();

    public static Gson setupGson(){
        RuntimeTypeAdapterFactory<Space> spaceRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(
                        Space.class)
                .registerSubtype(Space.class)
                .registerSubtype(BoardLaser.class, "Lazer")
                .registerSubtype(Checkpoint.class)
                .registerSubtype(Conveyorbelt.class, "Conveyor")
                .registerSubtype(Energy.class)
                .registerSubtype(FastConveyorbelt.class)
                .registerSubtype(Gear.class)
                .registerSubtype(Push.class)
                .registerSubtype(Pit.class)
                .registerSubtype(PriorityAntenna.class, "Priority")
                .registerSubtype(Spawn.class)
                .registerSubtype(RebootToken.class, "Reboot");
        RuntimeTypeAdapterFactory<Card> cardRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(Card.class, "cardtype")
                .registerSubtype(CommandCard.class)
                .registerSubtype(DamageCard.class);

        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapterFactory(spaceRuntimeTypeAdapterFactory)
                .registerTypeAdapterFactory(cardRuntimeTypeAdapterFactory)
                .setPrettyPrinting();
        return gsonBuilder.create();
    }

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

    public static String saveGame(Board board) {

        return gson.toJson(board, Board.class);
    }

    public static Board loadGame(String filename) {
        try {
            InputStream jsonStream = new FileInputStream(filename);
            JSONObject object = new JSONObject(IOUtils.toString(jsonStream,StandardCharsets.UTF_8));
            return parseBoard(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static Board parseBoard(JSONObject object){

        Board board = new Board(object.getInt("width"), object.getInt("height"), object.getString("boardName"), object.getInt("maxPlayers"), object.getJSONArray("spaces"));

        Checkpoint checkpoint = board.getWincondition();
        String win = object.getJSONObject("wincondition").toString();
        Checkpoint savedCheckpoint = gson.fromJson(win, Checkpoint.class);
        while(checkpoint != null){
            checkpoint.setPlayers(savedCheckpoint.getPlayers());
            checkpoint = checkpoint.getPrevious();
            savedCheckpoint = savedCheckpoint.getPrevious();
        }
        Type token = new TypeToken<ArrayList<Player>>(){}.getType();
        String playersString = object.getJSONArray("players").toString();

        List<Player> players = gson.fromJson(playersString, token);
        for (Player player : players
        ) {

            player.board = board;
            Space space = board.getSpace(player.getSpace());
            board.addPlayer(player);
            space.setPlayer(player);
            player.setPlayer();
        }
        JSONArray jsonArray = object.getJSONArray("playerOrder");
        for (int i = 0; i < jsonArray.length(); i++) {
            String player = jsonArray.getJSONObject(i).getString("name");
            board.addPlayerToOder(board.getPlayerByName(player));
        }
        if (object.has("current")) {
            board.setCurrentPlayer(board.getPlayerByName(object.getJSONObject("current").getString("name")));
        }
        board.setStep(object.getInt("step"));
        board.setPhase(Phase.valueOf(object.getString("phase")));

        return board;
    }


    public JSONArray getJsonSpaces() {
        return spacesArray;
    }


}
