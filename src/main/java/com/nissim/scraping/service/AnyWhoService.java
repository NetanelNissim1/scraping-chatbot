package com.nissim.scraping.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnyWhoService {

    private static final Pattern PRODUCT_PATTERN = Pattern.compile("<a class=\\\"name-link\\\" .*\\n.*\\n.*\\n.*\\n.*\\n.*\\n([^<]+)<\\/a>\\t\\t\\t\\t\\t\\t\\t\\t\\t<p>([^<]+)<\\/p>\\n\\t\\t\\t\\t\\t\\t\\t\\t\\t<p><a href=\\\"\\/phone\\/([^\\\"]+)");
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Autowired
    private ObjectMapper om;

    public String searchAnyWho(String firstName, String lastName) throws IOException {
        return parseProductHtml(getAnyWho(firstName, lastName));
    }

    private String parseProductHtml(String html) {
        StringBuilder res = new StringBuilder();
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res.append(matcher.group(1) + " address " + matcher.group(2) + ", phone: " + matcher.group(3) + "\n");
        }
        return res.toString();
    }

    private String getAnyWho(String firstName, String lastName) throws IOException {

        Request request = new Request.Builder()
                .url("https://www.anywho.com/people/" + firstName + "+" + lastName + "/")
                .method("GET", null)
                .addHeader("authority", "www.anywho.com")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en-IL;q=0.9,en;q=0.8,he-IL;q=0.7,he;q=0.6")
                .addHeader("cookie", "PHPSESSID=p40efqv91ljeieefsuvmh07d43; device-id=a8810eae-8361-4ad7-a3d8-4ee3abe4a8a6; _ga=GA1.2.1314087002.1649527670; _gid=GA1.2.1023816952.1649527670; last-known-device-id=a8810eae-8361-4ad7-a3d8-4ee3abe4a8a6; last-known-device-id=a8810eae-8361-4ad7-a3d8-4ee3abe4a8a6")
                .addHeader("referer", "https://www.anywho.com/people/" + firstName + "+" + lastName + "/")
                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Google Chrome\";v=\"100\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
