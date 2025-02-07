package com.evaluacion.demo.repositories.books;

import com.evaluacion.demo.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Libro,Long> {
}
