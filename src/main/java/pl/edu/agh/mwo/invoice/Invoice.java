package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.sun.javafx.collections.MappingChange.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product, Integer> products = new HashMap<Product, Integer>();

	public void addProduct(Product product) {
		addProduct(product, 1);;
	}

	public void addProduct(Product product, Integer quantity) {
		products.put(product, quantity);
	}

	public BigDecimal getSubtotal() {
		if (products == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal subtotal = new BigDecimal.ZERO;
		for (Product product : products.) {
			
		}

	}

	public BigDecimal getTax() {
		if (products == null) {
			return BigDecimal.ZERO;
		}
		return null;
	}

	public BigDecimal getTotal() {
		if (products == null) {
			return BigDecimal.ZERO;
		}
		return null;
	}
}
