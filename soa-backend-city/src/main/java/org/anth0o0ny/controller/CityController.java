package org.anth0o0ny.controller;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.anth0o0ny.dto.PageDto;
import org.anth0o0ny.enums.Climate;
import org.anth0o0ny.enums.Government;
import org.anth0o0ny.enums.StandardOfLiving;
import org.anth0o0ny.model.entity.City;
import org.anth0o0ny.service.CityService;

import java.util.List;

@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityController {

    @Inject
    private CityService cityService;

    @GET
    public Response getCities(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sort") List<String> sort,
            @QueryParam("name") String nameValue,
            @QueryParam("name-filter") String nameFilter,
            @QueryParam("id") String idValue,
            @QueryParam("id-filter") String idFilter,
            @QueryParam("population") String populationValue,
            @QueryParam("population-filter") String populationFilter,
            @QueryParam("coordinates.x") String coordinatesXValue,
            @QueryParam("coordinates.x-filter") String coordinatesXFilter,
            @QueryParam("coordinates.y") String coordinatesYValue,
            @QueryParam("coordinates.y-filter") String coordinatesYFilter,
            @QueryParam("area") String areaValue,
            @QueryParam("area-filter") String areaFilter,
            @QueryParam("metersAboveSeaLevel") String metersAboveSeaLevelValue,
            @QueryParam("metersAboveSeaLevel-filter") String metersAboveSeaLevelFilter,
            @QueryParam("climate") String climateValue,
            @QueryParam("climate-filter") String climateFilter,
            @QueryParam("government") String governmentValue,
            @QueryParam("government-filter") String governmentFilter,
            @QueryParam("standardOfLiving") String standardOfLivingValue,
            @QueryParam("standardOfLiving-filter") String standardOfLivingFilter,
            @QueryParam("age") String ageValue,
            @QueryParam("age-filter") String ageFilter) {

        PageDto<City> cities = cityService.findAll(page, size, sort,
                nameValue, nameFilter,
                idValue, idFilter,
                populationValue, populationFilter,
                coordinatesXValue, coordinatesXFilter,
                coordinatesYValue, coordinatesYFilter,
                areaValue, areaFilter,
                metersAboveSeaLevelValue, metersAboveSeaLevelFilter,
                climateValue, climateFilter,
                governmentValue, governmentFilter,
                standardOfLivingValue, standardOfLivingFilter,
                ageValue, ageFilter);
        return Response.ok(cities).build();
    }

    @GET
    @Path("/{id}")
    public Response getCity(@PathParam("id") Integer id) {
        City city = cityService.findById(id);
        return Response.ok(city).build();
    }

    @POST
    public Response uploadCity(@Valid City city) {
        cityService.save(city);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCity(@PathParam("id") Integer id, @Valid City city) {
        cityService.update(id, city);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCity(@PathParam("id") Integer id) {
        cityService.delete(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete-by-climate")
    public Response deleteCityByClimate(@QueryParam("climate") Climate climate) {
        cityService.deleteByClimate(climate);
        return Response.ok().build();
    }

    @GET
    @Path("/count-by-standard-of-living")
    public Response countByStandardOfLiving(@QueryParam("standardOfLiving") String standardOfLiving) {
        long count = cityService.countByStandardOfLivingLessThan(StandardOfLiving.valueOf(standardOfLiving));
        return Response.ok(count).build();
    }

    @GET
    @Path("/government-less-than")
    public Response findByGovernmentLessThan(
            @QueryParam("government") Government government) {
        List<City> cities = cityService.findByGovernmentLessThan(government);
        return Response.ok(cities).build();
    }
}

