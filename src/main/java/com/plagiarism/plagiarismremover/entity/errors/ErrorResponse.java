package com.plagiarism.plagiarismremover.entity.errors;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import jakarta.persistence.Entity;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorResponse {
	private ArrayList<ErrorFields> errors;
	
	public ErrorResponse() {
		this.errors = new ArrayList<>();
	}
	
	public ErrorResponse(String errorMessage, String fieldName) {
		this();
		ErrorFields errorFields = new ErrorFields(errorMessage, fieldName);
		this.addErrorFields(errorFields);
	}
	
	public ArrayList<ErrorFields> getErrors() {
		return this.errors;
	}
	
	public void addErrorFields(ErrorFields errorFields) {
		this.errors.add(errorFields);
	}
}