package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product, Integer> products = new HashMap<>();

	public void addProduct(Product product) {
		addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be positive!");
		}
		else if (products.containsKey(product)) {
			products.replace(product, products.get(product) + quantity);
		}
		else {
			products.put(product, quantity);
		}
	}

	public BigDecimal getSubtotal() {
		if (products.isEmpty()) {
			return BigDecimal.ZERO;
		}
		BigDecimal subtotal = BigDecimal.ZERO; 
		for (Product product : products.keySet()) {
			subtotal = subtotal.add(product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
		}
		return subtotal;
	}

	public BigDecimal getTax() {
		if (products.isEmpty()) {
			return BigDecimal.ZERO;
		}
		BigDecimal tax = BigDecimal.ZERO; 
		for (Product product : products.keySet()) {
			tax = tax.add(product.getPrice().multiply(product.getTaxPercent()).multiply(BigDecimal.valueOf(products.get(product))));
		}
		return tax;
	}

	public BigDecimal getTotal() {
		if (products.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return null;
	}
}
