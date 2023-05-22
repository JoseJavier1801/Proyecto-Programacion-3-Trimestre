package org.example.DOMAIN;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {

    private Products product;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin(1, "John Doe","1234","12345678B","admin@example.com");
        product = new Products(1, "Product 1", "Description 1", 10, 9.99, admin);
    }

    @Test
    public void testGetId() {
        assertEquals(1, product.getId());
    }

    @Test
    public void testSetId() {
        product.setId(2);
        assertEquals(2, product.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Product 1", product.getName());
    }

    @Test
    public void testSetName() {
        product.setName("Updated Product");
        assertEquals("Updated Product", product.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Description 1", product.getDescription());
    }

    @Test
    public void testSetDescription() {
        product.setDescription("Updated Description");
        assertEquals("Updated Description", product.getDescription());
    }

    @Test
    public void testGetStock() {
        assertEquals(10, product.getStock());
    }

    @Test
    public void testSetStock() {
        product.setStock(20);
        assertEquals(20, product.getStock());
    }

    @Test
    public void testGetPrice() {
        assertEquals(9.99, product.getPrice(), 0.01);
    }

    @Test
    public void testSetPrice() {
        product.setPrice(19.99);
        assertEquals(19.99, product.getPrice(), 0.01);
    }

    @Test
    public void testGetId_admin() {
        assertEquals(admin, product.getId_admin());
    }

    @Test
    public void testSetId_admin() {
        Admin newAdmin = new Admin(2, "Jane Smith","jane2023", "12345678G","admin2@example.com");
        product.setId_admin(newAdmin);
        assertEquals(newAdmin, product.getId_admin());
    }

    @Test
    public void testEquals() {
        Products sameProduct = new Products(1, "Product 1", "Description 1", 10, 9.99, admin);
        Products differentProduct = new Products(2, "Product 2", "Description 2", 20, 19.99, admin);

        assertEquals(product, sameProduct);
        assertNotEquals(product, differentProduct);
    }

}