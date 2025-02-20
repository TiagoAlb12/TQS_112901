package com.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
public class StocksPortfolioTest {

    @Mock
    private IStockmarketService stockmarketService;

    @InjectMocks
    private StocksPortfolio portofolio;

    @BeforeEach
    public void setUp() {
        portofolio = new StocksPortfolio(stockmarketService);
    }

    @Test
    public void testTotalValueWithDifferentStocks() {
        when(stockmarketService.lookUpPrice("Apple")).thenReturn(150.0);
        when(stockmarketService.lookUpPrice("Google")).thenReturn(2800.0);
        when(stockmarketService.lookUpPrice("Microsoft")).thenReturn(250.0);

        portofolio.addStock(new Stock("Apple", 2));
        portofolio.addStock(new Stock("Google", 1));
        portofolio.addStock(new Stock("Microsoft", 4));

        double expectedTotal = (2 * 150.0) + (1 * 2800.0) + (4 * 250.0); // 4100

        assertEquals(expectedTotal, portofolio.totalValue(), 0.01);

        verify(stockmarketService, times(3)).lookUpPrice(anyString());
    }

    @Test
    public void testTotalValueWithUnusedStocks() {
        when(stockmarketService.lookUpPrice("Apple")).thenReturn(150.0);
        when(stockmarketService.lookUpPrice("Google")).thenReturn(2800.0);

        when(stockmarketService.lookUpPrice("Amazon")).thenReturn(3500.0);
        when(stockmarketService.lookUpPrice("Tesla")).thenReturn(900.0);
        when(stockmarketService.lookUpPrice("Facebook")).thenReturn(450.0);

        portofolio.addStock(new Stock("Apple", 2));
        portofolio.addStock(new Stock("Google", 1));

        double expectedTotal = 3100.0;

        assertEquals(expectedTotal, portofolio.totalValue(), 0.01);

        verify(stockmarketService, times(1)).lookUpPrice("Apple");
        verify(stockmarketService, times(1)).lookUpPrice("Google");
    }


    // teste relativo à alinea d), usei Hamcrest
    @Test
    public void testTotalValueWithDifferentStocks_Hamcrest() {
        when(stockmarketService.lookUpPrice("Apple")).thenReturn(150.0);
        when(stockmarketService.lookUpPrice("Google")).thenReturn(2800.0);
        when(stockmarketService.lookUpPrice("Microsoft")).thenReturn(250.0);

        portofolio.addStock(new Stock("Apple", 2));
        portofolio.addStock(new Stock("Google", 1));
        portofolio.addStock(new Stock("Microsoft", 4));

        double expectedTotal = 4100.0;

        assertThat(portofolio.totalValue(), closeTo(expectedTotal, 0.01));

        verify(stockmarketService, times(3)).lookUpPrice(anyString());
    }


    // teste relativo à alinea e)
    @Test
    public void testMostValuableStocks() {
        when(stockmarketService.lookUpPrice("Apple")).thenReturn(150.0);
        when(stockmarketService.lookUpPrice("Google")).thenReturn(2800.0);
        when(stockmarketService.lookUpPrice("Microsoft")).thenReturn(250.0);
        when(stockmarketService.lookUpPrice("Amazon")).thenReturn(3500.0);

        portofolio.addStock(new Stock("Apple", 2));
        portofolio.addStock(new Stock("Google", 1));
        portofolio.addStock(new Stock("Microsoft", 4));
        portofolio.addStock(new Stock("Amazon", 1));

        List<Stock> topStocks = portofolio.mostValuableStocks(2);

        assertEquals(2, topStocks.size());
        assertEquals("Amazon", topStocks.get(0).getName());
        assertEquals("Google", topStocks.get(1).getName());

        verify(stockmarketService, atLeastOnce()).lookUpPrice(anyString());
    }

    // testes relativos à alinea f)
    @Test
    public void testMostValuableStocksWithEdgeCases() {
        when(stockmarketService.lookUpPrice("Apple")).thenReturn(150.0);
        when(stockmarketService.lookUpPrice("Google")).thenReturn(2800.0);
        when(stockmarketService.lookUpPrice("Microsoft")).thenReturn(250.0);

        portofolio.addStock(new Stock("Apple", 1));
        portofolio.addStock(new Stock("Google", 2));
        portofolio.addStock(new Stock("Microsoft", 3));

        List<Stock> top2 = portofolio.mostValuableStocks(2);
        assertEquals(2, top2.size());
        assertEquals("Google", top2.get(0).getName());
        assertEquals("Microsoft", top2.get(1).getName());

        List<Stock> allStocks = portofolio.mostValuableStocks(10);
        assertEquals(3, allStocks.size());
        assertEquals("Google", allStocks.get(0).getName());
        assertEquals("Microsoft", allStocks.get(1).getName());
        assertEquals("Apple", allStocks.get(2).getName());

        List<Stock> top0 = portofolio.mostValuableStocks(0);
        assertEquals(0, top0.size());

        List<Stock> top10 = portofolio.mostValuableStocks(10);
        assertEquals(3, top10.size());

        verify(stockmarketService, atLeastOnce()).lookUpPrice(anyString());
    }

    @Test
    public void testMostValuableStocksWithEmptyPortfolio() {
        List<Stock> topStocks = portofolio.mostValuableStocks(3);

        assertEquals(0, topStocks.size());
    }
}
