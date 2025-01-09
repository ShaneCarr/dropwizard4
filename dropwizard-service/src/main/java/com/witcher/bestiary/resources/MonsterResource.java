package com.witcher.bestiary.resources;

import com.witcher.bestiary.models.Monster;
import com.witcher.bestiary.service.MonsterService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/monsters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MonsterResource {
  private final MonsterService monsterService;

  public MonsterResource(MonsterService monsterService) {
    this.monsterService = monsterService;
  }

  @GET
  public List<Monster> getAllMonsters() {
    return monsterService.getAllMonsters();
  }

  @GET
  @Path("/{id}")
  public Response getMonsterById(@PathParam("id") int id) {
    Optional<Monster> monster = monsterService.getMonsterById(id);
    return monster.map(value -> Response.ok(value).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createMonster(Monster monster) {
    // Add logic to handle the request
    monsterService.addMonster(monster);
    return Response.status(Response.Status.CREATED).entity(monster).build();
  }

  @PUT
  @Path("/{id}")
  public Response updateMonster(@PathParam("id") int id, Monster monster) {
    monster.setId(id);
    monsterService.updateMonster(monster);
    return Response.ok(monster).build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteMonster(@PathParam("id") int id) {
    monsterService.deleteMonster(id);
    return Response.status(Response.Status.NO_CONTENT).build();
  }
}