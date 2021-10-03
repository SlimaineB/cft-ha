package com.slim.cft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.enterprise.context.ApplicationScoped;

import com.slim.cft.dto.HealthCheckDTO;

@ApplicationScoped
public class CftServiceImpl implements CftService {

	private static final String USER_HOME_SYSTEM_PROPERTY = "user.home";
	private static final String OS_NAME_SYSTEM_PROPERTY = "os.name";
	private static final String CFT_COMMAND = "cftping -p";

	public String sendFile(String partner) {

		return null;

	}

	public HealthCheckDTO healthCheck() {
		int exitCode = -1;
		try {
			boolean isWindows = System.getProperty(OS_NAME_SYSTEM_PROPERTY).toLowerCase().startsWith("windows");
			ProcessBuilder builder = new ProcessBuilder();
			if (isWindows) {
				builder.command("cmd.exe", "/c", CFT_COMMAND);
			} else {
				builder.command("sh", "-c", CFT_COMMAND);
			}
			builder.directory(new File(System.getProperty(USER_HOME_SYSTEM_PROPERTY)));

			Process process = builder.start();

			exitCode = process.waitFor();

			if (exitCode == 0) {
				StringBuilder strMessage = new StringBuilder();
				new BufferedReader(new InputStreamReader(process.getInputStream())).lines()
						.forEach(msg -> strMessage.append(msg));

				return HealthCheckDTO.builder().success(true).message(strMessage.toString()).build();
			} else {
				StringBuilder strErrorMessage = new StringBuilder();
				new BufferedReader(new InputStreamReader(process.getErrorStream())).lines()
						.forEach(msg -> strErrorMessage.append(msg));
				return HealthCheckDTO.builder().success(false).message(strErrorMessage.toString()).exitCode(exitCode)
						.build();

			}

		} catch (Exception ex) {

			return HealthCheckDTO.builder().success(false).message(ex.getMessage()).exitCode(exitCode).build();
		}

	}

}
