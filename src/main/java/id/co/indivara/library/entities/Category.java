package id.co.indivara.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "MST_categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(name = "category_id")
    @NotBlank(message = "Category Code must been filled")
    private String categoryId;

    @Column(name = "category_name")
    @NotBlank(message = "Category Name must been filled")
    private String categoryName;

//    @OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL)
//    private List<Book> books;
}
