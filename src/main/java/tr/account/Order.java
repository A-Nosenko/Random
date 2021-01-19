package tr.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String status; //
    private String timeInForce; //
    private String type; //
    private String side; //
    private double stopPrice;
    private double icebergQty;
    private long time;
    private long updateTime;
    private boolean working;
    private double origQuoteOrderQty;

    public void setIsWorking(boolean working) {
        this.working = working;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return  String.format("\t\t\t%-10s", symbol) + " >>> [" + String.format("%f", price) +
            " " + status + " " + dateFormat.format(new Date(updateTime)) +
            " " + (isWorking() ? " Is working" : "") + "]";
    }
}