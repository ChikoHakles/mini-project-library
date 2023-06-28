package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.History;
import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.Return;
import id.co.indivara.library.exceptions.DataRelatedException;
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

    @Override
    public ArrayList<History> findAllHistory() {
        //buat array list kosong yang akan menampung history
        ArrayList<History> histories = new ArrayList<>();
        //untuk semua transaksi (borrow yang ada return nya)
        for (Return r : returnService.findAllReturn()) {
            //buat object history berdasarkan buku, pembaca, borrow, dan return nya
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
        //jika history tidak ada, akan return DataRelatedException
        if (histories.isEmpty()) {
            throw new DataRelatedException("No History Found");
        }
        //output array list berisi history
        return histories;
    }

    @Override
    public ArrayList<History> findHistoryByBook(Book book) {
        //buat array list kosong yang akan menampung history
        ArrayList<History> histories = new ArrayList<>();
        //untuk semua transaksi (borrow yang ada return nya)
        for (Return r : returnService.findAllReturn()) {
            //jika buku yang terkait sama dengan buku yang di input
            if(r.getBorrow().getBook() == book) {
                //buat object history berdasarkan buku, pembaca, borrow, dan return nya
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
        //jika history tidak ada, akan return DataRelatedException
        if (histories.isEmpty()) {
            throw new DataRelatedException("No History Found");
        }
        //output array list berisi history berdasarkan buku
        return histories;
    }

    @Override
    public ArrayList<History> findHistoryByReader(Reader reader) {
        //buat array list kosong yang akan menampung history
        ArrayList<History> histories = new ArrayList<>();
        //untuk semua transaksi (borrow yang ada return nya)
        for (Return r : returnService.findAllReturn()) {
            //jika pembaca yang terkait sama dengan pembaca yang di input
            if(r.getBorrow().getReader() == reader) {
                //buat object history berdasarkan buku, pembaca, borrow, dan return nya
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
        //jika history tidak ada, akan return DataRelatedException
        if (histories.isEmpty()) {
            throw new DataRelatedException("No History Found");
        }
        //output array list berisi history berdasarkan buku
        return histories;
    }

    @Override
    public ArrayList<History> findHistoryByBookAndReader(Book book, Reader reader) {
        //buat array list kosong yang akan menampung history
        ArrayList<History> histories = new ArrayList<>();
        //untuk semua transaksi (borrow yang ada return nya)
        for (Return r : returnService.findAllReturn()) {
            //jika pembaca dan buku yang terkait sama dengan pembaca dan buku yang di input
            if((r.getBorrow().getBook() == book) && (r.getBorrow().getReader() == reader)) {
                //buat object history berdasarkan buku, pembaca, borrow, dan return nya
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
        //jika history tidak ada, akan return DataRelatedException
        if (histories.isEmpty()) {
            throw new DataRelatedException("No History Found");
        }
        //output array list berisi history berdasarkan buku
        return histories;
    }
}
