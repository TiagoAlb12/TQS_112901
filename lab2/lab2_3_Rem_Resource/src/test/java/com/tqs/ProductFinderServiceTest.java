package com.tqs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductFinderServiceTest {

    @Mock
    private ISimpleHttpClient httpClient;

    @InjectMocks
    private ProductFinderService productFinderService;

    @BeforeEach
    void setup() {
        productFinderService = new ProductFinderService(httpClient);
    }

    @Test
    void testFindProductDetails_ValidId() {
        String jsonResponse = """
        {
            "id": 3,
            "title": "Mens Cotton Jacket",
            "price": 55.99,
            "description": "Great winter jacket.",
            "image": "https://example.com/jacket.png",
            "category": "clothing"
        }
        """;

        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);

        Optional<Product> product = productFinderService.findProductDetails(3);

        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());
        assertEquals(55.99, product.get().getPrice());
        assertEquals("Great winter jacket.", product.get().getDescription());
        assertEquals("https://example.com/jacket.png", product.get().getImage());
        assertEquals("clothing", product.get().getCategory());

        verify(httpClient, times(1)).doHttpGet("https://fakestoreapi.com/products/3");
    }

    @Test
    void testFindProductDetails_InvalidId() {
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn(null);

        Optional<Product> product = productFinderService.findProductDetails(300);

        assertTrue(product.isEmpty());

        verify(httpClient, times(1)).doHttpGet("https://fakestoreapi.com/products/300");
    }
}

