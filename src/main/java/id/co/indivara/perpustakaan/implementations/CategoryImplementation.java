package id.co.indivara.perpustakaan.implementations;

import id.co.indivara.perpustakaan.entities.Category;
import id.co.indivara.perpustakaan.exceptions.DataRelatedException;
import id.co.indivara.perpustakaan.repositories.CategoryRepository;
import id.co.indivara.perpustakaan.services.CategoryService;
import id.co.indivara.perpustakaan.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

public class CategoryImplementation implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ArrayList<Category> findAllCategory() {
        ArrayList<Category> categories = new ArrayList<>((Collection<Category>) categoryRepository.findAll());
        if (categories.isEmpty()) {
            throw new DataRelatedException("No Data");
        }
        return categories;
    }

    @Override
    public Category findCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Data")
        );
    }

    @Transactional
    @Override
    public Category saveCategory(Category category) {
        if(category == null) {
            throw new DataRelatedException("Must have a category inputted");
        }
        Category createdCategory = new Category();
        Utility.copyNonNullField(category, createdCategory);
        return categoryRepository.save(createdCategory);
    }

    @Transactional
    @Override
    public Category updateCategory(String id, Category updateCategory) {
        if(updateCategory == null) {
            throw new DataRelatedException("Must have a category inputted");
        }
        Category oldCategory = findCategoryById(id);
        Utility.copyNonNullField(updateCategory, oldCategory);
        return categoryRepository.save(oldCategory);
    }

    @Transactional
    @Override
    public void deleteCategory(String id) {
        findCategoryById(id);
        categoryRepository.deleteById(id);
    }
}
