package com.plagiarism.plagiarismremover.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("${plagiarism-remover.base-path}")
@Hidden
public class ExceptionController {
	
	@Operation(summary = "Simulating an internal server error")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Internal Server Error Simulation Successful", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = String.class)) }),
	  @ApiResponse(responseCode = "400", description = "Simulation Failed", 
	    content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Resource Not Found", 
	    content = @Content) 
	})
	@GetMapping("${plagiarism-remover.endpoint.internal-server-error}")
    public ResponseEntity<String> simulateInternalServerError() {
        throw new RuntimeException("Simulating an internal server error");
    }
}