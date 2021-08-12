package edu.uchicago.tiang.resources;

import edu.uchicago.tiang.models.FavoriteItem;
import edu.uchicago.tiang.services.FavsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/favs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavsResource {

    @Inject
    FavsService favsService;

    @POST
    @Path("/add")
    public FavoriteItem add(FavoriteItem favoriteItem){
        return favsService.add(favoriteItem);
    }

    @GET
    @Path("/get/{id}")
    public FavoriteItem get(@PathParam("id") String id){
        return favsService.get(id);
    }

    @GET
    @Path("/get")
    public List<FavoriteItem> getAll(){
        return favsService.getAll();
    }

    @DELETE
    @Path("/delete/{id}")
    public FavoriteItem delete(@PathParam("id") String id){
        return favsService.delete(id);
    }

    @GET
    @Path("/paged/{page}")
    public List<FavoriteItem> paged(@PathParam("page") int page){
        return favsService.paged(page);
    }

    @PUT
    @Path("/update/{id}")
    public FavoriteItem update(FavoriteItem item, @PathParam("id") String id){
        return favsService.update(item, id);
    }

}