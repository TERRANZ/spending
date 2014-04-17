package ru.terra.spending.controller;

import com.sun.jersey.api.core.HttpContext;
import org.apache.log4j.Logger;
import ru.terra.server.controller.AbstractResource;
import ru.terra.spending.constants.URLConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Date: 13.02.14
 * Time: 11:47
 */
@Path(URLConstants.Resources.RESOURCES)
public class ResourceController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Path("/js/{path}")
    @GET
    public Response getJS(@Context HttpContext hc, @PathParam("path") String path) {
        return AbstractResource.getFile("resources/js/" + path);
    }

    @Path("/js/jquery/{path}")
    @GET
    public Response getJQuery(@Context HttpContext hc, @PathParam("path") String path) {
        return AbstractResource.getFile("resources/js/jquery/" + path);
    }

    @Path("/css/{path}")
    @GET
    public Response getCSS(@Context HttpContext hc, @PathParam("path") String path) {
        return AbstractResource.getFile("resources/css/" + path);
    }


    @Path("/img/{path}")
    @GET
    public Response getImg(@Context HttpContext hc, @PathParam("path") String path) {
        return AbstractResource.getFile("resources/img/" + path);
    }
}
