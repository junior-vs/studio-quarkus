package br.studio.quarkus.services;


import br.studio.quarkus.services.wscalculator.CalculatorService;
import io.quarkiverse.cxf.annotation.CXFClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/cxfclient")
public class CxfClientRestResource {

    @CXFClient("CalculatorService")
    CalculatorService calculatorService;

    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public int add(@QueryParam("a") int a, @QueryParam("b") int b) {
        return calculatorService.add(a, b);
    }

}
