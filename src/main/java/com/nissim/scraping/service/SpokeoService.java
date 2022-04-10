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
public class SpokeoService {

    private static final Pattern PRODUCT_PATTERN = Pattern.compile("<div class=\\\"title css-1bsdr70 eewv1840\\\">([^<]+)[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>([^<]+)[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>([^<]+)[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>[^>]+>([^<]+)");
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    @Autowired
    private ObjectMapper om;

    public String searchSpokeo(String firstName, String lastName) throws IOException {

        return parseProductHtml(getSpokeo(firstName, lastName));
    }

    private String parseProductHtml(String html) {
        StringBuilder res = new StringBuilder();
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res.append("Full Name, Age: " + matcher.group(1) + "  " + matcher.group(2) + ", Lived In" + matcher.group(3) + " Related To " + matcher.group(4));
            res.append("\n");
        }
        return res.toString();
    }

    private String getSpokeo(String firstName, String lastName) throws IOException {

        Request request = new Request.Builder()
                .url("https://www.spokeo.com/" + firstName + "-" + lastName)
                .method("GET", null)
                .addHeader("authority", "www.spokeo.com")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en-IL;q=0.9,en;q=0.8,he-IL;q=0.7,he;q=0.6")
                .addHeader("cookie", "spopt=1649589085; a=%5E%5E%5E%5E1649589085%5E%5Eaddress_gs_B%5E%5E1649589085%5E%5E%5E%5ECj0KCQjwgMqSBhDCARIsAIIVN1XhPzU2_e0Dur6lXozZYWNwQJMxCHyqRopbtCTFGO0-bHSk0UmlC1kaArYVEALw_wcB; _sp_ses.6a20=*; full_story_gtm=false; campaigns_list=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ2YWx1ZSI6ImFkZHJlc3NfZ3NfQiJ9.gR2KkmlnTJMm7BVEqjUNnZKEzvDtrfDUqwQZ6c3NH2Y; sem=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJnIjoiYWRkcmVzc19nc19CIiwic2VtRmxvdyI6IkgxMDAwUzEwMDFQMTAxOCJ9.V2KvcRU0IM2lVvV1DeYI-j45OJwoURxIkMfmQIQeopw; campaign_regex=%5Eaddress_%28g%7Cb%7Cy%29s_%28b%7CB%29.*; first_visit_date=2022-04-10+00%3A00%3A00+%2B0000; spokeo_sessions_rails4=7cde2ee46a6f82608207f673a789b31a; _ga=GA1.2.999967170.1649589087; _gid=GA1.2.491391208.1649589087; _gat_Insights=1; _gcl_au=1.1.2059165590.1649589087; _pin_unauth=dWlkPU5tTmpORGRoTmpndE1EZGhZUzAwTkRsakxUbG1NbUV0TVRJeU9ERmlZekE0T1RCbQ; referrer_url=https://www.spokeo.com/John-Smith?loaded=1; current_url=https://www.spokeo.com/people-search?g=name_gs_Y492493&g=address_gs_B&gclid=Cj0KCQjwgMqSBhDCARIsAIIVN1XhPzU2_e0Dur6lXozZYWNwQJMxCHyqRopbtCTFGO0-bHSk0UmlC1kaArYVEALw_wcB; _gac_UA-45987076-5=1.1649589134.Cj0KCQjwgMqSBhDCARIsAIIVN1XhPzU2_e0Dur6lXozZYWNwQJMxCHyqRopbtCTFGO0-bHSk0UmlC1kaArYVEALw_wcB; _gcl_aw=GCL.1649589134.Cj0KCQjwgMqSBhDCARIsAIIVN1XhPzU2_e0Dur6lXozZYWNwQJMxCHyqRopbtCTFGO0-bHSk0UmlC1kaArYVEALw_wcB; cto_bundle=qKTqLF93cGFVTzBJdiUyRkFHdTlKTkFwOWNpbXZZTk45MFJVcXU2Y1JEWFV0VFNhWDJSOG1xUzIlMkZwNW1jcG9LdzVlMWdwZFhaVmUxQXJyY1BScmppUlJVbWJqUXJGcnJ2dEhQSExRcW1qWUVSSTUwYlJPSiUyQnpkS0ZPcnpKY2ZzTHdOeiUyRm5YdkQ1M1Rqa2ZPNnRLdkljaVNvNlZqdyUzRCUzRA; page_view_id_refresh=false; insights=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJnYV9jYW1wYWlnbl92aXNpdCI6ImFkZHJlc3NfZ3NfQiIsImdhX3VzZXJ0eXBlX3BhZ2UiOiJGcmVlIiwicGFnZV92aWV3X2lkIjoiMWQ5MmU3MjEtMDZmZC00NGE0LWEyZDMtYzMxMDRjZWMzMDFiIiwicmVxX2hvc3QiOiJ3d3cuc3Bva2VvLmNvbSIsImdhX3NpbXBsZV90ZXN0X2dyb3VwIjoiIiwiZ2Ffc2VtX2Zsb3dfdmlzaXQiOiJIMTAwMFMxMDAxUDEwMTgiLCJnYV9jYW1wYWlnbl9zZXNzaW9uX2F0dHIiOiJhZGRyZXNzX2dzX0IifQ.arbLfsdCUm2RTW1i3CMSxFvhYiiAWpzLx7IM5OvkleQ; _sp_id.6a20=406e15a1-f9fc-4425-a0d4-1914ff5b4e5f.1649589085.1.1649589146.1649589085.400dd527-7d46-4b3e-8a87-57e0cab87e40; last_campaign_tstamp=1649589146; _sp_id.6a20=406e15a1-f9fc-4425-a0d4-1914ff5b4e5f.1649589085.1.1649589197.1649589085.400dd527-7d46-4b3e-8a87-57e0cab87e40; last_campaign_tstamp=1649589197; spokeo_sessions_rails4=7cde2ee46a6f82608207f673a789b31a")
                .addHeader("if-none-match", "W/\"4fbb773a0ed58f4bef6ac45f6c69fe81\"")
                .addHeader("referer", "https://www.spokeo.com/people-search?g=name_gs_Y492493&g=address_gs_B&gclid=Cj0KCQjwgMqSBhDCARIsAIIVN1XhPzU2_e0Dur6lXozZYWNwQJMxCHyqRopbtCTFGO0-bHSk0UmlC1kaArYVEALw_wcB")
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
        String res = response.body().string();
        return res;
    }


}
