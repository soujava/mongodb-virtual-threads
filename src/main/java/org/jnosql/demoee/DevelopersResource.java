package org.jnosql.demoee;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("developers")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class DevelopersResource {

    @Inject
    CameraService service;

    @GET
    public List<Camera> listAll(@QueryParam("name") String name) {
        if (name == null) {
            return service.select(Camera.class).result();
        }

        return service.select(Camera.class)
                .where("name")
                .like(name)
                .result();
    }

    public record NewDeveloperRequest(String name, LocalDate birthday) {
    }

    @POST
    public Camera add(NewDeveloperRequest request) {
        var newDeveloper = Camera.newDeveloper(request.name(), request.birthday());
        return service.insert(newDeveloper);
    }

    @Path("{id}")
    @GET
    public Camera get(@PathParam("id") String id) {
        return service.find(Camera.class, id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    public record UpdateDeveloperRequest(String name, LocalDate birthday) {
    }

    @Path("{id}")
    @PUT
    public Camera update(@PathParam("id") String id, UpdateDeveloperRequest request) {
        var developer = service.find(Camera.class, id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        var updatedDeveloper = developer.update(request.name(), request.birthday());
        return service.update(updatedDeveloper);

    }

    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") String id) {
        service.delete(Camera.class, id);
    }

}
