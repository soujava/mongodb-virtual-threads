package org.jnosql.demoee;

import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
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

import java.util.List;

@Path("cameras")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CameraResource {

    @Inject
    CameraService service;

    @GET
    @VirtualThreads
    public List<Camera> findAll() {
        return service.findAll();
    }

    @GET
    @Path("brand/{brand}")
    public List<Camera> listAll(@PathParam("brand") String brand) {
        if (brand == null || brand.isBlank()) {
            return service.findAll();
        }
        return service.findByBrand(brand);
    }


    @POST
    public Camera add(Camera camera) {
        return service.insert(camera);
    }

    @Path("{id}")
    @GET
    public Camera get(@PathParam("id") String id) {
        return service.findById(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @Path("{id}")
    @PUT
    public Camera update(@PathParam("id") String id, Camera request) {
        var camera = service.findById(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        return service.update(camera.update(request));

    }

    @Path("{id}")
    @DELETE
    public void delete(@PathParam("id") String id) {
        service.deleteById(id);
    }

    @POST
    @Path("async")
    public Response insertCamerasAsync(@QueryParam("size") @DefaultValue("100") int size) {
        service.insertAsync(size);
        return Response.accepted("Insertion of " + size + " cameras initiated.").build();
    }

}
