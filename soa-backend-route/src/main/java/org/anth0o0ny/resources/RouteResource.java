package org.anth0o0ny.resources;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.anth0o0ny.dto.CityDto;
import org.anth0o0ny.service.CityService;

@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
public class RouteResource {

    @Inject
    private CityService cityService;

    @GET
    @Path("/calculate/between-largest-and-smallest")
    public Response calculateBetweenLargestAndSmallest() {
        try {
            CityDto largestCity = cityService.getCityWithLargestArea();
            CityDto smallestCity = cityService.getCityWithSmallestArea();

            if (largestCity == null || smallestCity == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Cities not found").build();
            }

            double distance = cityService.calculateDistanceBetweenCities(largestCity, smallestCity);
            return Response.ok(distance).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/calculate/to-min-populated")
    public Response calculateToMinPopulated() {
        try {
            CityDto minPopulatedCity = cityService.getCityWithMinPopulation();

            if (minPopulatedCity == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("City not found").build();
            }

            double distance = cityService.calculateDistanceFromOrigin(minPopulatedCity);
            return Response.ok(distance).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}