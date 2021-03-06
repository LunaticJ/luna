package com.lunaticj.luna.vertex.todolist.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * todoEntity
 *
 * @author kuro
 * @create 2020-12-24
 **/
@DataObject(generateConverter = true)
public class TodoEntity {
  // counter
  private static final AtomicInteger acc = new AtomicInteger(0);

  private int id;
  private String title;
  private Boolean completed;
  private Integer order;
  private String url;

  public TodoEntity() {
  }

  public TodoEntity(TodoEntity other) {
    this.id = other.id;
    this.title = other.title;
    this.completed = other.completed;
    this.order = other.order;
    this.url = other.url;
  }

  public TodoEntity(JsonObject object) {
    TodoEntityConverter.fromJson(object, this);
  }

  public TodoEntity(String jsonStr) {
    TodoEntityConverter.fromJson(new JsonObject(jsonStr), this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    TodoEntityConverter.toJson(this, json);
    return json;
  }

  public TodoEntity(int id, String title, Boolean completed, Integer order, String url) {
    this.id = id;
    this.title = title;
    this.completed = completed;
    this.order = order;
    this.url = url;
  }

  public static int getIncId() {
    return acc.get();
  }

  public void setIncId() {
    this.id = acc.incrementAndGet();
  }

  public static void setIncIdWith(int n) {
    acc.set(n);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TodoEntity that = (TodoEntity) o;
    return id == that.id &&
      Objects.equals(title, that.title) &&
      Objects.equals(completed, that.completed) &&
      Objects.equals(order, that.order) &&
      Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, completed, order, url);
  }

  @Override
  public String toString() {
    return "TodoEntity{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", completed=" + completed +
      ", order=" + order +
      ", url='" + url + '\'' +
      '}';
  }
}
