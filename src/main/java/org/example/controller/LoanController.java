package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.LoanCreateRequest;
import org.example.dto.response.FineResponse;
import org.example.dto.response.LoanResponse;
import org.example.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @Operation(summary = "Create a loan")
    @PostMapping("/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse create(@Valid @RequestBody LoanCreateRequest request) {
        return loanService.createLoan(request);
    }

    @Operation(summary = "Return a loan")
    @PostMapping("/loans/{loanId}/return")
    public LoanResponse returnLoan(@PathVariable("loanId") long loanId) {
        return loanService.returnLoan(loanId);
    }

    @Operation(summary = "Get member loan history")
    @GetMapping("/members/{memberId}/loans")
    public List<LoanResponse> memberLoans(@PathVariable("memberId") long memberId) {
        return loanService.memberLoans(memberId);
    }

    @Operation(summary = "Get fine for a loan")
    @GetMapping("/loans/{loanId}/fine")
    public FineResponse fine(@PathVariable("loanId") long loanId) {
        return loanService.fineForLoan(loanId);
    }
}
