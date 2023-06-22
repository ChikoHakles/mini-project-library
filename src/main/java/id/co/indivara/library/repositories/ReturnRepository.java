package id.co.indivara.library.repositories;

import id.co.indivara.library.entities.Borrow;
import id.co.indivara.library.entities.Return;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReturnRepository extends CrudRepository<Return, UUID> {
    Return findByBorrow(Borrow borrow);
    Return findByReturnCode(String code);
}
