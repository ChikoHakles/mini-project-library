package id.co.indivara.perpustakaan.repositories;

import id.co.indivara.perpustakaan.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
}
