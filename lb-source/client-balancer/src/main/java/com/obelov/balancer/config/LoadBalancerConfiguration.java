package com.obelov.balancer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Validated
public class LoadBalancerConfiguration {

	@NotNull
	private List<ServerDefinition> servers;
}
