package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class FuelCanister extends Product {
    private BigDecimal excise;

    protected FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
        excise = new BigDecimal("5.56");
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return this.getPrice().multiply(this.getTaxPercent()).add(this.getPrice()).add(excise);
    }

}
