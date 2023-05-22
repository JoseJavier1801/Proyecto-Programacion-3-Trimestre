package org.example.DOMAIN;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin(1, "adminUser", "adminPassword", "123456789", "admin@example.com");
    }

    @Test
    public void testGetId() {
        assertEquals(1, admin.getId());
    }

    @Test
    public void testSetId() {
        admin.setId(2);
        assertEquals(2, admin.getId());
    }

    @Test
    public void testGetUsername() {
        assertEquals("adminUser", admin.getUsername());
    }

    @Test
    public void testSetUsername() {
        admin.setUsername("newAdminUser");
        assertEquals("newAdminUser", admin.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("adminPassword", admin.getPassword());
    }

    @Test
    public void testSetPassword() {
        admin.setPassword("newAdminPassword");
        assertEquals("newAdminPassword", admin.getPassword());
    }

    @Test
    public void testGetDNI() {
        assertEquals("123456789", admin.getDNI());
    }

    @Test
    public void testSetDNI() {
        admin.setDNI("987654321");
        assertEquals("987654321", admin.getDNI());
    }

    @Test
    public void testGetEmail() {
        assertEquals("admin@example.com", admin.getEmail());
    }

    @Test
    public void testSetEmail() {
        admin.setEmail("newadmin@example.com");
        assertEquals("newadmin@example.com", admin.getEmail());
    }

    @Test
    public void testEquals() {
        Admin sameAdmin = new Admin(1, "adminUser", "adminPassword", "123456789", "admin@example.com");
        Admin differentAdmin = new Admin(2, "adminUser", "adminPassword", "123456789", "admin@example.com");

        assertEquals(admin, sameAdmin);
        assertNotEquals(admin, differentAdmin);
    }


}