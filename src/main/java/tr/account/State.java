package tr.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class State {
    private String symbol;
    private double priceChange;
    private double priceChangePercent;
    private double weightedAvgPrice;
    private double prevClosePrice;
    private double lastPrice;
    private double lastQty;
    private double bidPrice;
    private double bidQty;
    private double askQty;
    private double askPrice;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
    private double volume;
    private double quoteVolume;
    private long openTime;
    private long closeTime;
    private long firstId;
    private long lastId;
    private long count;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t");
        builder.append(String.format("%-15s", symbol));
        builder.append(" ");
        builder.append("Change: ");
        builder.append(String.format("%-12f", priceChange));
        builder.append(" ");
        builder.append("Percent: ");
        builder.append(String.format("%-12f", priceChangePercent));
        builder.append(" ");
        builder.append("Price: ");
        builder.append(String.format("%-12f", weightedAvgPrice));
        builder.append(" ");
        builder.append("Volume: ");
        builder.append(String.format("%-12f", volume));

        return builder.toString();
    }
}
