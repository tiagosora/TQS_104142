package org.stocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @InjectMocks
    private StocksPortfolio portfolio;
    @Mock
    private IStockmarketService market;

    @BeforeEach
    void testSetup(){
        market = Mockito.mock(IStockmarketService.class);
        portfolio = mock(new StocksPortfolio(market));
    }

    @DisplayName("Test getTotalValueAnnot()")
    @Test
    void getTotalValueAnnot(){
        // 1. Prepare a mock to substitute the remote service (@Mock annotation)
        // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(market.lookUpPrice("EBAY")).thenReturn(4.8);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);

        // 4. Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("Ebay", 2));
        portfolio.addStock(new Stock("MSFT", 4));

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(4*1.5+2*4.8, portfolio.getTotalValue());
        verify(market, times(2)).lookUpPrice(anyString());
    }

}
