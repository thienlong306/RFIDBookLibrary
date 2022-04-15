package com.example.seminarbooklibrary.Repository;

import com.example.seminarbooklibrary.Domain.BookDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookDomain,Long> {
    List<BookDomain> findByTitleBookContaining(String titleBook);
    List<BookDomain> findByAuthorBookContaining(String authorBook);
    @Query(nativeQuery = true,value = "SELECT * FROM books WHERE book_title LIKE %:contentSearch% OR book_author LIKE %:contentSearch%")
    List<BookDomain> findAllByTitleBookOrAuthorBookContaining(String contentSearch);
    BookDomain findTopByOrderByIdBookDesc();
}
