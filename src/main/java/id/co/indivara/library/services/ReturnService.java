package id.co.indivara.library.services;

import id.co.indivara.library.entities.Borrow;
import id.co.indivara.library.entities.Return;

import java.util.ArrayList;
import java.util.UUID;

public interface ReturnService {
    ArrayList<Return> findAllReturn();
    Return findReturnById(UUID id);
    Return findReturnByCode(String code);
    Return findReturnByBorrow(Borrow borrow);
    Return saveReturn(Borrow borrow);
    void deleteReturn(UUID id);
    void deleteReturnByCode(String code);
}
