package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.History;
import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.Return;
import id.co.indivara.library.services.BorrowService;
import id.co.indivara.library.services.HistoryService;
import id.co.indivara.library.services.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HistoryImplementation implements HistoryService {
    @Autowired
    private ReturnService returnService;

    @Autowired
    private BorrowService borrowService;
    @Override
    public ArrayList<History> findAllHistory() {
        ArrayList<History> histories = new ArrayList<>();
        for (Return r : returnService.findAllReturn()) {
            histories.add(
                    History.builder()
                            .bookId(r.getBorrow().getBook().getBookId())
                            .bookTitle(r.getBorrow().getBook().getBookTitle())
                            .readerId(r.getBorrow().getReader().getReaderId())
                            .readerName(r.getBorrow().getReader().getReaderName())
                            .borrowCode(r.getBorrow().getBorrowCode())
                            .borrowDate(r.getBorrow().getBorrowDate())
                            .returnCode(r.getReturnCode())
                            .returnDate(r.getReturnDate())
                            .build()
            );
        }
        return histories;
    }

    @Override
    public ArrayList<History> findHistoryByBook(Book book) {
        ArrayList<History> histories = new ArrayList<>();
        for (Return r : returnService.findAllReturn()) {
            if(r.getBorrow().getBook() == book) {
                histories.add(
                        History.builder()
                                .bookId(r.getBorrow().getBook().getBookId())
                                .bookTitle(r.getBorrow().getBook().getBookTitle())
                                .readerId(r.getBorrow().getReader().getReaderId())
                                .readerName(r.getBorrow().getReader().getReaderName())
                                .borrowCode(r.getBorrow().getBorrowCode())
                                .borrowDate(r.getBorrow().getBorrowDate())
                                .returnCode(r.getReturnCode())
                                .returnDate(r.getReturnDate())
                                .build()
                );
            }
        }
        return histories;
    }

    @Override
    public ArrayList<History> findHistoryByReader(Reader reader) {
        ArrayList<History> histories = new ArrayList<>();
        for (Return r : returnService.findAllReturn()) {
            if(r.getBorrow().getReader() == reader) {
                histories.add(
                        History.builder()
                                .bookId(r.getBorrow().getBook().getBookId())
                                .bookTitle(r.getBorrow().getBook().getBookTitle())
                                .readerId(r.getBorrow().getReader().getReaderId())
                                .readerName(r.getBorrow().getReader().getReaderName())
                                .borrowCode(r.getBorrow().getBorrowCode())
                                .borrowDate(r.getBorrow().getBorrowDate())
                                .returnCode(r.getReturnCode())
                                .returnDate(r.getReturnDate())
                                .build()
                );
            }
        }
        return histories;
    }

    @Override
    public ArrayList<History> findHistoryByBookAndReader(Book book, Reader reader) {
        ArrayList<History> histories = new ArrayList<>();
        for (Return r : returnService.findAllReturn()) {
            if((r.getBorrow().getBook() == book) && (r.getBorrow().getReader() == reader)) {
                histories.add(
                        History.builder()
                                .bookId(r.getBorrow().getBook().getBookId())
                                .bookTitle(r.getBorrow().getBook().getBookTitle())
                                .readerId(r.getBorrow().getReader().getReaderId())
                                .readerName(r.getBorrow().getReader().getReaderName())
                                .borrowCode(r.getBorrow().getBorrowCode())
                                .borrowDate(r.getBorrow().getBorrowDate())
                                .returnCode(r.getReturnCode())
                                .returnDate(r.getReturnDate())
                                .build()
                );
            }
        }
        return histories;
    }
}
