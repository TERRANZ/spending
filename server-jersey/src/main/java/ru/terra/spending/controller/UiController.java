package ru.terra.spending.controller;

import com.sun.jersey.api.core.HttpContext;
import org.apache.log4j.Logger;
import ru.terra.server.controller.AbstractResource;
import ru.terra.spending.constants.URLConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Date: 16.04.14
 * Time: 15:36
 */
@Path(URLConstants.UI.UI)
public class UiController extends AbstractResource {
    private Logger logger = Logger.getLogger(this.getClass());

    @Path(URLConstants.UI.MAIN)
    @GET
    @Produces({"text/html"})
    public Response getMain(@Context HttpContext hc) {
        if (isAuthorized(hc))
            return returnHtmlFile("main.html");
        else
            return getLogin(hc);
    }

    @Path(URLConstants.UI.LOGIN)
    @GET
    @Produces({"text/html"})
    public Response getLogin(@Context HttpContext hc) {
        return returnHtmlFile("login.html");
    }

    @Path(URLConstants.UI.REG)
    @GET
    @Produces({"text/html"})
    public Response getReg(@Context HttpContext hc) {
        return returnHtmlFile("reg.html");
    }

    @Path(URLConstants.UI.TRANSACTIONS)
    @GET
    @Produces({"text/html"})
    public Response getTransactions(@Context HttpContext hc) {
        return returnHtmlFile("transactions.html");
    }
}