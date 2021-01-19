package tr;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import tr.request.Request;

public class Main {

    public static void main(String[] args) {
        AtomicBoolean ping = new AtomicBoolean();
        Scanner scanner = new Scanner(System.in);
        Request request = new Request();
        try {
            request.fetchPrice("BTCUSDT");
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
                        request.fetchPrice(command[1].toUpperCase());
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
                                    Thread.sleep(100);
                                    System.out.println();
                                    for (int i = 1; i < command.length; i++) {
                                        request.fetchPrice(command[i].toUpperCase());
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
                        request.orders();
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
