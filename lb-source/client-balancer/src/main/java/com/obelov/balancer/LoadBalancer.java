package com.obelov.balancer;

import java.util.Optional;

@FunctionalInterface
public interface LoadBalancer {

	Optional<String> getServer();
}
