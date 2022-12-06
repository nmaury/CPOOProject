package com.city.builder.game.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.Collection;
public class Map {
    private String name;
    private int score;
    private int score_limit;
    private int turn;
    private List<Tile> tiles;
    private List<Piece> pieces;

    public Map(final String mapname) throws IOException, ParseException {
        if ("Random".equals(mapname)) {
            this.initializeRandTabs();
        } else {
            this.initMap(mapname);
        }
    }
    private void initMap(final String mapname) throws IOException, ParseException {
        final JSONParser jsonp = new JSONParser();
        final JSONObject jsonO;
        try {
            jsonO = (JSONObject) jsonp.parse(new FileReader("src/main/java/com/city/builder/maps/" + mapname + ".json"));
            this.turn = Integer.parseInt(jsonO.get("turn").toString());
            this.score_limit = Integer.parseInt(jsonO.get("score_limit").toString());
            this.score = Integer.parseInt(jsonO.get("score").toString());
            this.name = jsonO.get("name").toString();
            this.pieces = new ArrayList<>();
            final List<JSONObject> pieces = this.convertObjectToList(jsonO.get("pieces"));
            pieces.stream().forEach(piece -> {
                final String type = piece.get("type").toString();
                final PieceType p;
                switch (type) {
                    case "HOUSE":
                        p = PieceType.HOUSE;
                        break;
                    case "CIRCUS":
                        p = PieceType.CIRCUS;
                        break;
                    case "WIND_TURBINE":
                        p = PieceType.WIND_TURBINE;
                        break;
                    case "FOUNTAIN":
                        p = PieceType.FOUNTAIN;
                        break;
                    default:
                        p = null;
                        break;
                }
                this.pieces.add(new Piece(p));
            });
            this.tiles = new ArrayList<>();
            final List<JSONObject> tiles = this.convertObjectToList(jsonO.get("tiles"));
            tiles.stream().forEach(tile -> {
                final String type = tile.get("type").toString();
                final TileType t;
                switch (type) {
                    case "EMPTY":
                        t = TileType.EMPTY;
                        break;
                    case "TREE":
                        t = TileType.TREE;
                        break;
                    case "WATER":
                        t = TileType.WATER;
                        break;
                    default:
                        t = null;
                        break;
                }
                this.tiles.add(new Tile(Integer.parseInt(tile.get("loct_x").toString()), Integer.parseInt(tile.get("loct_y").toString()), t));

            });
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    public List<JSONObject> convertObjectToList(final Object obj) {
        List<JSONObject> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((JSONObject[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<JSONObject>((Collection<JSONObject>) obj);
        }
        return list;
    }
    public void addPiece(final Piece p) {
        this.pieces.add(p);
    }
    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(final List<Tile> tiles) {
        this.tiles = tiles;
    }

    public Tile getOneTile(final int id) {
        return this.tiles.get(id);
    }
    public void setOneTile(final int id, final Tile t) {
        this.tiles.set(id, t);
    }
    public List<Piece> getPieces() {
        return pieces;
    }
    public Piece getOnePiece(final int id) {
        return pieces.get(id);
    }

    public void setPieces(final List<Piece> pieces) {
        this.pieces = pieces;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public int getScore_limit() {
        return score_limit;
    }

    public void setScore_limit(final int score_limit) {
        this.score_limit = score_limit;
    }

    public int getTurn() {
        return turn;
    }
    private char chooseLetter(final boolean taille) {
        final String letters = "abcdefghijklmnopqrstuvwxyz";
        final int nb = (int) (Math.random() * letters.length());
        if (taille) {
            return letters.toUpperCase().charAt(nb);
        }else {
            return letters.charAt(nb);
        }
    }
    void fillTiles(final int x, final int y) {
        if(this.tiles.size() < 100) {
            final Random random = new Random();
           final int rand = random.nextInt(5);
            switch (rand) {
                case 0:
                case 2:
                case 1:
                default:
                    this.tiles.add(new Tile(x, y, TileType.EMPTY));
                    break;
                case 3:
                    this.tiles.add(new Tile(x, y, TileType.WATER));
                    break;
                case 4:
                    this.tiles.add(new Tile(x, y, TileType.TREE));
                    break;

            }
            if(y == 9) {
                this.fillTiles(x + 1, 0);
            }else {
                this.fillTiles(x, y + 1);
            }
            }
    }
    private void initializeRandTabs() {
        this.score = 0;
        this.score_limit = 10;
        this.turn = 1;
        String name = "";
        name += chooseLetter(true);
        name += chooseLetter(false);
        name += chooseLetter(false);
        name += chooseLetter(false);
        name += chooseLetter(false);
        name += chooseLetter(false);
        name += chooseLetter(false);
        name += chooseLetter(false);
        this.name = name;
        this.tiles = new ArrayList<>();
        fillTiles(0, 0);
        this.pieces = new ArrayList<>();
        this.pieces.add(new Piece(PieceType.HOUSE));
    }
    public void setTurn(final int turn) {
        this.turn = turn;
    }

}
