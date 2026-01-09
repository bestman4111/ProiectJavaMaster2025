package org.example.service;

import org.example.dto.request.LoanCreateRequest;
import org.example.dto.response.FineResponse;
import org.example.dto.response.LoanResponse;

import java.util.List;

public interface LoanService {
    LoanResponse createLoan(LoanCreateRequest request);
    LoanResponse returnLoan(Long loanId);
    List<LoanResponse> memberLoans(Long memberId);
    FineResponse fineForLoan(Long loanId);
}
