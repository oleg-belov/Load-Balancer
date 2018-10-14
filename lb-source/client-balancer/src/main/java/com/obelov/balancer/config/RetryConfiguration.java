package com.obelov.balancer.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetryConfiguration {

	private int maxRetries;

	private int delay;

	private int maxDuration;
}