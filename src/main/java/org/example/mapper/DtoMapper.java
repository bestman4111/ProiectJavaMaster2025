package org.example.mapper;

import org.example.domain.*;
import org.example.dto.response.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoMapper {
    public MemberResponse toMemberResponse(Member m) {
        return new MemberResponse(m.getId(), m.getFullName(), m.getEmail());
    }

    public BookResponse toBookResponse(Book b) {
        return new BookResponse(
                b.getId(),
                b.getTitle(),
                b.getIsbn(),
                b.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()),
                b.getCategories().stream().map(Category::getName).collect(Collectors.toSet())
        );
    }

    public BookCopyResponse toCopyResponse(BookCopy c) {
        return new BookCopyResponse(c.getId(), c.getBook().getId(), c.getStatus().name());
    }

    public LoanResponse toLoanResponse(Loan l) {
        return new LoanResponse(l.getId(), l.getMember().getId(), l.getCopy().getId(), l.getStartDate(), l.getDueDate(), l.getReturnDate());
    }

    public ReservationResponse toReservationResponse(Reservation r) {
        return new ReservationResponse(r.getId(), r.getMember().getId(), r.getBook().getId(), r.getCreatedAt());
    }
}
