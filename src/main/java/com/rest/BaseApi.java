package com.rest;

import javax.ws.rs.core.Response;

/**
 * The com.rest BaseApi.java
 *
 * @author Daniel Mboya
 * @version 1.0.0
 * @since Nov 7, 2017 1:12:06 PM
 */
public abstract class BaseApi {

  public Response success() {
    return Response.status(200).entity(new SuccessResponse("true", "Successful")).build();
  }

  public Response failure(String msg) {
    return Response.status(400).entity(new SuccessResponse("false", msg)).build();
  }

  public Response notFound() {
    return Response.status(404).entity(new SuccessResponse("false", "Not found")).build();
  }

  public Response notFound(String msg) {
    return Response.status(404).entity(new SuccessResponse("false", msg)).build();
  }

  public Response invalidInput() {
    return Response.status(405).entity(new SuccessResponse("false", "Invalid input")).build();
  }

  public Response invalidInput(String msg) {
    return Response.status(405).entity(new SuccessResponse("false", msg)).build();
  }

  public Response serverFailure() {
    return Response.status(500).entity(new SuccessResponse("false", "Server Error")).build();
  }

  public Response entity(Object entity) {
    return Response.status(200).entity(entity).build();
  }

  public Response entityCORS(Object entity) {
    return Response.status(200)
      .header("Access-Control-Allow-Origin", "*")
      .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, token")
      .header("Access-Control-Allow-Credentials", "true")
      .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
      .entity(entity).build();
  }


}
