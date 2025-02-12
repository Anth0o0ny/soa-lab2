package org.anth0o0ny.resources;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

    @Path("/ping")
    @Produces("text/plain")
    public class PingResource {

        @GET
        public Response ping() {
            return Response.ok().entity("Pong").build();
        }

    }
