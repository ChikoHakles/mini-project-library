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
        ArrayList<Return> returns = new ArrayList<>((Collection<Return>) returnRepository.findAll());
         if (returns.isEmpty()) {
             throw new DataRelatedException("No Return Found");
         }
         return returns;
    }

    @Override
    public Return findReturnById(UUID id) {
        return returnRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Return Found")
        );
    }

    @Override
    public Return findReturnByCode(String code) {
       Return ret = returnRepository.findByReturnCode(code);
       if(ret == null) {
           throw new DataRelatedException("No Return Found");
       }
       return ret;
    }

    @Override
    public Return findReturnByBorrow(Borrow borrow) {
        return returnRepository.findByBorrow(borrow);
    }

    @Transactional
    @Override
    public Return saveReturn(Borrow borrow) {
        if(findReturnByBorrow(borrow) != null) {
            throw new BookTransactionException("This Borrow has returned!");
        }
        Book book = borrow.getBook();
        book.setBookReady(book.getBookReady() + 1);
        book.setBookUnreturned(book.getBookUnreturned() - 1);
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
