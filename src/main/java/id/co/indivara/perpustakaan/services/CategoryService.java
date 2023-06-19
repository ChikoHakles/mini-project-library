package id.co.indivara.perpustakaan.services;

import id.co.indivara.perpustakaan.entities.Category;

import java.util.ArrayList;

public interface CategoryService {
    ArrayList<Category> findAllCategory();
    Category findCategoryById(String id);
    Category saveCategory(Category category);
    Category updateCategory(String id, Category categoryUpdate);
    void deleteCategory(String id);
}
