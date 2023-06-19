package id.co.indivara.perpustakaan.services;

import id.co.indivara.perpustakaan.entities.Reader;

import java.util.ArrayList;

public interface ReaderService {
    ArrayList<Reader> findAllReader();
    Reader findReaderById(Integer id);
    Reader saveReader(Reader reader);
    Reader updateReader(Integer id, Reader readerUpdate);
    void deleteReader(Integer id);
}
