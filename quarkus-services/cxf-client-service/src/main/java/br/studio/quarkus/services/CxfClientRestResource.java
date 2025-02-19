package br.studio.quarkus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.studio.quarkus.services.wscalculator.CalculatorService;
import io.quarkiverse.cxf.annotation.CXFClient;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/cxfclient")
public class CxfClientRestResource {

    @CXFClient("CalculatorService")
    CalculatorService calculatorService;

    Logger logger = LoggerFactory.getLogger(CxfClientRestResource.class);

    /*
     * This method is used to add two numbers
     * 
     * @param a
     * 
     * @param b
     * 
     * @return int
     */
    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public int add(@QueryParam("a") int a, @QueryParam("b") int b) {
        return calculatorService.add(a, b);
    }

    /*
     * This method is used to subtract two numbers
     * 
     * @param a
     * 
     * @param b
     * 
     * @return int
     */
    @POST
    @Path("/subtract")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public int subtract(OperacaoRequest operacaoRequest) {
        logger.info("Request: " + operacaoRequest);
        return calculatorService.subtract(operacaoRequest.a(), operacaoRequest.b());
    }

}
