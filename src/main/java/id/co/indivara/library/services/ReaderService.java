package id.co.indivara.library.services;

import id.co.indivara.library.entities.Reader;

import java.util.ArrayList;

public interface ReaderService {
    ArrayList<Reader> findAllReader();
    Reader findReaderById(Integer id);
    Reader saveReader(Reader reader);
    Reader updateReader(Integer id, Reader readerUpdate);
    void deleteReader(Integer id);
}
