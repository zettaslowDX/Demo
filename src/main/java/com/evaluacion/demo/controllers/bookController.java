package com.evaluacion.demo.controllers;

import com.evaluacion.demo.models.Libro;
import com.evaluacion.demo.services.books.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/libros")
public class bookController {

    BooksService booksService;

    @Autowired
    public bookController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getBookById(@PathVariable long id) {
        Libro libro = booksService.getBookById(id);
        return ResponseEntity.ok(libro);
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getAllBooks() {
        List<Libro> libros = booksService.getAllBooks();
        return ResponseEntity.ok(libros);
    }

    @PostMapping
    public ResponseEntity<Libro> createBook(@RequestBody Libro libro) {
        Libro createdBook = booksService.createBook(libro);
        return new ResponseEntity<Libro>(libro, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Libro> updateBook(@RequestBody Libro libro) {
        Libro updatedBook = booksService.updateBook(libro);
        return ResponseEntity.ok(libro);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Libro> patchBook(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Libro patchedBook = booksService.patchBook(id, updates);
        return ResponseEntity.ok(patchedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {
        booksService.deleteBook(id);
        return ResponseEntity.ok(id);
    }
}