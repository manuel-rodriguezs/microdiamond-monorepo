package org.microdiamond.server.exceptions;

import javax.json.Json;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationErrorMapper implements ExceptionMapper<WebApplicationException>{

    @Override
    public Response toResponse(WebApplicationException e) {
        int code = e.getResponse().getStatus();
        return Response.status(code)
            .entity(Json.createObjectBuilder().add("error", e.getMessage()).build())
            .build();
    }
}
