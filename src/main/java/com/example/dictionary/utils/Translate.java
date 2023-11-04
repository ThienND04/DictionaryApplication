package com.example.dictionary.utils;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class Translate {
    public static String translate(String word, String sl, String tl) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getURL(word, sl, tl)))
                .header("Accept-Encoding", "application/gzip")
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.body().split("\"")[1];
    }

    private static String getURL(String word, String sl, String tl) {
        StringBuilder url = new StringBuilder("https://translate.googleapis.com/translate_a/single?client=gtx&dt=t");
        url.append("&q=");
        url.append(URLEncoder.encode(word, StandardCharsets.UTF_8));
        url.append("&sl=" + sl);
        url.append("&tl=" + tl);
        return url.toString();
    }

    private final static String css = "<style>ul {list-style:none;}</style>";

    public static String getDetail(String sentence) {
        StringBuilder res = new StringBuilder(css + "<ul>");
        String[] words = sentence.split("\\W+");

        for(int i = 0; i < words.length; i++) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/" + words[i]))
                    .header("Accept-Encoding", "application/gzip")
                    .build();
            HttpResponse<String> response = null;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(response.statusCode() == 404) {
                break;
            }
            res.append("<li><h1>" + words[i] + "</h1><ul>");

            JSONArray jsonArray = new JSONArray(response.body());

            res.append("<li><p>Phát âm : </p>");
            processPhonetics(res, jsonArray.getJSONObject(0).getJSONArray("phonetics"));
            res.append("</li>");

            res.append("<li><p>Nghĩa : </p>");
            processMeanings(res, jsonArray.getJSONObject(0).getJSONArray("meanings"));
            res.append("</li>");

            res.append("</ul></li>");
        }

        res.append("</ul>");
        return res.toString();
    }

    private static void processPhonetics(StringBuilder res, JSONArray phonetics) {
        JSONObject temp;
        res.append("<ul>");
        for(int i = 0; i < phonetics.length(); i++) {
            temp = phonetics.getJSONObject(i);
            if(temp.keySet().contains("text")) {
                res.append("<li>" + temp.getString("text") + "</li>");
            }
        }
        res.append("</ul>");
    }

    private static HashMap<String, String> map = new HashMap<String, String>();
    static {
        map.put("noun", "danh từ");
        map.put("verb", "động từ");
        map.put("interjection", "thán từ");
        map.put("pronoun", "đại từ");
        map.put("adjective", "tính từ");
        map.put("adverb", "trạng từ");
        map.put("determiner", "từ hạn định");
        map.put("preposition", "giới từ");
        map.put("conjunction", "liên từ");
    }
    private static void processMeanings(StringBuilder res, JSONArray meanings) {
        JSONObject temp;
        res.append("<ul>");

        for(int i = 0; i < meanings.length(); i++) {
            res.append("<li><ul>");

            temp = meanings.getJSONObject(i);
            res.append("<li>Loại : " + map.get(temp.getString("partOfSpeech")) + "</li>");


            JSONArray jsonArray = temp.getJSONArray("definitions");
            JSONObject temp1;

            res.append("<li><p>Các định nghĩa : </p><ul>");
            for(int j = 0; j < jsonArray.length(); j++) {
                res.append("<li><ul>");
                temp1 = jsonArray.getJSONObject(j);
                if(temp1.keySet().contains("definition")) {
                    res.append("<li>Nghĩa : " + temp1.getString("definition") + "</li>");
                }
                if(temp1.keySet().contains("example")) {
                    res.append("<li>Ví dụ : " + temp1.getString("example") + "</li>");
                }
                res.append("</ul></li>");
            }
            res.append("</ul></li>");


            jsonArray = temp.getJSONArray("synonyms");
            res.append("<li><p>Từ đồng nghĩa : </p><ul>");
            for(int j = 0; j < jsonArray.length(); j++) {
                res.append("<li>" + jsonArray.getString(j) + "</li>");
            }
            res.append("</ul></li>");


            jsonArray = temp.getJSONArray("antonyms");
            res.append("<li><p>Từ trái nghĩa : </p><ul>");
            for(int j = 0; j < jsonArray.length(); j++) {
                res.append("<li>" +jsonArray.getString(j) + "</li>");
            }
            res.append("</ul></li>");

            res.append("</ul></li>");
        }

        res.append("</ul>");
    }
}
