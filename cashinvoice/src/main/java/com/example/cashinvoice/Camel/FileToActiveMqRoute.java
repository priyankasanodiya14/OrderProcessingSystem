package com.example.cashinvoice.Camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileToActiveMqRoute extends RouteBuilder {

    @Override
    public void configure()  {
        from("file:input?noop=true")
                .routeId("file-to-activemq-route")
                .log("Processing file: ${header.CamelFileName}")
                .to("activemq:queue:order.queue")
                .log("Message sent to ActiveMQ queue");
    }
}
