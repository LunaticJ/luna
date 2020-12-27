package com.lunaticj.luna.vertex.todolist.service;

import com.lunaticj.luna.vertex.todolist.entity.TodoEntity;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.pgclient.PgPool;

import java.util.List;

/**
 * todolist service
 *
 * @author kuro
 * @create 2020-12-27
 **/
public class TodoService {
  private Vertx vertx;
  private PgPool client;

  private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS \"todo\" (\n" +
    "  \"id\" serial4 NOT NULL,\n" +
    "  \"title\" varchar(255) DEFAULT NULL,\n" +
    "  \"completed\" boolean DEFAULT NULL,\n" +
    "  \"order\" integer DEFAULT NULL,\n" +
    "  \"url\" varchar(255) DEFAULT NULL,\n" +
    "  PRIMARY KEY (\"id\") )";
  private static final String SQL_INSERT = "INSERT INTO \"todo\" " +
    "(\"id\", \"title\", \"completed\", \"order\", \"url\") VALUES (?, ?, ?, ?, ?)";
  private static final String SQL_QUERY = "SELECT * FROM todo WHERE id = ?";
  private static final String SQL_QUERY_ALL = "SELECT * FROM todo";
  private static final String SQL_UPDATE = "UPDATE \"todo\"\n" +
    "SET \"id\" = ?,\n" +
    "\"title\" = ?,\n" +
    "\"completed\" = ?,\n" +
    "\"order\" = ?,\n" +
    "\"url\" = ?\n" +
    "WHERE \"id\" = ?;";
  private static final String SQL_DELETE = "DELETE FROM \"todo\" WHERE \"id\" = ?";
  private static final String SQL_DELETE_ALL = "DELETE FROM \"todo\"";

  public TodoService(Vertx vertx, PgPool client) {
    this.vertx = vertx;
    this.client = client;
  }

  public Future<Boolean> insert(TodoEntity entity) {
    client.getConnection();
    return null;
  }

  public Future<Boolean> update(String id, TodoEntity entity) {
    return null;
  }

  public Future<Boolean> delete(String id) {
    return null;
  }

  public Future<Boolean> drop() {
    return null;
  }

  public Future<TodoEntity> getOne(String id) {
    return null;
  }

  public Future<List<TodoEntity>> getAll() {
    return null;
  }
}
