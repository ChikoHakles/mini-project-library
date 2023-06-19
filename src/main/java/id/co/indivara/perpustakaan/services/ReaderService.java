package id.co.indivara.perpustakaan.services;

import id.co.indivara.perpustakaan.entities.Reader;

import java.util.ArrayList;

public interface ReaderService {
    ArrayList<Reader> findAllReader();
    Reader findReaderById(Integer id);
    Reader saveReader(Reader book);
    Reader updateReader(Integer id, Reader bookUpdate);
    void deleteReader(Integer id);
}
