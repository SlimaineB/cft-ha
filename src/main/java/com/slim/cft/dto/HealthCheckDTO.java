package com.slim.cft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class HealthCheckDTO {

	private String message;
	private String errorMessage;
	private boolean success;
	private int exitCode;
	





}
