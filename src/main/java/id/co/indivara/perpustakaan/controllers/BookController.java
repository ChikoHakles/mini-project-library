package id.co.indivara.perpustakaan.controllers;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.entities.ResponseBody;
import id.co.indivara.perpustakaan.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    ResponseEntity<ResponseBody<ArrayList<Book>>> findAllBook() {
        ArrayList<Book> books = bookService.findAllBook();
        if (books == null) {
            return new ResponseEntity<>(
                    new ResponseBody<>(HttpStatus.NOT_FOUND.value(), "No Data", null, books),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.OK.value(), "Data Found", null, books),
                HttpStatus.OK
        );
    }

    @GetMapping("/books/{id}")
    ResponseEntity<ResponseBody<Book>> findBookById(@PathVariable(name = "id") Integer id) {
        Book book = bookService.findBookById(id);
        if (book == null) {
            return new ResponseEntity<>(
                    new ResponseBody<>(HttpStatus.NOT_FOUND.value(), "No Data", null, book),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new ResponseBody<>(HttpStatus.OK.value(), "Data Found", null, book),
                HttpStatus.OK
        );
    }
}