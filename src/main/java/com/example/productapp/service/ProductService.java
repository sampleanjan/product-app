package com.example.productapp.service;
 
import com.example.productapp.model.Product;
 
import java.util.List;
 
public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(Product product, Long id);

    boolean deleteProduct(Long id);

}