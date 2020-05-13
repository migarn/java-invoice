package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class InvoiceTest {
    private Invoice invoice;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
        Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
        invoice.addProduct(taxFreeProduct);
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalForManyProducts() {
        invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
        invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("310"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProduct() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("10.30"), Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProduct() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("320.30"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalWithQuantityMoreThanOne() {
        // 2x kubek - price: 10
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        // 3x kozi serek - price: 30
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        // 1000x pinezka - price: 10
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("54.70"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }
    
    @Test
    public void testInvoiceHasNumber() {
    	int number = invoice.getNumber();
    	Assert.assertTrue(number > 0);
    }
    
    @Test
    public void testTwoInvoicesHaveDifferentNumbers() {
    	int number = invoice.getNumber();
    	int number2 = new Invoice().getNumber();
    	Assert.assertNotEquals(number, number2);
    }
    
    @Test
    public void testTwoInvoicesHaveConsequentNumbers() {
    	int number = invoice.getNumber();
    	int number2 = new Invoice().getNumber();
    	Assert.assertEquals(number, number2 - 1);
    }
    
    @Test
    public void testPrintHasResult() {
        String printResult = invoice.print();
        Assert.assertTrue(printResult != null);
    }
    
    @Test
    public void testPrintEmptyInvoice() {
        String printResult = invoice.print();
        Assert.assertEquals(printResult, "Faktura " + invoice.getNumber() + ":\nLiczba pozycji: 0");
    }
    
    @Test
    public void testPrintInvoiceWithOneProduct() {        
        invoice.addProduct(new TaxFreeProduct("Warzywa", new BigDecimal("199.99")));
        String printResult = invoice.print();
        String expectedResult = "Faktura " + invoice.getNumber() +  ":\n- Warzywa, sztuk: 1, cena netto za sztukę: 199.99 PLN"
                + "\nLiczba pozycji: 1";
        Assert.assertEquals(printResult, expectedResult);
    }
    
    @Test
    public void testPrintInvoiceWithManyProducts() {        
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        String printResult = invoice.print();
        String expectedResult = "Faktura " + invoice.getNumber() +  ":\n- Kubek, sztuk: 2, cena netto za sztukę: 5 PLN"
                + "\n- Kozi Serek, sztuk: 3, cena netto za sztukę: 10 PLN"
                + "\n- Pinezka, sztuk: 1000, cena netto za sztukę: 0.01 PLN"
                + "\nLiczba pozycji: 3";
        Assert.assertEquals(printResult, expectedResult);
    }
    
    @Test
    public void testInvoiceHasNoDuplicatesForTheSameProduct() {
        invoice.addProduct(new TaxFreeProduct("Warzywa", new BigDecimal("199.99")));
        invoice.addProduct(new TaxFreeProduct("Warzywa", new BigDecimal("199.99")));
        String printResult = invoice.print();
        String expectedResult = "Faktura " + invoice.getNumber() +  ":\n- Warzywa, sztuk: 2, cena netto za sztukę: 199.99 PLN"
                + "\nLiczba pozycji: 1";
        Assert.assertEquals(printResult, expectedResult);
    }
    
    @Test
    public void testNoMergeForProductsWithDifferentsPrices() {
        invoice.addProduct(new TaxFreeProduct("Warzywa", new BigDecimal("199.99")));
        invoice.addProduct(new TaxFreeProduct("Warzywa", new BigDecimal("198.99")));
        String printResult = invoice.print();
        String expectedResult = "Faktura " + invoice.getNumber() +  ":\n- Warzywa, sztuk: 1, cena netto za sztukę: 199.99 PLN"
                + "\n- Warzywa, sztuk: 1, cena netto za sztukę: 198.99 PLN"
        		+ "\nLiczba pozycji: 2";
        Assert.assertEquals(printResult, expectedResult);
    }
    
    @Test
    public void testNoMergeForProductsWithDifferentTax() {
        invoice.addProduct(new TaxFreeProduct("Warzywa", new BigDecimal("199.99")));
        invoice.addProduct(new OtherProduct("Warzywa", new BigDecimal("199.99")));
        String printResult = invoice.print();
        String expectedResult = "Faktura " + invoice.getNumber() +  ":\n- Warzywa, sztuk: 1, cena netto za sztukę: 199.99 PLN"
                + "\n- Warzywa, sztuk: 1, cena netto za sztukę: 199.99 PLN"
        		+ "\nLiczba pozycji: 2";
        Assert.assertEquals(printResult, expectedResult);
    }
    
    // ta sama nazwa inna cena?
}
