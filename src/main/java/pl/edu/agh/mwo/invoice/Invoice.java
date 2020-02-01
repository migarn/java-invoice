package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product, Integer> products = new HashMap<>();

	public void addProduct(Product product) {
		addProduct(product, 1);;
	}

	public void addProduct(Product product, Integer quantity) {
		
	}

	public BigDecimal getSubtotal() {
		if (products.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return null;

	}

	public BigDecimal getTax() {
		if (products.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return null;
	}

	public BigDecimal getTotal() {
		if (products.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return null;
	}
}
