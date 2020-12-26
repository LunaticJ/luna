package com.lunaticj.luna.vertex.todolist;

import com.lunaticj.luna.vertex.todolist.constant.ToDoConstants;
import com.lunaticj.luna.vertex.todolist.entity.ToDoEntity;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.Response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainVerticle extends AbstractVerticle {
  private static final String REDIS_URL = "redis://luna-project:6379/1";
  private static final int HTTP_PORT = 9010;

  private RedisAPI redis;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    initData();

    Router router = Router.router(vertx);
    // CORS support
    Set<String> allowHeaders = new HashSet<>();
    allowHeaders.add("x-requested-with");
    allowHeaders.add("Access-Control-Allow-Origin");
    allowHeaders.add("origin");
    allowHeaders.add("Content-Type");
    allowHeaders.add("accept");
    HashSet<HttpMethod> allowMethods = new HashSet<>();
    allowMethods.add(HttpMethod.GET);
    allowMethods.add(HttpMethod.POST);
    allowMethods.add(HttpMethod.PUT);
    allowMethods.add(HttpMethod.DELETE);
    allowMethods.add(HttpMethod.PATCH);

    router.route().handler(CorsHandler.create("*")
      .allowedHeaders(allowHeaders)
      .allowedMethods(allowMethods));
    router.route().handler(BodyHandler.create());

    // routes
    router.get(ToDoConstants.API_GET).handler(this::handleGetTodo);
    router.get(ToDoConstants.API_GET_ALL).handler(this::handleGetAllTodo);
    router.get(ToDoConstants.API_CREATE).handler(this::handleRequest);
    router.get(ToDoConstants.API_UPDATE).handler(this::handleRequest);
    router.get(ToDoConstants.API_DELETE).handler(this::handleRequest);

    vertx.createHttpServer().requestHandler(router).listen(HTTP_PORT, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port "+HTTP_PORT);
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  private void initData(){
    this.redis = RedisAPI.api(Redis.createClient(vertx, REDIS_URL));
    redis.hsetnx(ToDoConstants.REDIS_TODO_KEY, "24",
      new ToDoEntity(24, "Something to do...", false, 1, "todo/ex").toJson().encode(), res -> {
      if(res.failed()) {
        System.out.println("[Error] Redis service is not running!");
      }
    });
  }

  private void handleRequest(RoutingContext context) {
    // TODO: ...
  }

  private void handleGetTodo(RoutingContext context) {
    String todoId = context.request().getParam("id");
    if(StringUtil.isNullOrEmpty(todoId)) {
      sendError(400, context.response());
    } else {
      redis.hget(ToDoConstants.REDIS_TODO_KEY, todoId, res -> {
        if(res.succeeded()) {
          Response result = res.result();
          if(result == null) {
            sendError(404, context.response());
          } else {
            context.response().putHeader("content-type", "application/json").end(result.toString());
          }
        } else {
          sendError(503, context.response());
        }
      });
    }
  }

  private void handleGetAllTodo(RoutingContext context) {
    redis.hvals(ToDoConstants.REDIS_TODO_KEY, res -> {
      if(res.succeeded()) {
        context.response().putHeader("content-type", "application/json").end(res.result().toString());
      } else {
        sendError(503, context.response());
      }
    });
  }

  private void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }
}
