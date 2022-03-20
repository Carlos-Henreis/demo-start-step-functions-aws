package br.com.carloshenreis.executeStepFunctions.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Output {
	
	@JsonProperty("first_name")
	String firstName;
	
}
