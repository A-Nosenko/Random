package tr.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Price {
    private String symbol;
    private double price;

    @Override
    public String toString() {
        return String.format("\t\t\t>>> %s >>> %f", symbol, price);
    }
}
