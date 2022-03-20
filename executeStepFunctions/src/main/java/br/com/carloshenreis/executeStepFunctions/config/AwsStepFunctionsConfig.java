package br.com.carloshenreis.executeStepFunctions.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

@Profile("aws")
@Configuration
public class AwsStepFunctionsConfig  implements StepFunctionConfig{

	@Override
	public SfnClient getSfnClient(Region region) {
		SfnClient sfnClient;
		sfnClient = SfnClient.builder()
		    .region(region)
		    .build();
		return sfnClient;
	}

}
