package id.co.indivara.library.controllers;

import id.co.indivara.library.entities.Category;
import id.co.indivara.library.entities.ResponseBody;
import id.co.indivara.library.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    ResponseEntity<ResponseBody<Object>> findAllCategory() {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message("Category Found")
                        .data(categoryService.findAllCategory())
                        .build()
        );
    }

    @GetMapping("/categories/{id}")
    ResponseEntity<ResponseBody<Object>> findCategoryById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Category Found"))
                        .data(categoryService.findCategoryById(id))
                        .build()
        );
    }

    @PostMapping("/categories")
    ResponseEntity<ResponseBody<Object>> saveCategory(@Valid @Nullable @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.builder()
                        .status(HttpStatus.CREATED.value())
                        .message(("Category Created"))
                        .data(categoryService.saveCategory(category))
                        .build()
        );
    }

    @PutMapping("/categories/{id}")
    ResponseEntity<ResponseBody<Object>> updateCategory(@PathVariable(name = "id") String id, @Nullable @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseBody.builder()
                        .status(HttpStatus.ACCEPTED.value())
                        .message(("Category Updated"))
                        .data(categoryService.updateCategory(id, category))
                        .build()
        );
    }

    @DeleteMapping("/categories/{id}")
    ResponseEntity<ResponseBody<Object>> deleteCategory(@PathVariable(name = "id") String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                ResponseBody.builder()
                        .status(HttpStatus.OK.value())
                        .message(("Category Deleted"))
                        .build()
        );
    }
}
