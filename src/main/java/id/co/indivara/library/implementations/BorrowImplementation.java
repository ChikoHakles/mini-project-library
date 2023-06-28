package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.*;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.BorrowRepository;
import id.co.indivara.library.repositories.ReturnRepository;
import id.co.indivara.library.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class BorrowImplementation implements BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private ReturnRepository returnRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private WishlistService wishlistService;

    @Override
    public ArrayList<Borrow> findAllBorrow() {
        //membuat array list yang berisi semua return
        ArrayList<Borrow> borrows = new ArrayList<>((Collection<Borrow>) borrowRepository.findAll());
        //jika array list tersebut kosong, throw DataRelatedException
        if(borrows.isEmpty()) {
            throw new DataRelatedException("No Borrow Found");
        }
        //output array list tersebut
        return borrows;
    }

    @Override
    public ArrayList<Borrow> findAllBorrowByReader(Reader reader) {
        //cari borrow berdasarkan pembaca nya (dibuat di BorrowRepository)
        return new ArrayList<>(borrowRepository.findAllByReader(readerService.findReaderById(reader.getReaderId())));
    }

    @Override
    public ArrayList<Borrow> findAllBorrowByBook(Book book) {
        //cari borrow berdasarkan buku nya (dibuat di BorrowRepository)
        return new ArrayList<>(borrowRepository.findAllByBook(bookService.findBookById(book.getBookId())));
    }

    @Override
    public Borrow findBorrowByCode(String code) {
        //cari borrow berdasarkan code nya
        Borrow borrow = borrowRepository.findByBorrowCode(code);
        //jika tidak ada, throw DatarelatedException
        if (borrow == null) {
            throw new DataRelatedException("No Borrow Found");
        }
        //output borrow tersebut
        return borrow;
    }

    @Override
    public Borrow findBorrowById(UUID id) {
        //cari borrow berdasarkan id nya, kalau tidak ada akan throw DataRelatedException
        return borrowRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Borrow Found")
        );
    }

    @Override
    public ArrayList<Borrow> findUnreturnedBorrow() {
        //buat array list yang menampung semua borrow
        ArrayList<Borrow> pool = findAllBorrow();
        //buat array list kosong untuk menampung object borrow yang sudah ada return nya
        ArrayList<Borrow> returneds = new ArrayList<>();
        returnRepository.findAll().forEach(ret -> returneds.add(ret.getBorrow()));
        //remove borrow yang sudah ada return nya
        pool.removeIf(returneds::contains);
        //output sisa nya (berisi yang belum ada return nya)
        return pool;
    }

    @Override
    public ArrayList<Borrow> findUnreturnedBorrowByReader(Reader reader) {
        //buat array list yang menampung semua borrow berdasarkan pembaca
        ArrayList<Borrow> pool = findAllBorrowByReader(reader);
        //buat array list kosong untuk menampung object borrow yang belum ada return nya
        ArrayList<Borrow> unreturneds = new ArrayList<>();
        //untuk setiap anggota array list "pool", cari return nya, apabila tidak ada return terkait, masukan ke array list "unreturneds"
        for (Borrow b: pool) {
            Return ret = returnRepository.findByBorrow(b);
            if(ret == null) {
                unreturneds.add(b);
            }
        }
        //output array list unreturneds
        return unreturneds;
    }

    @Override
    public ArrayList<Borrow> findUnreturnedBorrowByBook(Book book) {
        //buat array list yang menampung semua borrow berdasarkan buku
        ArrayList<Borrow> pool = findAllBorrowByBook(book);
        //buat array list kosong untuk menampung object borrow yang belum ada return nya
        ArrayList<Borrow> unreturneds = new ArrayList<>();
        //untuk setiap anggota array list "pool", cari return nya, apabila tidak ada return terkait, masukan ke array list "unreturneds"
        for (Borrow b: pool) {
            Return ret = returnRepository.findByBorrow(b);
            if(ret == null) {
                unreturneds.add(b);
            }
        }
        //output array list unreturneds
        return unreturneds;
    }

    @Transactional
    @Override
    public Borrow saveBorrow(BorrowDTO borrowDTO) {
        //jika input nya tidak ada, throw DataRelatedException
        if (borrowDTO == null) {
            throw new DataRelatedException("Must have a borrow inputted");
        }
        //cari pembaca dan buku, akan throw DataRelatedException jika tidak ada
        Book book = bookService.findBookById(borrowDTO.getBookId());
        Reader reader = readerService.findReaderById(borrowDTO.getReaderId());
        //jika tidak ada buku yang ready, masukkan ke dalam wishlist
        if (book.getBookReady() == 0) {
            wishlistService.saveWishlist(new WishlistDTO(borrowDTO.getBookId(), borrowDTO.getReaderId()));
            return null;
        }
        //jika ada wishlist terkait dengan buku & pembaca tersebut, hapus dari daftar wishlist
        Wishlist wishlist = wishlistService.findWishlistByBookAndReader(book, reader);
        if(wishlist != null) {
            wishlistService.delete(wishlist);
        }
        //set buku ready - 1 (karena dipinjam)
        book.setBookReady(book.getBookReady() - 1);
        //set buku unreturned + 1 (karena dipinjam)
        book.setBookUnreturned(book.getBookUnreturned() + 1);
        //set jumlah buku dibaca + 1 (karena dipinjam)
        book.setBookNumberOfReading(book.getBookNumberOfReading() + 1);
        //buat object borrow baru
        Borrow createdBorrow = new Borrow(book, reader);
        //simpan ke repository (dan database)
        return borrowRepository.save(createdBorrow);
    }

    @Transactional
    @Override
    public void deleteBorrow(UUID id) {
        borrowRepository.delete(findBorrowById(id));
    }

    @Transactional
    @Override
    public void deleteBorrowByCode(String code) {
        borrowRepository.delete(findBorrowByCode(code));
    }
}
