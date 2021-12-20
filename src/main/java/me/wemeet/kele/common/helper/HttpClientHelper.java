package me.wemeet.kele.common.helper;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClientHelper {

    public static String doGet(String httpUrl) {
        return doGet(httpUrl, new LinkedMultiValueMap<>());
    }

    public static String doGet(String httpUrl, MultiValueMap<String, String> headers) {
        HttpURLConnection connection = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            for (String key: headers.keySet()) {
                connection.setRequestProperty(key, headers.getFirst(key));
            }
            connection.connect();
            result = getResult(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private static String getResult(HttpURLConnection connection) throws IOException {
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        if (connection.getResponseCode() == 200) {
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sbf = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                sbf.append(temp);
                sbf.append("\r\n");
            }
            result = sbf.toString();
        }

        if (br != null) {
            br.close();
        }
        if (is != null) {
            is.close();
        }

        return result;
    }
}