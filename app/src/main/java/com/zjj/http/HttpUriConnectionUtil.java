package com.zjj.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by zjj on 2016/5/4.
 */
public class HttpUriConnectionUtil {
    public static String execute(Request request) throws IOException {
        switch (request.getMethod()) {
            case GET:
                return get(request);
            case POST:
                return post(request);
        }
        return null;
    }

    private static String get(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15 * 3000);
        connection.setReadTimeout(15 * 3000);

        addHeader(connection, request.getHeaders());

        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();
            return new String(out.toByteArray());
        }
        return null;
    }

    private static String post(Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(15 * 3000);
        connection.setReadTimeout(15 * 3000);
        connection.setDoOutput(true);

        connection.addRequestProperty("content-type", "application/json");
        addHeader(connection, request.getHeaders());

        OutputStream os = connection.getOutputStream();
        os.write(request.getContent().getBytes());
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();
            return new String(out.toByteArray());
        }
        return null;
    }

    private static void addHeader(HttpURLConnection connection, Map<String, String> headers) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }
}
