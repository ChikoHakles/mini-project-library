package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Borrow;
import id.co.indivara.library.entities.Return;
import id.co.indivara.library.exceptions.BookTransactionException;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.ReturnRepository;
import id.co.indivara.library.services.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class ReturnImplementation implements ReturnService {
    @Autowired
    private ReturnRepository returnRepository;

    @Override
    public ArrayList<Return> findAllReturn() {
        //membuat array list yang berisi semua return
        ArrayList<Return> returns = new ArrayList<>((Collection<Return>) returnRepository.findAll());
        //jika array list tersebut kosong, throw DataRelatedException
         if (returns.isEmpty()) {
             throw new DataRelatedException("No Return Found");
         }
        //output array list tersebut
         return returns;
    }

    @Override
    public Return findReturnById(UUID id) {
        //cari return berdasar id return, jika tidak ada akan throw DataRelatedException
        return returnRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Return Found")
        );
    }

    @Override
    public Return findReturnByCode(String code) {
        //cari return berdasar code return
       Return ret = returnRepository.findByReturnCode(code);
       //jika tidak ada akan throw DataRelatedException
       if(ret == null) {
           throw new DataRelatedException("No Return Found");
       }
       //output data return tersebut
       return ret;
    }

    @Override
    public Return findReturnByBorrow(Borrow borrow) {
        //mencari return berdasarkan object Borrow nya (dibuat di ReturnRepository)
        return returnRepository.findByBorrow(borrow);
    }

    @Transactional
    @Override
    public Return saveReturn(Borrow borrow) {
        //jika return dengan borrow yang dimaksud sudah ada, tandanya buku sudah dikembalikan dan tidak bisa return lagi
        if(findReturnByBorrow(borrow) != null) {
            throw new BookTransactionException("This Borrow has returned!");
        }
        //simpan data buku ke sebuah variabel
        Book book = borrow.getBook();
        //value bookready + 1 (karena buku telah dikembalikan)
        book.setBookReady(book.getBookReady() + 1);
        //value bookUnreturned - 1 (karena buku telah dikembalikan)
        book.setBookUnreturned(book.getBookUnreturned() - 1);
        //output berupa save return ke repository (dan database)
        return returnRepository.save(new Return(borrow));
    }

    @Transactional
    @Override
    public void deleteReturn(UUID id) {
        returnRepository.delete(findReturnById(id));
    }

    @Transactional
    @Override
    public void deleteReturnByCode(String code) {
        returnRepository.delete(findReturnByCode(code));
    }
}
