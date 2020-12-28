package com.lunaticj.luna.vertex.todolist;

import com.lunaticj.luna.vertex.todolist.constant.TodoConstants;
import com.lunaticj.luna.vertex.todolist.entity.TodoEntity;
import com.lunaticj.luna.vertex.todolist.service.TodoService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MainVerticle extends AbstractVerticle {
  private static final String REDIS_URL = "redis://luna-project:6379/1";
  private static final int HTTP_PORT = 9010;

  private PgPool client;

  private TodoService todoService;

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
    router.get(TodoConstants.API_GET).handler(this::handleGetTodo);
    router.get(TodoConstants.API_GET_ALL).handler(this::handleGetAllTodo);
    router.get(TodoConstants.API_CREATE).handler(this::handleCreateTodo);
    router.get(TodoConstants.API_UPDATE).handler(this::handleUpdateTodo);
    router.get(TodoConstants.API_DELETE).handler(this::handleDeleteTodo);
    router.get(TodoConstants.API_DELETE_ALL).handler(this::handleDeleteAllTodo);

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
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("luna-project")
      .setUser("postgres")
      .setPassword("1234qwer")
      .setDatabase("luna");

    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

    this.client = PgPool.pool(vertx, connectOptions, poolOptions);
    this.todoService = new TodoService(vertx, client);
  }

  private <T> Handler<AsyncResult<T>> resultHandler(RoutingContext context, Consumer<T> consumer) {
    return res -> {
      if(res.succeeded()) {
        consumer.accept(res.result());
      } else {
        serviceUnavailable(context);
      }
    };
  }

  private void handleCreateTodo(RoutingContext context) {
    TodoEntity entity = wrapObject(new TodoEntity(context.getBodyAsString()), context);
    String encoded = entity.toJson().encode();

    todoService.insert(entity).onComplete(resultHandler(context, res -> {
      if(res) {
        context.response()
          .setStatusCode(201)
          .putHeader("Content-Type", "application/json")
          .end(encoded);
      } else {
        serviceUnavailable(context);
      }
    }));
  }

  private void handleGetTodo(RoutingContext context) {
    String id = context.request().getParam("id");
    if(id == null) {
      sendError(400, context.response());
      return;
    }

    todoService.getOne(id).onComplete(resultHandler(context, res -> {
      if(res == null) {
        notFound(context);
      } else {
        context.response()
          .setStatusCode(201)
          .putHeader("Content-Type", "application/json")
          .end(res.toJson().encode());
      }
    }));
  }

  private void handleGetAllTodo(RoutingContext context) {
    todoService.getAll().onComplete(resultHandler(context, res -> {
      if(res == null) {
        serviceUnavailable(context);
      } else {
        context.response()
          .setStatusCode(201)
          .putHeader("Content-Type", "application/json")
          .end(res.stream().map(TodoEntity::toJson).collect(Collectors.toList()).toString());
      }
    }));
  }

  private void handleUpdateTodo(RoutingContext context) {
    String id = context.request().getParam("id");
    if(id == null) {
      sendError(400, context.response());
      return;
    }
    TodoEntity entity = new TodoEntity(context.getBodyAsString());

    todoService.update(id, entity).onComplete(resultHandler(context, res -> {
      if(res == null) {
        notFound(context);
      } else {
        context.response()
          .setStatusCode(201)
          .putHeader("Content-Type", "application/json")
          .end();
      }
    }));
  }

  private void handleDeleteTodo(RoutingContext context) {
    String id = context.request().getParam("id");
    if(id == null) {
      sendError(400, context.response());
      return;
    }

    todoService.delete(id).onComplete(resultHandler(context, res -> {
      // TODO: ...
    }));
  }

  private void handleDeleteAllTodo(RoutingContext context) {
    // TODO: ...
  }

  private void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }

  private void badRequest(RoutingContext context) {
    context.response().setStatusCode(400).end();
  }

  private void notFound(RoutingContext context) {
    context.response().setStatusCode(404).end();
  }

  private void serviceUnavailable(RoutingContext context) {
    context.response().setStatusCode(503).end();
  }

  private TodoEntity wrapObject(TodoEntity todo, RoutingContext context) {
    int id = todo.getId();
    if(id > TodoEntity.getIncId()) {
      TodoEntity.setIncIdWith(id);
    } else if(id==0) {
      todo.setIncId();
    }
    todo.setUrl(context.request().absoluteURI()+"/"+todo.getId());
    return todo;
  }
}
