package com.slim.cft.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import com.slim.cft.dto.HealthCheckDTO;
import com.slim.cft.service.CftService;

@Liveness
@ApplicationScoped
public class CftHealthCheck implements HealthCheck {

	@Inject
	CftService cftService;

	@Override
	public HealthCheckResponse call() {

		HealthCheckDTO healthCheckDTO = cftService.healthCheck();

		if (healthCheckDTO.getExitCode() == 0) {
			return HealthCheckResponse.builder().up().name("CFT").withData("EXIT_CODE", healthCheckDTO.getExitCode())
					.withData("OUTPUT_MESSAGE", healthCheckDTO.getMessage()).build();
		} else {

			return HealthCheckResponse.builder().down().name("CFT").withData("EXIT_CODE", healthCheckDTO.getExitCode())
					.withData("ERROR_MESSAGE", healthCheckDTO.getErrorMessage()).build();
		}

	}

}
