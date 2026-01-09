package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.domain.Book;
import org.example.domain.Member;
import org.example.domain.Reservation;
import org.example.dto.request.ReservationCreateRequest;
import org.example.dto.response.ReservationResponse;
import org.example.exception.BusinessRuleException;
import org.example.exception.NotFoundException;
import org.example.mapper.DtoMapper;
import org.example.repository.BookCopyRepository;
import org.example.repository.BookRepository;
import org.example.repository.MemberRepository;
import org.example.repository.ReservationRepository;
import org.example.service.ReservationService;
import org.springframework.stereotype.Service;

import static org.example.domain.BookCopy.Status.AVAILABLE;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository copyRepository;
    private final DtoMapper mapper;

    @Override
    public ReservationResponse create(ReservationCreateRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("Member not found: " + request.memberId()));
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new NotFoundException("Book not found: " + request.bookId()));

        long available = copyRepository.countByBookIdAndStatus(book.getId(), AVAILABLE);
        if (available > 0) {
            throw new BusinessRuleException("Book has available copies; reservation not needed");
        }

        Reservation r = Reservation.builder().member(member).book(book).build();
        return mapper.toReservationResponse(reservationRepository.save(r));
    }
}
