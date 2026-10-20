package com.demov2.collab.DTO;

import com.demov2.collab.Entity.Enums.ProductCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotNull(message = "category is required")
    private ProductCategory category;
    @NotNull(message = "price is required")
    @Positive
    private Long price;
    @NotBlank(message = "manufacturor is required")
    @Size(min = 3, max = 50, message = "manufacturor must be between 3 and 50 characters")
    private String manufacturor;
    @NotNull(message = "available stock is required")
    @Min(value = 0, message = "available stock must atleast be 0")
    private Long availableStock;
}
