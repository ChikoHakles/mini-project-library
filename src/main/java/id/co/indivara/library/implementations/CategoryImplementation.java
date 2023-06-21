package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.Category;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.CategoryRepository;
import id.co.indivara.library.services.CategoryService;
import id.co.indivara.library.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CategoryImplementation implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ArrayList<Category> findAllCategory() {
        ArrayList<Category> categories = new ArrayList<>((Collection<Category>) categoryRepository.findAll());
        if (categories.isEmpty()) {
            throw new DataRelatedException("No Category Found");
        }
        return categories;
    }

    @Override
    public Category findCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Category Found")
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
    public Category updateCategory(String id, Category categoryUpdate) {
        if(categoryUpdate == null) {
            throw new DataRelatedException("Must have a category inputted");
        }
        Category oldCategory = findCategoryById(id);
        Utility.copyNonNullField(categoryUpdate, oldCategory);
        return categoryRepository.save(oldCategory);
    }

    @Transactional
    @Override
    public void deleteCategory(String id) {
        findCategoryById(id);
        categoryRepository.deleteById(id);
    }
}
