package br.com.carloshenreis.executeStepFunctions.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

@Profile("local")
@Configuration
public class AwsStepFunctionConfigLocal implements StepFunctionConfig{

	@Override
	public SfnClient getSfnClient(Region region) {
		SfnClient sfnClient;
		try {
			sfnClient = SfnClient.builder()
			    .region(region)
			    .endpointOverride(new URI("http://localhost:4566"))
			    .build();
			return sfnClient;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
