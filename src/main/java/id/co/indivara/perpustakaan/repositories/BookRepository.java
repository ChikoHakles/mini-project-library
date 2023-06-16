package id.co.indivara.perpustakaan.repositories;

import id.co.indivara.perpustakaan.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}