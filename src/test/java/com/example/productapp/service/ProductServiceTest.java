package com.example.productapp.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
 
import com.example.productapp.model.Product;
import com.example.productapp.repository.ProductRepository;
 
@SpringBootTest
public class ProductServiceTest {
 
	@Mock
	private ProductRepository productRepo;
 
	@InjectMocks
	private ProductServiceImpl productService;
 
	// test case 1 - save product - productService.saveProduct
	@Test
	public void testCreateProduct() {
		// Arrange
		Product product = new Product();
		product.setName("Laptops");
		product.setDescription("Sony");
		product.setPrice(600.00);
 
		// Action - mocking the service call
		Mockito.when(productRepo.save(Mockito.any(Product.class))).thenReturn(product);
 
		// Making the real call ProductServiceImpl class - action
		Product savedProduct = productService.saveProduct(product);
 
		// Assert
		assertNotNull(savedProduct);
		assertEquals("Laptops", savedProduct.getName());
	}
 
	// test case 2 - find all products - productService.getAllProducts
	@Test
	public void testGetAllProducts() {
		// Arrange
		when(productRepo.findAll())
				.thenReturn(Arrays.asList(new Product("Pens", "Parker", 25), new Product("Pencils", "Natraj", 450)));
 
		// Act
		List<Product> products = productService.getAllProducts();
 
		// Assert
		verify(productRepo, times(1)).findAll();
		assertEquals(products.size(), 2);
	}
 
	// test case 3 - getProductById
	@Test
	public void testGetProductById() {
		// Arrange
		Product product = new Product();
		product.setName("Laptops");
		product.setDescription("Sony");
		product.setPrice(600.00);
 
		// mocking the findById - mock call
		Mockito.when(productRepo.findById(1L)).thenReturn(Optional.of(product));
 
		// Action - real service call
		Optional<Product> foundProduct = Optional.ofNullable(productService.getProductById(1L));
 
		// Assert stmts
		assertTrue(foundProduct.isPresent());
		assertEquals("Laptops", foundProduct.get().getName());
	}
 
	// test case 4 - update product - productService.updateProduct
	@Test
    public void testUpdateProduct_Success() {
		//Assign
		Product product = new Product();
		product.setName("Laptops");
		product.setDescription("Sony");
		product.setPrice(600.00);
		
        // Mock the behavior of the product repository
        Mockito.when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepo.save(product)).thenReturn(product);
 
		Product newProduct = new Product();
		newProduct.setName("Laptops handheld");
		newProduct.setDescription("Sony");
		newProduct.setPrice(800.00);
 
        // Call the method under test
        Product updatedProduct = productService.updateProduct( newProduct,1L);
 
        // Verify the repository methods were called
        Mockito.verify(productRepo).findById(1L);
        Mockito.verify(productRepo).save(product);
 
        // Assert that the updated product has the new details
        assertEquals("Laptops handheld",updatedProduct.getName());
       //ssertEquals(Double.valueOf(0.), updatedProduct.getPrice());
    }
 
	//test case 5- deleteProduct
	@Test
	public void testDeleteProduct() {
		// Arrange
        Long productId = 1L;
        Product product = new Product("Demo","uct 1", 100);  // Creating a mock product
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));  // Mock the repository behavior
 
        // Act
        boolean result = productService.deleteProduct(productId);  // Call the service method
 
        // Assert
        assertTrue(result);  // Assert that the result is true, meaning the product was deleted
        verify(productRepo, times(1)).delete(product);  // Verify the delete method was called once
            
    }
 
}
 