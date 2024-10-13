package com.example.productapp.service;


import com.example.productapp.exception.ResourceNotFoundException;

import com.example.productapp.model.Product;

import com.example.productapp.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
 
import java.util.List;

import java.util.Optional;
 
@Service

public class ProductServiceImpl implements ProductService {
 
    @Autowired

    private ProductRepository productRepository;
 
    @Override

    public Product saveProduct(Product product) {

        return productRepository.save(product);

    }
 
    @Override

    public List<Product> getAllProducts() {

        return productRepository.findAll();

    }
 
    @Override

    public Product getProductById(Long id) {

        return productRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

    }
 
    @Override

    public Product updateProduct(Product product, Long id) {

        Product existingProduct = getProductById(id);

        existingProduct.setName(product.getName());

        existingProduct.setDescription(product.getDescription());

        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);

    }
 
    @Override

    public boolean deleteProduct(Long id) {

    	Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {

            productRepository.delete(product.get());

            return true;

        }

        return false;
 
    }

}