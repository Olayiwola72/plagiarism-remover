package com.plagiarism.plagiarismremover.entity.errors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import jakarta.persistence.Entity;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorFields {
	private String errorMessage;
	private String fieldName;
	
	public ErrorFields() {}
	
	public ErrorFields(String errorMessage, String fieldName) {
		this.errorMessage = errorMessage;
		this.fieldName = fieldName;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}