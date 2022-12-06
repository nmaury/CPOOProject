package com.city.builder.game.model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class Game {
    private int id;
    private String game_name;
    private Map map;
    private String player;




    public Game(final String name, final int id, final String player, final String mapname) throws IOException, ParseException {
        this.id = id;
        game_name = name;
        this.player = player;
        map = new Map(mapname);
    }
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
    public String getGame_name() {
        return game_name;
    }


    public Map getMap() {
        return map;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(final String player) {
        this.player = player;
    }

    public void setGame_name(final String game_name) {
        this.game_name = game_name;
    }

    public JSONArray end(final String mapname, final int scorefinal, final String playername) throws IOException, ParseException {
        final JSONParser jsonp = new JSONParser();
        FileWriter file = null;
        final JSONArray sortie = new JSONArray();
        final List<JSONObject> jsonO;
        try {
            jsonO = (List<JSONObject>) jsonp.parse(new FileReader("src/main/java/com/city/builder/scores/scores.json"));
            file = new FileWriter("src/main/java/com/city/builder/scores/scores.json");

            jsonO.stream()
                    .forEach(elt -> {
                        final  String map = elt.get("mapname").toString();
                        if(Objects.equals(elt.get("mapname").toString(), mapname)) {
                            final JSONObject result = new JSONObject();
                            final JSONArray scores = (JSONArray) elt.get("scores");
                            final JSONObject obj = new JSONObject();
                            obj.put("player", playername);
                            obj.put("score", scorefinal);
                            scores.add(obj);
                            result.put("mapname", map);
                            result.put("scores", scores);
                            sortie.add(result);
                        }else {
                            sortie.add(elt);
                        }
                    });

            sortie.writeJSONString(file);


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(file != null) {
                    file.flush();
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sortie;
        }

        /*for(JSONObject elt : jsonO) {
            final String map = elt.get("mapname").toString();
                if(Objects.equals(map, mapname)) {
                    final JSONObject result = new JSONObject();
                    final JSONArray scores = (JSONArray) elt.get("scores");
                    final JSONObject obj = new JSONObject();
                    obj.put("player", playername);
                    obj.put("score", scorefinal);
                    scores.add(obj);
                    result.put("mapname", map);
                    result.put("scores", scores);
                    sortie.add(result);
                }else {
                    sortie.add(elt);
                }
        }*/


}
