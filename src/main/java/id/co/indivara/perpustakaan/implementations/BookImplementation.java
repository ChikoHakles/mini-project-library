package id.co.indivara.perpustakaan.implementations;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.repositories.BookRepository;
import id.co.indivara.perpustakaan.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class BookImplementation implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public ArrayList<Book> findAllBook() {
        ArrayList<Book> books = new ArrayList<>((Collection<Book>) bookRepository.findAll());
        return books.isEmpty() ? null : books;
    }

    @Override
    public Book findBookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Integer id, Book updateBook) {
        if(!bookRepository.findById(id).isPresent()) {
            return null;
        }
        Book oldBook = bookRepository.findById(id).get();
        if(updateBook.getBookTitle().isEmpty()) {
            oldBook.setBookTitle(updateBook.getBookTitle());
        }
        return null;
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}
