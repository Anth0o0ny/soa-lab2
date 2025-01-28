package org.anth0o0ny.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class CityNotFoundHandler implements ExceptionMapper<CityNotFoundException> {
    @Override
    public Response toResponse(CityNotFoundException e) {
        return Response.status(404).entity(
                String.format("City with id %s not found", e.getId())
        ).build();
    }
}
