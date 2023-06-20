package id.co.indivara.perpustakaan.implementations;

import id.co.indivara.perpustakaan.entities.Reader;
import id.co.indivara.perpustakaan.exceptions.DataRelatedException;
import id.co.indivara.perpustakaan.repositories.ReaderRepository;
import id.co.indivara.perpustakaan.services.ReaderService;
import id.co.indivara.perpustakaan.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReaderImplementation implements ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public ArrayList<Reader> findAllReader() {
        ArrayList<Reader> readers = new ArrayList<>((Collection<Reader>) readerRepository.findAll());
        if (readers.isEmpty()) {
            throw new DataRelatedException("No Reader Found");
        }
        return readers;
    }

    @Override
    public Reader findReaderById(Integer id) {
        return readerRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Reader Found")
        );
    }

    @Transactional
    @Override
    public Reader saveReader(Reader reader) {
        if(reader == null) {
            throw new DataRelatedException("Must have a reader inputted");
        }
        Reader createdReader = new Reader();
        Utility.copyNonNullField(reader, createdReader);
        return readerRepository.save(createdReader);
    }

    @Transactional
    @Override
    public Reader updateReader(Integer id, Reader readerUpdate) {
        if(readerUpdate == null) {
            throw new DataRelatedException("Must have a reader inputted");
        }
        Reader oldReader = findReaderById(id);
        Utility.copyNonNullField(readerUpdate, oldReader);
        return readerRepository.save(oldReader);
    }

    @Transactional
    @Override
    public void deleteReader(Integer id) {
        findReaderById(id);
        readerRepository.deleteById(id);
    }
}
