package com.city.builder.game.resource;


import com.city.builder.game.model.Game;
import com.city.builder.game.model.Player;
import com.city.builder.game.model.Block;
import com.city.builder.game.model.Replay;
import com.city.builder.game.model.Map;
import com.city.builder.game.model.Tile;
import com.city.builder.game.model.Piece;
import com.city.builder.game.model.TileType;
import javax.inject.Singleton;

import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Path("city")
public class GameResource {
	private List<Game> games;
	private List<Player> players;
	private Replay replay;

	public GameResource() throws IOException, ParseException {
		super();
		this.replay = new Replay();
		this.games = new ArrayList<>();
		this.players = new ArrayList<>();
		final Player p1 = new Player("p1", 0);
		final Game g1 = new Game("game0", 0, p1.getUser_name(), "Random");
		p1.addGame(g1);
		this.games.add(g1);
		this.players.add(p1);
		final List<Tile> tiles1 = new ArrayList<>();
		this.fillTiles(tiles1, g1, 0);
		final Block b1 = new Block(g1.getMap().getScore(), g1.getMap().getScore_limit(), tiles1, p1.getUser_name(), g1.getGame_name());
		this.replay.addGame(b1);
		final Player p2 = new Player("p2", 1);
		final Game g2 = new Game("game1", 1, p2.getUser_name(), "Bordeaux");
		p2.addGame(g2);
		this.games.add(g2);
		this.players.add(p2);
		final List<Tile> tiles2 = new ArrayList<>();
		this.fillTiles(tiles2, g2, 0);
		final Block b2 = new Block(g2.getMap().getScore(), g2.getMap().getScore_limit(), tiles2, p2.getUser_name(), g2.getGame_name());
		this.replay.addGame(b2);
	}

	private void fillTiles(final List<Tile> tiles, final Game g, final int place) {
		if(tiles.size() < 100) {
			final Tile tile = g.getMap().getTiles().get(place);
			tiles.add(new Tile(tile.getLoct_x(), tile.getLoct_y(), tile.getType()));
			fillTiles(tiles, g, place + 1);
		}
	}

	/***** To get all the availables games *****/
	@GET
	@Path("game")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGames() {
		final List<Game> list = this.games;
		return Response.status(Response.Status.OK).entity(list).build();
	}
	/***** To get one specific game *****/
	@GET
	@Path("game/{gameID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneGame(@PathParam("gameID")final int gameID) {
		final Game game = this.games.get(gameID);
		return Response.status(Response.Status.OK).entity(game).build();
	}
	/***** To modify the score of a specific game *****/
	@PUT
	@Path("game/{gameID}/{score}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateScore(@PathParam("gameID")final int gameID, @PathParam("score")final int score) {
		this.games.get(gameID).getMap().setScore(score);
		this.players.stream().forEach(player -> {
			final List<Game> games = player.getGames();
			games.stream().filter(game -> game.getId() == gameID)
					.forEach(game -> game.getMap().setScore(score));
		});
		return Response.status(Response.Status.OK).entity(this.games.get(gameID)).build();
	}
	/***** To get the map of a specific game *****/
	@GET
	@Path("game/{gameID}/map")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneMap(@PathParam("gameID") final int gameID, @PathParam("mapID")final int mapID) {
		final Map map = this.games.get(gameID).getMap();
		return Response.status(Response.Status.OK).entity(map).build();
	}
	/***** To get all the pieces of a specific game *****/
	@GET
	@Path("game/{gameID}/map/piece")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPieces(@PathParam("gameID") final int gameID) {
		final List<Piece> list = this.games.get(gameID).getMap().getPieces();
		return Response.status(Response.Status.OK).entity(list).build();
	}
	/***** To get all the availables players *****/
	@GET
	@Path("player")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlayer() {
		final List<Player> p = this.players;
		return Response.status(Response.Status.OK).entity(p).build();
	}
	/***** To get add a new player *****/
	@POST
	@Path("player/{user_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response newPlayer(@PathParam("user_name") final String s) {
		final Player new_p = new Player(s, this.players.size());
		this.players.add(new_p);
		return Response.status(Response.Status.OK).entity(new_p.getId()).build();
	}
	/***** To get one specific player *****/
	@GET
	@Path("player/{playerID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOnePlayer(@PathParam("playerID") final int playerID) {
		final Player p = this.players.get(playerID);
		return Response.status(Response.Status.OK).entity(p).build();
	}

