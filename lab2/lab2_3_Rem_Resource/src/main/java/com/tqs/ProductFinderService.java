package com.tqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;

public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products/";
    private ISimpleHttpClient httpClient;
    private ObjectMapper objectMapper;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public Optional<Product> findProductDetails(int id) {
        String jsonResponse = httpClient.doHttpGet(API_PRODUCTS + id);

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return Optional.empty();
        }

        try {
            Product product = objectMapper.readValue(jsonResponse, Product.class);
            return Optional.of(product);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
