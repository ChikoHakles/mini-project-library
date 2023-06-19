package id.co.indivara.perpustakaan.controllers;

import id.co.indivara.perpustakaan.entities.Reader;
import id.co.indivara.perpustakaan.entities.ResponseBody;
import id.co.indivara.perpustakaan.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ReaderController {
    @Autowired
    private ReaderService readerService;

    @GetMapping("/readers")
    ResponseEntity<ResponseBody<Object>> findAllReader() {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Reader Found")
                        .data(readerService.findAllReader())
                        .build()
        );
    }

    @GetMapping("/readers/{id}")
    ResponseEntity<ResponseBody<Object>> findReaderById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Reader Found"))
                        .data(readerService.findReaderById(id))
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
