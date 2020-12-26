package com.lunaticj.luna.vertex.todolist.constant;

/**
 * @author kuro
 * @create 2020-12-26
 **/
public class ToDoConstants {

  public static final String API_GET = "/todolist/:id";
  public static final String API_GET_ALL = "/todolist";
  public static final String API_CREATE = "/todolist";
  public static final String API_UPDATE = "/todolist/:id";
  public static final String API_DELETE = "/todolist/:id";
  public static final String API_DELETE_ALL = "/todolist/:id";

  public static final String REDIS_TODO_KEY = "VERTEX_TODO_KEY";
}
