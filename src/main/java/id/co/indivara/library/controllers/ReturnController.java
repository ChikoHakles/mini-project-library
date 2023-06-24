package id.co.indivara.library.controllers;

import id.co.indivara.library.entities.Borrow;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.services.BorrowService;
import id.co.indivara.library.services.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReturnController {
    @Autowired
    private ReturnService returnService;

    @Autowired
    private BorrowService borrowService;

    @GetMapping("/returns")
    ResponseEntity<ResponseBody<Object>> findAllReturn() {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Return Found")
                        .data(returnService.findAllReturn())
                        .build()
        );
    }

    @GetMapping("/returns/{code}")
    ResponseEntity<ResponseBody<Object>> findAllReturnByCode(@PathVariable(name = "code") String code) {
        Object data = null;
        if(code.startsWith("B")) {
            data = returnService.findReturnByBorrow(borrowService.findBorrowByCode(code));
        }
        else if (code.startsWith("R")) {
            data = returnService.findReturnByCode(code);
        }

        if(data == null) {
            throw new DataRelatedException("No Return Found");
        }

        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Return Found")
                        .data(data)
                        .build()
        );
    }

    @PostMapping("/returns")
    ResponseEntity<ResponseBody<Object>> saveReturn(@RequestParam(name = "borrowCode") String borrowCode) {
        Borrow borrow = borrowService.findBorrowByCode(borrowCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Return Created")
                        .data(returnService.saveReturn(borrow))
                        .build()
        );
    }
}
