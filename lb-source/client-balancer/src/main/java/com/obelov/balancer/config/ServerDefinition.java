package com.obelov.balancer.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
public class ServerDefinition {

	@NonNull
	@NotBlank
	private String url;

	private boolean enabled;

	private String zone;
}
