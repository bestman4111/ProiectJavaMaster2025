package org.example.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "loans")
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "copy_id")
    private BookCopy copy;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnDate;

    public boolean isActive () {
        return returnDate == null;
    }
}
