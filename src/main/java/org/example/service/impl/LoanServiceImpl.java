package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.BookCopy;
import org.example.domain.Loan;
import org.example.domain.Member;
import org.example.dto.request.LoanCreateRequest;
import org.example.dto.response.FineResponse;
import org.example.dto.response.LoanResponse;
import org.example.exception.BusinessRuleException;
import org.example.exception.NotFoundException;
import org.example.mapper.DtoMapper;
import org.example.repository.BookCopyRepository;
import org.example.repository.LoanRepository;
import org.example.repository.MemberRepository;
import org.example.service.LoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private static final int MAX_ACTIVE_LOANS = 5;
    private static final int LOAN_DAYS = 14;
    private static final double FINE_PER_DAY = 2.5;

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookCopyRepository copyRepository;
    private final DtoMapper mapper;

    @Override
    @Transactional
    public LoanResponse createLoan(LoanCreateRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("Member not found: " + request.memberId()));
        BookCopy copy = copyRepository.findById(request.copyId())
                .orElseThrow(() -> new NotFoundException("Copy not found: " + request.copyId()));

        if(copy.getStatus() != BookCopy.Status.AVAILABLE) {
            throw new BusinessRuleException("Copy is not available");
        }
        long activeLoans = loanRepository.countByMemberIdAndReturnDateIsNull(member.getId());
        if(activeLoans >= MAX_ACTIVE_LOANS) {
            throw new BusinessRuleException("Member reached max active loans: " + MAX_ACTIVE_LOANS);
        }

        copy.setStatus(BookCopy.Status.LOANED);

        LocalDate start = LocalDate.now();
        Loan loan = Loan.builder()
                .member(member)
                .copy(copy)
                .startDate(start)
                .dueDate(start.plusDays(LOAN_DAYS))
                .build();

        copyRepository.save(copy);
        return mapper.toLoanResponse(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public LoanResponse returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found: " + loanId));
        if(!loan.isActive()) {
            throw new BusinessRuleException("Loan is already returned");
        }

        loan.setReturnDate(LocalDate.now());

        BookCopy copy = loan.getCopy();
        copy.setStatus(BookCopy.Status.AVAILABLE);

        copyRepository.save(copy);
        return mapper.toLoanResponse(loanRepository.save(loan));
    }

    @Override
    public List<LoanResponse> memberLoans(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundException("Member not found: " + memberId);
        }
        return loanRepository.findByMemberIdOrderByStartDateDesc(memberId)
                .stream().map(mapper::toLoanResponse).toList();
    }

    @Override
    public FineResponse fineForLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found: " + loanId));

        LocalDate end = (loan.getReturnDate() != null) ? loan.getReturnDate() : LocalDate.now();
        long overdueDays = Math.max(0, ChronoUnit.DAYS.between(loan.getDueDate(), end));
        double fine = overdueDays * FINE_PER_DAY;

        return new FineResponse(loanId, overdueDays, fine);
    }
}
