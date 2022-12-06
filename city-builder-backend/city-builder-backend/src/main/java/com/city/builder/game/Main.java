package com.city.builder.game;

import java.net.URI;
import com.city.builder.game.resource.GameResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public final class Main {
	private Main() {
		super();
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 * @param httpAddress
	 */
	public static HttpServer startServer(final String httpAddress) {
		final ResourceConfig rc = new ResourceConfig()
				 .register(GameResource.class);


		return GrizzlyHttpServerFactory.createHttpServer(URI.create(httpAddress), rc);

	}

	/**
	 * Takes as argument the HTTP address of the server.
	 * Should be http://localhost:4444/ for testing purpose.
	 * http://0.0.0.0:4444/ for a Docker image
	 * In such cases the swagger UI is available http://localhost:4444/swag/index.html (localhost or 0.0.0.0)
	 */

	public static void main(final String[] args) throws InterruptedException {
		//final String httpAddress = args[0];
		final String httpAddress = "http://localhost:4444/";
		startServer(httpAddress);
		Thread.currentThread().join();
	}
}

