package id.co.indivara.perpustakaan.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String categoryCode;

    @Column(name = "category_name")
    @NotBlank(message = "Category Name must been filled")
    private String categoryName;
}
