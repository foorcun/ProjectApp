package com.troyatech.ProjectApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductTests {

	
	
	@Autowired
	private ProductRepository repo;
	
	@Test
	@Rollback(false)
	public void testCreateProduct() {
		
		Product product = new Product("iPhone 10", 789);
		Product savedProduct = repo.save(product);
		
		assertNotNull(savedProduct);
	}
	
	
	@Test
	public void testFindProductByNameExist() { //nedense bu da çalışmıyor
		
		String name = "iPhone 10";
		Product product = repo.findByName(name);
		
		assertEquals(product.getName(), name);
		
	}
	
	@Test
	public void testFindProductByNameNotExist() {
		
		String name = "iPhone 11";
		Product product = repo.findByName(name);
		
		assertNull(product);
		
	}
	
	
	@Test
	@Rollback(false)
	public void testUpdateProduct() {
		
		String productName = "Kindle Reader";
		Product product = new Product(productName, 199);
		product.setId(2);
		
		repo.save(product);
		Product updatedProduct = repo.findByName(productName);
		
		assertEquals(updatedProduct.getName(), productName);

	}
	
	
	@Test
	public void testListProducts() {
		
		List<Product> products = (List<Product>) repo.findAll();
		
		assertNotEquals( products.size(),0);
		
	}
	
	@Test
	@Rollback(false)
	public void testDeleteProduct() {
		
		Integer id = 2; //id 2 de data olduğunda emin ol. yoksa hata verir zaten
		
		boolean isExistBeforeDelete = repo.findById(id).isPresent();
				
		repo.deleteById(id);
		
		boolean notExistAfterDelete =  repo.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		assertFalse(notExistAfterDelete);
		
	}
	
	
}
