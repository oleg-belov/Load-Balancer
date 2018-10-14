package com.obelov.balancer;

@FunctionalInterface
public interface LoadBalancer {

	String getServer();
}
