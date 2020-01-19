package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private LinkedHashMap<Product, Integer> products;

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
		return null;

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
