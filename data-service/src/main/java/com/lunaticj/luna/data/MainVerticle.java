package com.lunaticj.luna.data;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigStoreOptions file = new ConfigStoreOptions().setType("file").setConfig(new JsonObject().put("path", "application.json"));
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(file));
    Router router = Router.router(vertx);

    router.route().handler(ctx -> {

      // This handler will be called for every request
      HttpServerResponse response = ctx.response();
      response.putHeader("content-type", "text/plain");

      // Write to the response and end it
      response.end("Hello World from Vert.x-Web!");
    });
    retriever.getConfig(conf -> {
      vertx.createHttpServer().requestHandler(router).listen(conf.result().getInteger("port"), http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port "+conf.result().getInteger("port"));
        } else {
          startPromise.fail(http.cause());
        }
      });
    });

  }
}
