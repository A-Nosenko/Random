package tr.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import tr.account.Account;
import tr.account.Order;
import tr.account.Price;
import tr.account.State;
import tr.common.Constants;
import tr.settings.Settings;


public class Request {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Settings settings;

    {
        Settings settingsTmp;
        try {
            InputStream in = getClass().getResourceAsStream("/binance_api_key.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            reader.lines().forEach(builder::append);
            settingsTmp = OBJECT_MAPPER.readValue(builder.toString(), Settings.class);
        } catch (IOException e) {
            settingsTmp = null;
            e.printStackTrace();
        }
        settings = settingsTmp;
    }

    private String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }

    public void balance() throws Exception {
        long timestamp = System.currentTimeMillis();
        String signature = encode(settings.getSecretKey(), "timestamp=" + timestamp);
        String endpoint = "https://api.binance.com/api/v3/account?timestamp=" + timestamp + "&signature=" + signature;

        HttpURLConnection httpConnection = setupHttpConnection(endpoint, "GET");
        String json = extractJson(httpConnection);
        httpConnection.disconnect();

        Account account = OBJECT_MAPPER.readValue(json, Account.class);

        System.out.println(account);

    }

    public void printOrders() throws Exception {
        Order[] orders = getOrders();

        if (orders.length == 0) {
            System.out.println("\n\n\t\t\t============ ALL ORDERS COMPLETED ============\n\n");
        }

        Map<String, Double> prices = new HashMap<>();
        Stream.of(getPrices()).forEach(price -> prices.put(price.getSymbol(), price.getPrice()));

        Arrays.sort(orders, Comparator.comparingDouble(order -> prices.get(order.getSymbol()) - order.getPrice()));

        int count = 0;
        for (Order order : orders) {
            System.out.printf("%-10f [%f]", prices.get(order.getSymbol()), prices.get(order.getSymbol()) - order.getPrice());
            System.out.println(order);
            count++;
        }
        System.out.printf("\n\t\t\tTOTAL ORDERS: %d\n\n", count);

    }

    private Order[] getOrders() throws Exception {
        long timestamp = System.currentTimeMillis();
        String signature = encode(settings.getSecretKey(), "timestamp=" + timestamp);
        String endpoint = "https://api.binance.com/api/v3/openOrders?timestamp=" + timestamp + "&signature=" + signature;

        HttpURLConnection httpConnection = setupHttpConnection(endpoint, "GET");
        String json = extractJson(httpConnection);
        httpConnection.disconnect();

        return OBJECT_MAPPER.readValue(json, Order[].class);
    }

    private String extractJson(HttpURLConnection httpConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
        String output = "";
        StringBuilder json = new StringBuilder();
        while ((output = br.readLine()) != null) {
            json.append(output);
        }
        return json.toString();
    }

    private HttpURLConnection setupHttpConnection(String endpoint, String requestMethod)
        throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setInstanceFollowRedirects(false);
        httpConnection.setUseCaches(false);
        httpConnection.setRequestMethod(requestMethod);
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setRequestProperty("X-MBX-APIKEY", settings.getApiKey());

        if (httpConnection.getResponseCode() != 200) {
            System.out.println(httpConnection.getResponseCode() + " response code, for account. "
                + httpConnection.getResponseMessage());
            httpConnection.disconnect();
            throw new Exception(httpConnection.getResponseCode() + " response code, for account.");
        }
        return httpConnection;
    }

    public void fetchPrice(String pair) throws Exception {
        String endpoint = "https://api.binance.com/api/v1/ticker/price?symbol=" + pair;

        HttpURLConnection httpConnection = setupHttpConnection(endpoint, "GET");
        String json = extractJson(httpConnection);
        httpConnection.disconnect();

        Price price = OBJECT_MAPPER.readValue(json, Price.class);
        System.out.println(price);
    }

    private Price[] getPrices() throws Exception {
        String endpoint = "https://api.binance.com/api/v1/ticker/price";

        HttpURLConnection httpConnection = setupHttpConnection(endpoint, "GET");
        String json = extractJson(httpConnection);
        httpConnection.disconnect();

        return OBJECT_MAPPER.readValue(json, Price[].class);
    }

    public void printPrice() throws Exception {
        Stream.of(getPrices())
            .filter(price -> price.getSymbol().endsWith(Constants.USDT))
            .sorted(Comparator.comparingDouble(Price::getPrice))
            .forEach(System.out::println);
    }

    private State[] getStates() throws Exception {
        String endpoint = "https://api.binance.com/api/v1/ticker/24hr";

        HttpURLConnection httpConnection = setupHttpConnection(endpoint, "GET");
        String json = extractJson(httpConnection);
        httpConnection.disconnect();

        return OBJECT_MAPPER.readValue(json, State[].class);
    }

    public void printState() throws Exception {
        Stream.of(getStates())
            .filter(state -> state.getSymbol().endsWith(Constants.USDT))
            .sorted(Comparator.comparingDouble(State::getLastPrice))
            .forEach(System.out::println);
    }
}
