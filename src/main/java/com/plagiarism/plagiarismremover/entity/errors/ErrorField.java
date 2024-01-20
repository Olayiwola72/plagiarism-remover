package com.plagiarism.plagiarismremover.entity.errors;

import jakarta.persistence.Entity;

@Entity
public class ErrorField {
	private String errorMessage;
	private String fieldName;
	
	public ErrorField(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}
	
	public ErrorField(String errorMessage, String fieldName) {
		this.setErrorMessage(errorMessage);
		this.setFieldName(fieldName);
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