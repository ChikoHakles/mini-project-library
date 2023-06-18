package id.co.indivara.perpustakaan.controllers;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.entities.ResponseBody;
import id.co.indivara.perpustakaan.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    ResponseEntity<ResponseBody<Object>> findAllBook() {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Dsata Found")
                        .data(bookService.findAllBook())
                        .build()
        );
    }

    @GetMapping("/books/{id}")
    ResponseEntity<ResponseBody<Object>> findBookById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message(("Data Found"))
                        .data(bookService.findBookById(id))
                        .build()
        );
    }

    @PostMapping("/books")
    ResponseEntity<ResponseBody<Object>> saveBook(@Valid @Nullable @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message(("Data Created"))
                        .data(bookService.saveBook(book))
                        .build()
        );
    }

    @PutMapping("/books/{id}")
    ResponseEntity<ResponseBody<Object>> updateBook(@PathVariable(name = "id") Integer id, @Nullable @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseBody.builder()
                        .status(HttpStatus.ACCEPTED.value())
                        .message(("Data Updated"))
                        .data(bookService.updateBook(id, book))
                        .build()
        );
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<ResponseBody<Object>> deleteBook(@PathVariable(name = "id") Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message(("Data Deleted"))
                        .build()
        );
    }
}