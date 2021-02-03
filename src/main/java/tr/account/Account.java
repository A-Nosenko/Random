package tr.account;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private double makerCommission;
    private double takerCommission;
    private double buyerCommission;
    private double sellerCommission;
    private boolean canTrade;
    private boolean canWithdraw;
    private boolean canDeposit;
    private long updateTime;
    private AccountType accountType;
    private Balance[] balances;
    private Permission[] permissions;

    @Override
    public String toString() {

        Arrays.sort(balances, (balance1, balance2) -> Double.compare(balance2.getFree(), balance1.getFree()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t\tMaker commission: ");
        builder.append(makerCommission);
        builder.append("\n");
        builder.append("\t\t\tTaker commission: ");
        builder.append(takerCommission);
        builder.append("\n");
        builder.append("\t\t\tBuyer commission: ");
        builder.append(buyerCommission);
        builder.append("\n");
        builder.append("\t\t\tSeller commission: ");
        builder.append(sellerCommission);
        builder.append("\n");
        builder.append("\t\t\tCan trade: ");
        builder.append(canTrade);
        builder.append("\n");
        builder.append("\t\t\tCan withdraw: ");
        builder.append(canWithdraw);
        builder.append("\n");
        builder.append("\t\t\tCan deposit: ");
        builder.append(canDeposit);
        builder.append("\n");
        builder.append("\t\t\tUpdate time: ");
        builder.append(dateFormat.format(new Date(updateTime)));
        builder.append("\n");
        builder.append("\t\t\tAccount type: ");
        builder.append(accountType);
        builder.append("\n");
        builder.append("\t\t\tBalances: ");
        builder.append("\n");
        for (Balance balance : balances) {
            if (balance.getFree() > 0 || balance.getLocked() > 0) {
                builder.append(balance);
                builder.append("\n");
            }
        }
        builder.append("\t\t\tPermissions: ");
        builder.append(Arrays.toString(permissions));

        return builder.toString();
    }
}
