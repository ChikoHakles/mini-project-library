package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.ReaderRepository;
import id.co.indivara.library.repositories.WishlistRepository;
import id.co.indivara.library.services.ReaderService;
import id.co.indivara.library.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReaderImplementation implements ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public ArrayList<Reader> findAllReader() {
        //buat array list yang menampung semua pembaca
        ArrayList<Reader> readers = new ArrayList<>((Collection<Reader>) readerRepository.findAll());
        //jika array list kosong, throw DataRelatedException
        if (readers.isEmpty()) {
            throw new DataRelatedException("No Reader Found");
        }
        //output array list tersebut
        return readers;
    }

    @Override
    public Reader findReaderById(Integer id) {
        //mencari pembaca berdasar id, jika ada maka output pembaca dengan id tersebut, jika tidak ada akan throw DataRelatedException
        return readerRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Reader Found")
        );
    }

    @Transactional
    @Override
    public Reader saveReader(Reader reader) {
        //jika input tidak ada, throw DataRelatedException
        if(reader == null) {
            throw new DataRelatedException("Must have a reader inputted");
        }
        //buat object pembaca baru
        Reader createdReader = new Reader();
        //copy data dari input ke object yang baru dibuat
        Utility.copyNonNullField(reader, createdReader);
        //simpan ke repository (dan database) dan output data pembaca tersebut
        return readerRepository.save(createdReader);
    }

    @Transactional
    @Override
    public Reader updateReader(Integer id, Reader readerUpdate) {
        //jika input tidak ada, throw DataRelatedException
        if(readerUpdate == null) {
            throw new DataRelatedException("Must have a reader inputted");
        }
        //cari data pembaca, jika ada dimasukan ke variabel, jika tidak ada akan throw DataRelatedException
        Reader oldReader = findReaderById(id);
        //copy data dari input ke pembaca yang telah ada tadi
        Utility.copyNonNullField(readerUpdate, oldReader);
        //simpan perubahan
        return readerRepository.save(oldReader);
    }

    @Transactional
    @Override
    public void deleteReader(Integer id) {
        //cari pembaca, setelah itu cari semua wishlist yang dimiliki pembaca, dan hapus wishlist-wishlist tersebut.
        //jika pembaca tidak ada, maka throw DataRelatedException
        wishlistRepository.deleteAll(wishlistRepository.findAllByReader(findReaderById(id)));
        //hapus pembaca
        readerRepository.deleteById(id);
    }
}
