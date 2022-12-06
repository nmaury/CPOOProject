package com.city.builder.game.resource;

import com.github.hanleyt.JerseyExtension;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.city.builder.game.model.Game;
import com.city.builder.game.model.Map;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameResource {
	static {
		System.setProperty("jersey.config.test.container.port", "0");
	}

	@SuppressWarnings("unused") @RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);



	Application configureJersey() {

		return new ResourceConfig()
			.register(GameResource.class);
	}

	@Test
	public  void TestGetGamesStatus(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetGames(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/game")
				.request()
				.get();
		final List list =  res.readEntity(List.class);
		System.out.println(list.size());
		assertNotNull(list);
		for(int i = 0; i < list.size(); i++){
			assertTrue(list.get(i).toString().contains("game"+i));
		}
	}
	@Test
	public  void TestGetOneGameStatus(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public void TestGetOneGame(final Client client, final URI baseUri){
		final Response res = client
				.target(baseUri)
				.path("city/game/0")
				.request()
				.get();
		final Object game = res.readEntity(Object.class);
		assertNotNull(game);
		assertTrue(game.toString().contains("game0"));
	}
	@Test
	public  void TestUpdateScoreStatus(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/10")
				.request()
				.put(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public void TestUpdateScore(final Client client, final URI baseUri){
		final Response res = client
				.target(baseUri)
				.path("city/game/0/10")
				.request()
				.put(Entity.json(""));
		final Object game = res.readEntity(Object.class);
		assertNotNull(game);
		assertTrue(game.toString().contains("score=10"));
	}
	@Test
	public  void TestGetOneMapStatus(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetOneMap(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map")
				.request()
				.get();
		Object map = res.readEntity(Object.class);
		assertNotNull(map);
		String temp = "score=0, score_limit=10, turn=1";
		assertTrue(map.toString().contains(temp));
	}
	@Test
	public  void TestGetPiecesStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/piece")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetPieces(final Client client, final URI baseUri){
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/piece")
				.request()
				.get();
		List pieces = res.readEntity(List.class);
		assertTrue(pieces.toString().contains("type=HOUSE"));
	}
	@Test
	public  void TestGetPlayersStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/player")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetPlayers(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/player")
				.request()
				.get();
		List players = res.readEntity(List.class);
		assertEquals(players.size(),2);
		assertTrue(players.toString().contains("user_name=p1"));
		assertTrue(players.toString().contains("user_name=p2"));
	}
	@Test
	public  void TestNewPlayerStatus(final Client client, final URI baseUri){
		final Response res = client
				.target(baseUri)
				.path("city/player/pierre")
				.request()
				.post(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestNewPlayer(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/player/pierre")
				.request()
				.post(Entity.json(""));
		Object player = res.readEntity(Object.class);
		assertEquals(parseInt(player.toString()),2);
	}
	@Test
	public  void TestGetOnePlayerStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/player/0")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetOnePlayer(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/player/0")
				.request()
				.get();
		Object player = res.readEntity(Object.class);
		assertTrue((player.toString().contains("id=0, user_name=p1")));
	}
	@Test
	public  void TestModifyOnePlayerStatus(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/player/0/pierre")
				.request()
				.put(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestModifyOnePlayer(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/player/1/pierre")
				.request()
				.put(Entity.json(""));
		Object player = res.readEntity(Object.class);
		assertTrue(player.toString().contains("id=1, user_name=pierre"));
	}
	@Test
	public  void TestGetPlayerGamesStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/player/0/game")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetPlayerGames(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/player/0/game")
				.request()
				.get();
		List games = res.readEntity(List.class);
		assertEquals(games.size(),1);
		assertTrue(games.toString().contains("id=0, game_name=game0"));
	}
	@Test
	public  void TestNewGameStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/player/0/2/game2/Bordeaux")
				.request()
				.post(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestNewGame(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/player/0/2/game2/Bordeaux")
				.request()
				.post(Entity.json(""));
		Object game = res.readEntity(Object.class);
		assertTrue(game.toString().contains("id=2, game_name=game2"));
		assertTrue(game.toString().contains("name=Bordeaux"));
	}
	@Test
	public  void TestGetTilesStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetTiles(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile")
				.request()
				.get();
		List tiles = res.readEntity(List.class);
		System.out.println(tiles.get(0));
		assertEquals(tiles.size(),100);
		assertTrue(tiles.get(0).toString().contains("loct_x=0"));
		assertTrue(tiles.get(0).toString().contains("loct_y=0"));
		assertTrue(tiles.get(0).toString().contains("type="));
	}
	@Test
	public  void TestModifyTileStatus(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/0/house/10/30")
				.request()
				.put(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestModifyTileHouse(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/0/house/10/30")
				.request()
				.put(Entity.json(""));
		Object game = res.readEntity(Object.class);
		assertTrue(game.toString().contains("score=10"));
		assertTrue(game.toString().contains("score_limit=30"));
		assertTrue(game.toString().contains("loct_x=0, loct_y=0, type=HOUSE"));
	}
	@Test
	public  void TestModifyTileWindTurbine(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/0/wind_turbine/10/30")
				.request()
				.put(Entity.json(""));
		Object game = res.readEntity(Object.class);
		assertTrue(game.toString().contains("score=10"));
		assertTrue(game.toString().contains("score_limit=30"));
		assertTrue(game.toString().contains("loct_x=0, loct_y=0, type=WIND_TURBINE"));
	}
	@Test
	public  void TestModifyTileFountain(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/0/fountain/10/30")
				.request()
				.put(Entity.json(""));
		Object game = res.readEntity(Object.class);
		assertTrue(game.toString().contains("score=10"));
		assertTrue(game.toString().contains("score_limit=30"));
		assertTrue(game.toString().contains("loct_x=0, loct_y=0, type=FOUNTAIN"));
	}
	@Test
	public  void TestModifyTileCircus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/0/circus/10/30")
				.request()
				.put(Entity.json(""));
		Object game = res.readEntity(Object.class);
		assertTrue(game.toString().contains("score=10"));
		assertTrue(game.toString().contains("score_limit=30"));
		assertTrue(game.toString().contains("loct_x=0, loct_y=0, type=CIRCUS"));
	}
	@Test
	public  void TestModifyTileNull(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/0/ushz/10/30")
				.request()
				.put(Entity.json(""));
		Object game = res.readEntity(Object.class);
		assertTrue(game.toString().contains("score=10"));
		assertTrue(game.toString().contains("score_limit=30"));
		assertTrue(game.toString().contains("loct_x=0, loct_y=0, type=null"));
	}
	@Test
	public  void TestDeleteTileStatus(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/6/5/25")
				.request()
				.delete();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestDeleteTile(final Client client, final URI baseUri)  {
		final Response res = client
				.target(baseUri)
				.path("city/game/0/map/tile/6/5/25")
				.request()
				.delete();
		Object tile = res.readEntity(Object.class);
		assertTrue(tile.toString().contains("loct_x=0, loct_y=6, type=EMPTY"));
	}
	@Test
	public  void TestGetOneReplayStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/replay/0")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public  void TestGetOneReplay(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/replay/0")
				.request()
				.get();
		List blocks = res.readEntity(List.class);
		assertEquals(blocks.size(),1);
		System.out.println(blocks);
		assertTrue(blocks.toString().contains("gamename=game0"));
	}
	@Test
	public  void TestendGameStatus(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("city/end/1/Bordeaux/650/p2")
				.request()
				.post(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}
	@Test
	public void TestendGame(final Client client, final URI baseUri) throws IOException, ParseException {
		final Response res = client
				.target(baseUri)
				.path("city/end/1/Bordeaux/650/p2")
				.request()
				.post(Entity.json(""));
		Object end = res.readEntity(Object.class);
		assertTrue(end.toString().contains("score=650"));
		assertTrue(end.toString().contains("name=Bordeaux"));
		assertTrue(end.toString().contains("id=1"));
		assertTrue(end.toString().contains("player=p2"));
	}


}

