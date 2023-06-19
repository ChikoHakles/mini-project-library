package id.co.indivara.perpustakaan.repositories;

import id.co.indivara.perpustakaan.entities.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Integer> {

}