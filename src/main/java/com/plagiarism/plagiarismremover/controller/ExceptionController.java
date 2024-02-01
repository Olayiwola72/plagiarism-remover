package com.plagiarism.plagiarismremover.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("${plagiarism-remover.base-path}")
@Hidden
public class ExceptionController {
	@GetMapping("${plagiarism-remover.endpoint.internal-server-error}")
    public ResponseEntity<String> simulateInternalServerError() {
        throw new RuntimeException("Simulating an internal server error");
    }
}