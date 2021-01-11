package com.lunaticj.luna.mnemosyne.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;


/**
 * @author kuro
 * @create 2021-01-02
 **/
public class DataVerticle extends AbstractVerticle {

  private MongoClient mongoClient = null;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    startPromise.complete();
  }

  private void initData() {
    JsonObject dbConfig = config();
    mongoClient = MongoClient.createShared(vertx, dbConfig);

    if(mongoClient.getCollections().isComplete()) {
      System.out.println("complete");
    }
  }
}
