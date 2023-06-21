package id.co.indivara.library.controllers;

import id.co.indivara.library.entities.BorrowDTO;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping("/borrows")
    ResponseEntity<ResponseBody<Object>> findAllBorrow(@RequestParam(name = "uuid", required = false) UUID id, @RequestParam(name = "code", required = false) String code) {
        if(id != null && code != null) {
            throw new DataRelatedException("Exactly 1 parameter needed, choose either code or id");
        }
        if(id != null) {
            return ResponseEntity.ok(
                    ResponseBody.builder()
                            .status(HttpStatus.OK.value())
                            .message("Borrow Found")
                            .data(borrowService.findBorrowById(id))
                            .build()
            );
        }
        if(code != null) {
            return ResponseEntity.ok(
                    ResponseBody.builder()
                            .status(HttpStatus.OK.value())
                            .message("Borrow Found")
                            .data(borrowService.findBorrowByCode(code))
                            .build()
            );
        }
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Borrow Found")
                        .data(borrowService.findAllBorrow())
                        .build()
        );
    }

    @PostMapping("/borrows")
    ResponseEntity<ResponseBody<Object>> saveBorrow(@Nullable @RequestBody BorrowDTO borrowDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Borrow Created")
                        .data(borrowService.saveBorrow(borrowDTO))
                        .build()
        );
    }

    @DeleteMapping("/borrows")
    ResponseEntity<ResponseBody<Object>> deleteBorrow(@RequestParam(name = "uuid", required = false) UUID id, @RequestParam(name = "code", required = false) String code) {
        if((id != null && code != null) || (id == null && code == null))  {
            throw new DataRelatedException("Exactly 1 parameter needed, choose either code or id");
        }
        if(id != null) {
            borrowService.deleteBorrow(id);
            return ResponseEntity.ok(
                    ResponseBody.builder()
                            .status(HttpStatus.OK.value())
                            .message("Borrow Deleted")
                            .build()
            );
        }
        borrowService.deleteBorrowByCode(code);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Borrow Deleted")
                        .build()
        );
    }
}
