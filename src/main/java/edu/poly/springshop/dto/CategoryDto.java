package edu.poly.springshop.dto;

import edu.poly.springshop.domain.CategoryStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * DTO for {@link edu.poly.springshop.domain.Category}
 */
@Data
public class CategoryDto implements Serializable {
    private Long id;
    @NotEmpty(message = "Category name required ")  //không trống và trả thông báo lõi
    private String name;
    private CategoryStatus status;
}