	/***** To modify the username of a specific player *****/
	@PUT
	@Path("player/{playerID}/{user_name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyOnePlayer(@PathParam("playerID")final int playerID, @PathParam("user_name") final String user_name) {
		final Player p = this.players.get(playerID);
		p.setUser_name(user_name);
		return Response.status(Response.Status.OK).entity(p).build();
	}
	/***** To get all the availables games of a specific player *****/
	@GET
	@Path("player/{playerID}/game")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlayerGames(@PathParam("playerID") final int playerID) {
		final List<Game> p = this.players.get(playerID).getGames();
		return Response.status(Response.Status.OK).entity(p).build();
	}
	/***** To add a new game played by a specific player 
	 * @throws ParseException
	 * @throws IOException*****/
	@POST
	@Path("player/{playerID}/{gameID}/{gamename}/{mapname}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOnePlayerGame(@PathParam("playerID") final int playerID, @PathParam("gameID") final int gameID, @PathParam("gamename") final String gamename, @PathParam("mapname") final String mapname) throws IOException, ParseException {
		final Player p = this.players.get(playerID);
		final Game g = new Game(gamename, gameID, p.getUser_name(), mapname);
		this.games.add(g);
		this.players.get(playerID).addGame(g);
		final List<Tile> tiles = new ArrayList<>();
		this.fillTiles(tiles, g, 0);
		final Block b = new Block(g.getMap().getScore(), g.getMap().getScore_limit(), tiles, p.getUser_name(), g.getGame_name());
		this.replay.addGame(b);
		return Response.status(Response.Status.OK).entity(g).build();
	}
	/***** To get the tiles of a specific game *****/
	@GET
	@Path("game/{gameID}/map/tile")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTiles(@PathParam("gameID") final int gameID) {
		final List<Tile> list = this.games.get(gameID).getMap().getTiles();
		return Response.status(Response.Status.OK).entity(list).build();
	}
	/***** To modify a tile *****/
	@PUT
	@Path("game/{gameID}/map/tile/{tileID}/{type}/{score}/{score_limit}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyTile(@PathParam("gameID") final int gameID, @PathParam("tileID") final int tileID, @PathParam("type") final String type, @PathParam("score") final int score, @PathParam("score_limit") final int score_limit) {
		final TileType t;
		switch(type) {
			case "house":
				t = TileType.HOUSE;
				break;
			case "wind_turbine":
				t = TileType.WIND_TURBINE;
				break;
			case "fountain":
				t = TileType.FOUNTAIN;
				break;
			case "circus":
				t = TileType.CIRCUS;
				break;
			default:
				t = null;
		}
		this.games.get(gameID).getMap().getOneTile(tileID).setType(t);
		final Game g = this.games.get(gameID);
		//update score et score_limit
		this.games.get(gameID).getMap().setScore(score);
		this.games.get(gameID).getMap().setScore_limit(score_limit);
		//ajout d'un nouveau block
		final List<Tile> tiles = new ArrayList<>();
		this.fillTiles(tiles, g, 0);
		final Block b = new Block(g.getMap().getScore(), g.getMap().getScore_limit(), tiles, g.getPlayer(), g.getGame_name());
		this.replay.addBlock(gameID, b);
		return Response.status(Response.Status.OK).entity(this.games.get(gameID)).build();
	}
	/***** To delete one tile (set his type to "empty") *****/
	@DELETE
	@Path("game/{gameID}/map/tile/{tileID}/{score}/{score_limit}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOneTile(@PathParam("gameID") final int gameID, @PathParam("tileID") final int tileID, @PathParam("score") final int score, @PathParam("score_limit") final int score_limit) {
		final Tile t = this.games.get(gameID).getMap().getOneTile(tileID);
		t.setType(TileType.EMPTY);
		//update score et score_limit
		this.games.get(gameID).getMap().setScore(score);
		this.games.get(gameID).getMap().setScore_limit(score_limit);
		//ajout d'un nouveau block
		final Game g = this.games.get(gameID);
		final List<Tile> tiles = new ArrayList<>();
		this.fillTiles(tiles, g, 0);
		final Block b = new Block(g.getMap().getScore(), g.getMap().getScore_limit(), tiles, g.getPlayer(), g.getGame_name());
		this.replay.addBlock(gameID, b);
		this.games.get(gameID).getMap().setOneTile(tileID, t);
		return Response.status(Response.Status.OK).entity(t).build();
	}

	/***** To get the replay of a specific game *****/
	@GET
	@Path("replay/{gameid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneReplay(@PathParam("gameid") final int gameid) {
		final List<Block> l = this.replay.getOneGameList(gameid);
		return Response.status(Response.Status.OK).entity(l).build();
	}
	/***** To post a game and add a score to the hall of fame 
	 * @throws ParseException
	 * @throws IOException*****/
	@POST
	@Path("end/{gameid}/{mapname}/{scorefinal}/{playername}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response endGame(@PathParam("gameid") final int gameid, @PathParam("mapname") final String mapname, @PathParam("scorefinal") final int scorefinal, @PathParam("playername") final String playername) throws IOException, ParseException {
		final Game g = this.games.get(gameid);
		g.end(mapname, scorefinal, playername);
		g.getMap().setScore(scorefinal);
		return Response.status(Response.Status.OK).entity(g).build();
	}

}
