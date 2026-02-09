package com.example.cashinvoice.Camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "activemq.enabled", havingValue = "true")
public class ActiveMqConsumerRoute extends RouteBuilder {


    @Override
    public void configure() {

        from("activemq:queue:order.queue")
                .routeId("activemq-consumer-route")
                .log("Message received from ActiveMq: ${body}");

    }
}
