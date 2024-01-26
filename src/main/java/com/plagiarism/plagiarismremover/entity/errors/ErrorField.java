package com.plagiarism.plagiarismremover.entity.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;

@Entity
@Schema(description = "Error Response Field Information")
public class ErrorField {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "error Message", example = "field cannot be blank")
	private String errorMessage;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "field Name", example = "message")
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