package tr.account;

import java.util.Formatter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Balance {
    private String asset;
    private double free;
    private double locked;

    public Balance() {
    }

    public Balance(String asset, double free, double locked) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "\t\t\t" + String.format("%-12s", asset) + " >>> [Free: " + free + ", locked: " + locked + "]";
    }
}
