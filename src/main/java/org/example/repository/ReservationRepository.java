package org.example.repository;

import org.example.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBookIdOrderByCreatedAtAsc(Long bookId);
}
