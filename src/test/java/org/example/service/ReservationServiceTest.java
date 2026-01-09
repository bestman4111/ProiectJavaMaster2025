package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookCopy;
import org.example.domain.Member;
import org.example.domain.Reservation;
import org.example.dto.request.ReservationCreateRequest;
import org.example.exception.BusinessRuleException;
import org.example.mapper.DtoMapper;
import org.example.repository.BookCopyRepository;
import org.example.repository.BookRepository;
import org.example.repository.MemberRepository;
import org.example.repository.ReservationRepository;
import org.example.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationServiceTest {
    @Test
    void create_shouldFail_whenCopiesAvailable() {
        ReservationRepository reservationRepo = mock(ReservationRepository.class);
        MemberRepository memberRepo = mock(MemberRepository.class);
        BookRepository bookRepo = mock(BookRepository.class);
        BookCopyRepository copyRepo = mock(BookCopyRepository.class);

        ReservationService service = new ReservationServiceImpl(reservationRepo, memberRepo, bookRepo, copyRepo, new DtoMapper());

        when(memberRepo.findById(1L)).thenReturn(Optional.of(Member.builder().id(1L).email("a@b.com").build()));
        when(bookRepo.findById(2L)).thenReturn(Optional.of(Book.builder().id(2L).title("T").isbn("1234567890").build()));
        when(copyRepo.countByBookIdAndStatus(2L, BookCopy.Status.AVAILABLE)).thenReturn(1L);

        assertThrows(BusinessRuleException.class,
                () -> service.create(new ReservationCreateRequest(1L, 2L)));
    }

    @Test
    void create_shouldSucceed_whenNoCopiesAvailable() {
        ReservationRepository reservationRepo = mock(ReservationRepository.class);
        MemberRepository memberRepo = mock(MemberRepository.class);
        BookRepository bookRepo = mock(BookRepository.class);
        BookCopyRepository copyRepo = mock(BookCopyRepository.class);

        ReservationService service = new ReservationServiceImpl(reservationRepo, memberRepo, bookRepo, copyRepo, new DtoMapper());

        when(memberRepo.findById(1L)).thenReturn(Optional.of(Member.builder().id(1L).fullName("A").email("a@b.com").build()));
        when(bookRepo.findById(2L)).thenReturn(Optional.of(Book.builder().id(2L).title("T").isbn("1234567890").build()));
        when(copyRepo.countByBookIdAndStatus(2L, BookCopy.Status.AVAILABLE)).thenReturn(0L);

        when(reservationRepo.save(any(Reservation.class))).thenAnswer(inv -> {
            Reservation r = inv.getArgument(0);
            r.setId(99L);
            return r;
        });

        var res = service.create(new ReservationCreateRequest(1L, 2L));
        assertEquals(99L, res.id());
        verify(reservationRepo).save(any(Reservation.class));
    }
}
