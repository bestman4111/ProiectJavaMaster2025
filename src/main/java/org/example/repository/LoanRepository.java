package org.example.repository;

import org.example.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    long countByMemberIdAndReturnDateIsNull(Long memberId);
    List<Loan> findByMemberIdOrderByStartDateDesc(Long memberId);
}
