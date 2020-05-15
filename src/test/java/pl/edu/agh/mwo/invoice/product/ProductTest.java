package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.Product;

public class ProductTest {
    @Test
    public void testProductNameIsCorrect() {
        Product product = new OtherProduct("buty", new BigDecimal("100.0"));
        Assert.assertEquals("buty", product.getName());
    }

    @Test
    public void testProductPriceAndTaxWithDefaultTax() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithDairyProduct() {
        Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testPriceWithTax() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullName() {
        new OtherProduct(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithEmptyName() {
        new TaxFreeProduct("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullPrice() {
        new DairyProduct("Banany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNegativePrice() {
        new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
    }
    
    @Test
    public void testBottleOfWineWithTaxAndExcise() {
        Product product = new BottleOfWine("Kadarka", new BigDecimal("5"));
        Assert.assertThat(new BigDecimal("11.71"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
    
    @Test
    public void testFuelCanisterWithTaxAndExciseBeforeRoadmansDay() {
    	Clock mockClock = Clock.fixed(Instant.parse("2021-04-25T23:50:00Z"),ZoneOffset.UTC);
        FuelCanister product = new FuelCanister("95", new BigDecimal("2"));
        product.setClock(mockClock);
        Assert.assertThat(new BigDecimal("8.02"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
    
    @Test
    public void testFuelCanisterWithTaxAndExciseOnRoadmansDay() {
    	Clock mockClock = Clock.fixed(Instant.parse("2021-04-26T08:00:00Z"),ZoneOffset.UTC);
        FuelCanister product = new FuelCanister("95", new BigDecimal("2"));
        product.setClock(mockClock);
        Assert.assertThat(new BigDecimal("2"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
    
    @Test
    public void testFuelCanisterWithTaxAndExciseAfterRoadmansDay() {
    	Clock mockClock = Clock.fixed(Instant.parse("2021-04-27T00:01:00Z"),ZoneOffset.UTC);
        FuelCanister product = new FuelCanister("95", new BigDecimal("2"));
        product.setClock(mockClock);
        Assert.assertThat(new BigDecimal("8.02"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
}
