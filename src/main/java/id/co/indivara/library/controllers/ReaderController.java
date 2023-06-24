package id.co.indivara.library.controllers;

import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ReaderController {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private BookService bookService;

    @GetMapping("/readers")
    ResponseEntity<ResponseBody<Object>> findAllReader() {
        ArrayList<Reader> readers = readerService.findAllReader();
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Reader Found")
                        .data(readers)
                        .build()
        );
    }

    @GetMapping("/readers/{id}")
    ResponseEntity<ResponseBody<Object>> findReaderById(@PathVariable(name = "id") Integer id) {
        Reader reader = readerService.findReaderById(id);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Reader Found")
                        .data(reader)
                        .build()
        );
    }

    @GetMapping("/readers/{id}/wishlists")
    ResponseEntity<ResponseBody<Object>> findReaderWishlists(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Wishlist Found")
                        .data(wishlistService.findAllWishlistByReader(readerService.findReaderById(id)))
                        .build()
        );
    }

    @GetMapping("/readers/{id}/unreturned")
    ResponseEntity<ResponseBody<Object>> findReaderUnreturned(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Unreturned Book Found")
                        .data(borrowService.findUnreturnedBorrowByReader(readerService.findReaderById(id)))
                        .build()
        );
    }

    @GetMapping("/readers/{id}/history")
    ResponseEntity<ResponseBody<Object>> findReaderHistory(@PathVariable(name = "id") Integer id, @RequestParam(name = "bookId", required = false) Integer bookId) {
        if(bookId != null) {
            return ResponseEntity.ok(
                    ResponseBody.builder()
                            .status(HttpStatus.OK.value())
                            .message("History Found")
                            .data(historyService.findHistoryByBookAndReader(bookService.findBookById(bookId), readerService.findReaderById(id)))
                            .build()
            );
        }
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("History Found")
                        .data(historyService.findHistoryByReader(readerService.findReaderById(id)))
                        .build()
        );
    }

    @PostMapping("/readers")
    ResponseEntity<ResponseBody<Object>> saveReader(@Valid @Nullable @RequestBody Reader reader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Reader Created")
                        .data(readerService.saveReader(reader))
                        .build()
        );
    }

    @PutMapping("/readers/{id}")
    ResponseEntity<ResponseBody<Object>> updateReader(@PathVariable(name = "id") Integer id, @Nullable @RequestBody Reader reader) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseBody.builder()
                        .status(HttpStatus.ACCEPTED.value())
                        .message(("Reader Updated"))
                        .data(readerService.updateReader(id, reader))
                        .build()
        );
    }

    @DeleteMapping("/readers/{id}")
    ResponseEntity<ResponseBody<Object>> deleteBook(@PathVariable(name = "id") Integer id) {
        readerService.deleteReader(id);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Reader Deleted"))
                        .build()
        );
    }
}
