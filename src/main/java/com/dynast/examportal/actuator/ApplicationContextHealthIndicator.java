package com.dynast.examportal.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("health")
public class ApplicationContextHealthIndicator implements HealthIndicator {

    public static int counter;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        counter++;
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return null;
    }

    @Override
    public Health health() {
        if (counter == 0) {
            return Health.down().withDetail("Error Code", 500).build();
        }
        return Health.up().build();
    }
}
