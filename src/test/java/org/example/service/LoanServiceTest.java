package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookCopy;
import org.example.domain.Member;
import org.example.dto.request.LoanCreateRequest;
import org.example.exception.BusinessRuleException;
import org.example.mapper.DtoMapper;
import org.example.repository.BookCopyRepository;
import org.example.repository.LoanRepository;
import org.example.repository.MemberRepository;
import org.example.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoanServiceTest {
    @Test
    void createLoan_shouldFail_whenCopyNotAvailable() {
        LoanRepository loanRepo = mock(LoanRepository.class);
        MemberRepository memberRepo = mock(MemberRepository.class);
        BookCopyRepository copyRepo = mock(BookCopyRepository.class);

        LoanService service = new LoanServiceImpl(loanRepo, memberRepo, copyRepo, new DtoMapper());

        Member member = Member.builder().id(1L).fullName("X").email("x@y.com").build();
        Book book = Book.builder().id(2L).title("T").isbn("1234567890").build();
        BookCopy copy = BookCopy.builder().id(10L).book(book).status(BookCopy.Status.LOANED).build();

        when(memberRepo.findById(1L)).thenReturn(Optional.of(member));
        when(copyRepo.findById(10L)).thenReturn(Optional.of(copy));

        assertThrows(BusinessRuleException.class,
                () -> service.createLoan(new LoanCreateRequest(1L, 10L)));
    }
}
