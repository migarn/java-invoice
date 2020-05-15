package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;

public class FuelCanister extends Product {
    private BigDecimal excise;
    private Clock clock;
    private final Month roadmansMonth = Month.APRIL;
    private final int roadmansDay = 26;

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
        this.excise = new BigDecimal("5.56");
        setClock(Clock.systemDefaultZone());
    }

    public void setClock(Clock newClock) {
        this.clock = newClock;
    }

    @Override
    public BigDecimal getPriceWithTax() {
        LocalDateTime currentTime = LocalDateTime.now(this.clock);
        if (currentTime.getMonth().equals(this.roadmansMonth)
                && currentTime.getDayOfMonth() == this.roadmansDay) {
            return this.getPrice();
        }
        return this.getPrice().multiply(this.getTaxPercent()).add(this.getPrice()).add(this.excise);
    }
}
