package id.co.indivara.library.services;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.History;
import id.co.indivara.library.entities.Reader;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface HistoryService {
    ArrayList<History> findAllHistory();
    ArrayList<History> findHistoryByBook(Book book);
    ArrayList<History> findHistoryByReader(Reader reader);
    ArrayList<History> findHistoryByBookAndReader(Book book, Reader reader);
}
