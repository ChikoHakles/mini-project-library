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
    ResponseEntity<ResponseBody<ArrayList<Book>>> findAllBook() {
        ArrayList<Book> books = bookService.findAllBook();
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.OK.value(), "Data Found", null, books),
                HttpStatus.OK
        );
    }

    @GetMapping("/books/{id}")
    ResponseEntity<ResponseBody<Book>> findBookById(@PathVariable(name = "id") Integer id) {
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.OK.value(), "Data Found", null, book),
                HttpStatus.OK
        );
    }

    @PostMapping("/books")
    ResponseEntity<ResponseBody<Book>> saveBook(@Valid @Nullable @RequestBody Book book) {
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.CREATED.value(), "Data Created", null, bookService.saveBook(book)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/books/{id}")
    ResponseEntity<ResponseBody<Book>> updateBook(@PathVariable(name = "id") Integer id, @Nullable @RequestBody Book book) {
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.ACCEPTED.value(), "Data Updated", null, bookService.updateBook(id, book)),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<ResponseBody<Object>> deleteBook(@PathVariable(name = "id") Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.OK.value(), "Data Deleted", null, null),
                HttpStatus.OK
        );
    }
}