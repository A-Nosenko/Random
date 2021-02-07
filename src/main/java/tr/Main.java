package tr;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import tr.account.Price;
import tr.common.Constants;
import tr.request.Request;

public class Main {

    public static void main(String[] args) {
        AtomicBoolean ping = new AtomicBoolean();
        Scanner scanner = new Scanner(System.in);
        Request request = new Request();
        try {
            System.out.println(request.fetchPrice("BTCUSDT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            String[] command = scanner.nextLine().split("\\s+");
            System.out.println("\t\t\t>>> " + command[0].toUpperCase());
            switch (command[0].toUpperCase()) {
                case "BALANCE":
                    try {
                        request.balance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "CHECK":
                    try {
                        Price price = request.fetchPrice(command[1].toUpperCase());
                        System.out.println(price);
                        if (command.length == 4) {
                            double balance = Double.parseDouble(command[2]);
                            System.out.printf("\t\t\tStart balance: %.2f$\n", balance);
                            double buyCrypto = balance / price.getPrice();
                            System.out.printf("\t\t\tYou can buy  %f %s\n", buyCrypto, price.getSymbol());
                            double buyUsdt = buyCrypto * Double.parseDouble(command[3]);
                            System.out.printf("\t\t\tFinal balance  %.2f$\n", buyUsdt);
                            System.out.printf("\t\t\tYou profit  %.2f$\n", buyUsdt - balance);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "STATE":
                    try {
                        if (command.length > 1) {
                            request.printState(Double.parseDouble(command[1]));
                        } else {
                            request.printState(Double.MAX_VALUE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "PING":
                    if (ping.get()) {
                        ping.set(false);
                    } else {
                        ping.set(true);
                        new Thread(() -> {
                            while (ping.get()) {
                                try {
                                    Thread.sleep(Constants.PING_TIMEOUT);
                                    System.out.println();
                                    for (int i = 1; i < command.length; i++) {
                                        System.out.println(request.fetchPrice(command[i].toUpperCase()));
                                    }
                                    System.out.println();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    break;
                case "ORDERS":
                    try {
                        request.printOrders();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    if (ping.get()) {
                        ping.set(false);
                    } else {
                        System.out.println("\t\t\t>>> Incorrect command!");
                    }
                    break;
            }
        }
    }
}
