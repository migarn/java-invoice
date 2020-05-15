package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new LinkedHashMap<Product, Integer>();
    private static InvoicesList invoicesList = new InvoicesList();
    private int number = invoicesList.getNewNumber();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (this.products.containsKey(product)) {
            this.products.replace(product, this.products.get(product) + quantity);
        } else {
            this.products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : this.products.keySet()) {
            BigDecimal quantity = new BigDecimal(this.products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : this.products.keySet()) {
            BigDecimal quantity = new BigDecimal(this.products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public String print() {
        StringBuilder result = new StringBuilder();
        result.append("Faktura " + this.number + ":\n");
        for (Product product : this.products.keySet()) {
            result.append("- " + product.getName() + ", sztuk: " + this.products.get(product)
                + ", cena netto za sztukę: " + product.getPrice() + " PLN\n");
        }
        result.append("Liczba pozycji: " + this.products.size());
        return result.toString();
    }
}
