package com.plagiarism.plagiarismremover.entity.errors;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
	private List<ErrorField> errors;

    public ErrorResponse() {
        this.errors = new ArrayList<>();
    }
    
    public ErrorResponse(String errorMessage) {
        this();
        ErrorField errorField = new ErrorField(errorMessage);
        this.addErrors(errorField);
    }

    public ErrorResponse(String errorMessage, String fieldName) {
        this();
        ErrorField errorField = new ErrorField(errorMessage, fieldName);
        this.addErrors(errorField);
    }

    public List<ErrorField> getErrors() {
        return this.errors;
    }

    public void addErrors(ErrorField errorField) {
        this.errors.add(errorField);
    }
}
