package br.com.carloshenreis.executeStepFunctions.services;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.carloshenreis.executeStepFunctions.config.StepFunctionConfig;
import br.com.carloshenreis.executeStepFunctions.domain.Output;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.ListStateMachinesResponse;
import software.amazon.awssdk.services.sfn.model.SfnException;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;
import software.amazon.awssdk.services.sfn.model.StartExecutionResponse;
import software.amazon.awssdk.services.sfn.model.StateMachineListItem;

@Service
public class StartStepFunctions {
	
	@Autowired
	private StepFunctionConfig sfnClientConnect;
	
	
	 // snippet-start:[stepfunctions.java2.list_machines.main]
    public static String getArnMachineByName(SfnClient sfnClient, String name) {

        try {
	        ListStateMachinesResponse response = sfnClient.listStateMachines();
	        List<StateMachineListItem> machines = response.stateMachines();
	        
	        StateMachineListItem machine = machines.stream()
	        		  .filter(machin -> name.equals(machin.name()))
	        		  .findAny()
	        		  .orElse(null);
	        
	        return machine.stateMachineArn();

        } catch (SfnException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
		return null;
    }

    @PostConstruct
    public void teste() {
    	Region region = Region.US_EAST_1;
    	ObjectMapper mapper = new ObjectMapper();
    	
    	Output saida = new Output("Carlos");
    	try {
			String json = mapper.writeValueAsString(saida);
			SfnClient sfnClient = sfnClientConnect.getSfnClient(region);
			
			
			String stateMachineArn = getArnMachineByName(sfnClient, "StepHalloWorld");
			
			String exeArn = startWorkflow(sfnClient,stateMachineArn, json);
	        System.out.println("The execution ARN is" +exeArn);
			sfnClient.close();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public static String startWorkflow(SfnClient sfnClient, String stateMachineArn, String jsonString) {
        // Specify the name of the execution by using a GUID value.
        UUID uuid = UUID.randomUUID();
        String uuidValue = uuid.toString();
        try {

            StartExecutionRequest executionRequest = StartExecutionRequest.builder()
                    .input(jsonString)
                    .stateMachineArn(stateMachineArn)
                    .name(uuidValue)
                    .build();

            StartExecutionResponse response = sfnClient.startExecution(executionRequest);
            return response.executionArn();


        } catch (SfnException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }

}
