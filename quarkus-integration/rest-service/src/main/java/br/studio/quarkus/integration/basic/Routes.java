package br.studio.quarkus.integration.basic;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class Routes extends RouteBuilder {

        private final Set<Fruit> fruits = Collections.synchronizedSet(new LinkedHashSet<>()); // Uses thread-safe
                                                                                              // collections to store
                                                                                              // fruits and legumes

        private final Set<Legume> legumes = Collections.synchronizedSet(new LinkedHashSet<>()); // Uses thread-safe
                                                                                                // collections to store
                                                                                                // fruits and legumes

        public Routes() {
                fruits.add(new Fruit("Apple", "Winter fruit"));
                fruits.add(new Fruit("Pineapple", "Tropical fruit"));
                fruits.add(new Fruit("Banana", "Tropical fruit"));

                legumes.add(new Legume("Carrot", "Root vegetable, usually orange"));
                legumes.add(new Legume("Zucchini", "Summer squash"));
                legumes.add(new Legume("Pea", "Pod fruit"));
        }

        @Override
        public void configure() throws Exception {

                // Configures the REST endpoints to use JSON for request/response binding
                restConfiguration().bindingMode(RestBindingMode.json);

                rest("/fruits") // configuração do endpoint /fruits
                                .get() // no método GET
                                .to("direct:getFruits") // encaminha a requisição para o endpoint getFruits

                                .post() // no método POST
                                .type(Fruit.class) // espera um objeto do tipo Fruit
                                .to("direct:addFruit"); // encaminha a requisição para o endpoint addFruit

                from("direct:getFruits") // endpoint getFruits
                                .to("log:br.studio.quarkus.integration.basic?level=INFO") // loga a requisição
                                .setBody(constant(fruits)); // retorna a lista de frutas

                from("direct:addFruit")// endpoint addFruit
                                .to("log:br.studio.quarkus.integration.basic?level=INFO") // loga a requisição
                                .process().body(Fruit.class, fruits::add) // adiciona a fruta recebida na lista de
                                                                          // frutas
                                .setBody().constant(fruits);// retorna a lista de frutas

                rest("/legumes") // configuração do endpoint /legumes
                                .get() // no método GET
                                .to("direct:getLegumes") // encaminha a requisição para o endpoint getLegumes

                                .post() // no método POST
                                .type(Legume.class) // espera um objeto do tipo Legume
                                .to("direct:addLegume"); // encaminha a requisição para o endpoint addLegume

                from("direct:getLegumes") // endpoint getLegumes
                                .to("log:br.studio.quarkus.integration.basic?level=INFO&showAll=true&multiline=true") // loga a requisição
                                .setBody(constant(legumes)); // retorna a lista de legumes

                from("direct:addLegume") // endpoint addLegume
                                //.to("log:br.studio.quarkus.integration.basic?level=INFO")// loga a requisição                               
                                .log(LoggingLevel.INFO, this.getClass().getName(), "Processando a mensagem: ${body}")// loga a requisição
                                .process().body(Legume.class, legumes::add)// adiciona o legume recebido na lista de                                                                           // legumes
                                .setBody().constant(legumes); // retorna a lista de legumes

        }

}
