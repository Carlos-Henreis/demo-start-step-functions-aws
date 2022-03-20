package br.com.carloshenreis.executeStepFunctions.config;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

public interface StepFunctionConfig {

	public SfnClient getSfnClient(Region region); 
	
}
