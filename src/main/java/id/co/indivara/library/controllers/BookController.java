package id.co.indivara.library.controllers;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.services.BookService;
import id.co.indivara.library.services.BorrowService;
import id.co.indivara.library.services.HistoryService;
import id.co.indivara.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ReaderService readerService;

    @GetMapping("/books")
    ResponseEntity<ResponseBody<Object>> findAllBook() {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Book Found")
                        .data(bookService.findAllBook())
                        .build()
        );
    }

    @GetMapping("/books/{id}")
    ResponseEntity<ResponseBody<Object>> findBookById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Book Found"))
                        .data(bookService.findBookById(id))
                        .build()
        );
    }

    @GetMapping("/books/{id}/unreturned")
    ResponseEntity<ResponseBody<Object>> findBookUnreturned(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Unreturned Book Found")
                        .data(borrowService.findUnreturnedBorrowByBook(bookService.findBookById(id)))
                        .build()
        );
    }

    @GetMapping("/books/{id}/history")
    ResponseEntity<ResponseBody<Object>> findBookHistory(@PathVariable(name = "id") Integer id, @RequestParam(name = "readerId", required = false) Integer readerId) {
        if(readerId != null) {
            return ResponseEntity.ok(
                    ResponseBody.builder()
                            .status(HttpStatus.OK.value())
                            .message("History Found")
                            .data(historyService.findHistoryByBookAndReader(bookService.findBookById(id), readerService.findReaderById(readerId)))
                            .build()
            );
        }
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("History Found")
                        .data(historyService.findHistoryByBook(bookService.findBookById(id)))
                        .build()
        );
    }

    @PostMapping("/books")
    ResponseEntity<ResponseBody<Object>> saveBook(@Valid @Nullable @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message(("Book Created"))
                        .data(bookService.saveBook(book))
                        .build()
        );
    }

    @PutMapping("/books/{id}")
    ResponseEntity<ResponseBody<Object>> updateBook(@PathVariable(name = "id") Integer id, @Nullable @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseBody.builder()
                        .status(HttpStatus.ACCEPTED.value())
                        .message(("Book Updated"))
                        .data(bookService.updateBook(id, book))
                        .build()
        );
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<ResponseBody<Object>> deleteBook(@PathVariable(name = "id") Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Book Deleted"))
                        .build()
        );
    }
}