package com.demov2.collab.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demov2.collab.DTO.ProductDto;
import com.demov2.collab.Entity.Product;
import com.demov2.collab.Service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping ("/api/v1/product") 
@RequiredArgsConstructor 
public class ProductController {
    private final ProductService productService;

    @GetMapping 
    public List<Product> getAllProducts(@Valid ProductDto productDto){
        return productService.getAllProducts();
    }

    // GET: Get product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // POST: Create one product
    @PostMapping
    public Product createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = new Product();

        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setManufacturor(productDto.getManufacturor());
        product.setAvailableStock(productDto.getAvailableStock());

        return productService.createProduct(product);
    }

    // PUT: Update product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {

        Product product = new Product();

        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setManufacturor(productDto.getManufacturor());
        product.setAvailableStock(productDto.getAvailableStock());

        return productService.updateProduct(id, product);
    }

    // DELETE: Delete product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
