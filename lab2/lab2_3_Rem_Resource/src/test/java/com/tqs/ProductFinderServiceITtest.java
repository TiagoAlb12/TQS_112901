package com.tqs;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFinderServiceITtest {

    @Test
    public void testFindProductDetails_ValidId() {
        ISimpleHttpClient httpClient = (ISimpleHttpClient) new SimpleHttpClient();
        ProductFinderService productFinderService = new ProductFinderService(httpClient);

        Optional<Product> product = productFinderService.findProductDetails(3);

        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());
    }

    @Test
    public void testFindProductDetails_InvalidId() {
        ISimpleHttpClient httpClient = (ISimpleHttpClient) new SimpleHttpClient();
        ProductFinderService productFinderService = new ProductFinderService(httpClient);

        Optional<Product> product = productFinderService.findProductDetails(300);

        assertTrue(product.isEmpty());
    }
}
