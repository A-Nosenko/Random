package tr.account;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String symbol;
    private String orderId;
    private String orderListId;
    private String clientOrderId;
    private double price;
    private double origQty;
    private double executedQty;
    private double cummulativeQuoteQty;
    private String status;
    private String timeInForce;
    private String type;
    private String side;
    private double stopPrice;
    private double icebergQty;
    private long time;
    private long updateTime;
    private boolean working;
    private double origQuoteOrderQty;

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return  String.format(" %-10s", symbol) + " >>> [Price: " + String.format("%f", price) +
            String.format(" Amount: %-10.2f", origQty) + String.format(" Total: %-5.2f", price * origQty) + " " + status + " " + dateFormat.format(new Date(updateTime)) +
            " " + (isWorking() ? " + " : " - ") + "]";
    }
}
