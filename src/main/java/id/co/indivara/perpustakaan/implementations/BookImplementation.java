package id.co.indivara.perpustakaan.implementations;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.exceptions.DataRelatedException;
import id.co.indivara.perpustakaan.repositories.BookRepository;
import id.co.indivara.perpustakaan.services.BookService;
import id.co.indivara.perpustakaan.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class BookImplementation implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public ArrayList<Book> findAllBook() {
        ArrayList<Book> books = new ArrayList<>((Collection<Book>) bookRepository.findAll());
        if(books.isEmpty()) {
            throw new DataRelatedException("No Book Found");
        }
        return books;
    }

    @Override
    public Book findBookById(Integer id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Book Found")
        );
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        if(book == null) {
            throw new DataRelatedException("Must have a book inputted");
        }
        //object dibuat dulu karena semua field di Book dibuat berdasarkan 6 data ini
        Book createdBook = new Book(
                book.getBookTitle(),
                book.getBookAuthor(),
                book.getBookPublisher(),
                book.getBookDescription(),
                book.getBookPages(),
                book.getBookCopy()
        );
        Utility.copyNonNullField(book, createdBook);
        return bookRepository.save(createdBook);
    }

    @Transactional
    @Override
    public Book updateBook(Integer id, Book updateBook) {
        if(updateBook == null) {
            throw new DataRelatedException("Must have a book inputted");
        }
        Book oldBook = findBookById(id);
        Utility.copyNonNullField(updateBook, oldBook);
        return bookRepository.save(oldBook);
    }

    @Transactional
    @Override
    public void deleteBook(Integer id) {
        findBookById(id);
        bookRepository.deleteById(id);
    }
}
