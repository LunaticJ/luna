package com.lunaticj.luna.vertex.todolist.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * todoEntity
 *
 * @author kuro
 * @create 2020-12-24
 **/
@DataObject(generateConverter = true)
public class ToDoEntity {
  // counter
  private static final AtomicInteger acc = new AtomicInteger();

  private int id;
  private String title;
  private Boolean completed;
  private Integer order;
  private String url;

  public ToDoEntity() {
  }

  public ToDoEntity(ToDoEntity other) {
    this.id = other.id;
    this.title = other.title;
    this.completed = other.completed;
    this.order = other.order;
    this.url = other.url;
  }

  public ToDoEntity(JsonObject object) {
    ToDoEntityConverter.fromJson(object, this);
  }

  public ToDoEntity(String jsonStr) {
    ToDoEntityConverter.fromJson(new JsonObject(jsonStr), this);
  }

  public ToDoEntity(int id, String title, Boolean completed, Integer order, String url) {
    this.id = id;
    this.title = title;
    this.completed = completed;
    this.order = order;
    this.url = url;
  }

  public static AtomicInteger getAcc() {
    return acc;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
