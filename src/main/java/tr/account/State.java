package tr.account;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t\t");
//        builder.append(dateFormat.format(new Date(openTime)));
//        builder.append("\n");
        builder.append(symbol);
        builder.append(" ");
        builder.append("Price change: ");
        builder.append(priceChange);
        builder.append(" ");
        builder.append("Price change percent: ");
        builder.append(priceChangePercent);
        builder.append(" ");
        builder.append("Weighted avg price: ");
        builder.append(weightedAvgPrice);
        builder.append(" ");
        builder.append("Prev close price: ");
        builder.append(prevClosePrice);
        builder.append(" ");
        builder.append("lastPrice: ");
        builder.append(lastPrice);
        builder.append(" ");
        builder.append("Last qty: ");
        builder.append(lastQty);
        builder.append(" ");
        builder.append("Volume: ");
        builder.append(volume);

        return builder.toString();
    }
}
