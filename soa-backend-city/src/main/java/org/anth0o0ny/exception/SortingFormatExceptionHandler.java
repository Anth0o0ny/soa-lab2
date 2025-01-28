package org.anth0o0ny.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SortingFormatExceptionHandler implements ExceptionMapper<SortingFormatException> {
    @Override
    public Response toResponse(SortingFormatException e) {
        return Response.status(400).entity(
                "Sorting arguments are not valid. \nFormat is: <field_name>,<asc/desc>.\n Allowed fields: \n\"name\", \"coordinates.x\", \"coordinates.y\", \"id\", \"tagline\", \"creationDate\", \"oscarsCount\", \"usaBoxOffice\", \"genre\", \"screenwriter.name\". \n Valid examples are: ?sort=id,desc&sort=name,asc&sort=oscarsCount,asc"
        ).build();
    }
}
