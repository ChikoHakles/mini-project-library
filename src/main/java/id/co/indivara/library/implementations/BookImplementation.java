package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.BookRepository;
import id.co.indivara.library.services.BookService;
import id.co.indivara.library.utils.Utility;
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
        //buat array list yang menampung semua buku
        ArrayList<Book> books = new ArrayList<>((Collection<Book>) bookRepository.findAll());
        //jika array list kosong, throw DataRelatedException
        if(books.isEmpty()) {
            throw new DataRelatedException("No Book Found");
        }
        //output array list tersebut
        return books;
    }

    @Override
    public Book findBookById(Integer id) {
        //mencari buku berdasar id, jika ada maka output buku dengan id tersebut, jika tidak ada akan throw DataRelatedException
        return bookRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Book Found")
        );
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        //jika input tidak ada, throw DataRelatedException
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
        //akan copy data buku lain nya (dari inputan ke object yang telah dibuat)
        Utility.copyNonNullField(book, createdBook);
        //menyimpan buku ke repository (dan database) dan output buku tersebut
        return bookRepository.save(createdBook);
    }

    @Transactional
    @Override
    public Book updateBook(Integer id, Book updateBook) {
        //jika input tidak ada, throw DataRelatedException
        if(updateBook == null) {
            throw new DataRelatedException("Must have a book inputted");
        }
        //cari data buku, jika ada dimasukan ke variabel, jika tidak ada akan throw DataRelatedException
        Book oldBook = findBookById(id);
        //copy data dari input ke buku yang telah ada tadi
        Utility.copyNonNullField(updateBook, oldBook);
        //simpan perubahan
        return bookRepository.save(oldBook);
    }

    @Transactional
    @Override
    public void deleteBook(Integer id) {
        //cari buku, jika tidak ada akan throw DataRelatedException
        findBookById(id);
        //hapus buku dari repository (dan database)
        bookRepository.deleteById(id);
    }
}
