package org.example.repository;

import org.example.domain.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByBookId(Long bookId);
    long countByBookIdAndStatus(Long bookId, BookCopy.Status status);
}
