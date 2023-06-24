package id.co.indivara.library.controllers;

import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/history")
    ResponseEntity<ResponseBody<Object>> findAllHistory(){
        Object history = historyService.findAllHistory();
        if(history == null) {
            throw new DataRelatedException("No History Found");
        }
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("History Found")
                        .data(history)
                        .build()
        );
    }
}
