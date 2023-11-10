package com.example.dictionary.api;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class Translate {
    public static String translate(String word, String sl, String tl) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getURL(word, sl, tl)))
                .header("Accept-Encoding", "application/gzip")
                .build();
        HttpResponse<String> response;
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().split("\"")[1];
    }

    private static String getURL(String word, String sl, String tl) {
        return "https://translate.googleapis.com/translate_a/single?client=gtx&dt=t" + "&q=" +
                URLEncoder.encode(word, StandardCharsets.UTF_8) +
                "&sl=" + sl +
                "&tl=" + tl;
    }

    public static ArrayList<String> getSuggestions(String word, int type) {
        word = word.replace(" ", "%20");
        ArrayList<String> res = new ArrayList<>();
        if(type == 0) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.oed.com/autocomplete/dictionary/?q=" + word))
                    .header("Accept-Encoding", "application/gzip")
                    .build();
            HttpResponse<String> response;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                Pattern pattern = Pattern.compile("\"name\":\"(.*?)\"");
                Matcher matcher = pattern.matcher(response.body());
                while (matcher.find()) {
                    res.add(matcher.group(1));
                }
            } catch (Exception ignored) {

            } finally {
                return res;
            }
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://vi.wiktionary.org/w/api.php?action=opensearch&format=json&formatversion=2&namespace=0&limit=10&search=" + word))
                .header("Accept-Encoding", "application/gzip")
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String[] arr = response.body().split("\\[|\\]")[2].split(",");
            for(int i = 0; i < arr.length; i++)
                res.add(arr[i].substring(1, arr[i].length()-1));
        } catch (Exception ignored) {

        } finally {
            return res;
        }



    }


    public static String getDetail(String sentence) throws Exception {
        StringBuilder res = new StringBuilder("<html><head><style>body {padding : 120px 0px}.container {padding: 10px;border: 2px red solid;display: none;position: absolute;background-color: wheat;border-radius: 10px;}.btn {border-radius: 10px;padding: 5px;}</style></head><body><ul>");
        String[] words = sentence.split("\\W+");

        for (String word : words) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/" + word))
                    .header("Accept-Encoding", "application/gzip")
                    .build();
            HttpResponse<String> response;

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response == null || response.statusCode() == 404) {
                continue;
            }
            res.append("<li><h1 style=\"color : red\">").append(word).append("</h1><ul>");

            JSONArray jsonArray = new JSONArray(response.body());

            res.append("<li><h2 style=\"color : green\">Phát âm : </h2>");
            processPhonetics(res, jsonArray.getJSONObject(0).getJSONArray("phonetics"));
            res.append("</li>");

            res.append("<li><h2 style=\"color : green\">Nghĩa : </h2>");
            processMeanings(res, jsonArray.getJSONObject(0).getJSONArray("meanings"));
            res.append("</li>");

            res.append("</ul></li>");
        }

        res.append("</ul>");
        res.append("<div class=\"container\"><button class=\"btn\"><img src=\"https://flyclipart.com/thumb2/audio-interface-speaker-symbol-png-icon-free-download-342951.png\" width=\"20px\" alt=\"\"></button>    <p class=\"txt\">sd</p><button class='btn1'>Tìm kiếm</button></div><script>var div = document.querySelector('.container');var btn = document.querySelector('.btn');var btn1 = document.querySelector('.btn1');var txt = document.querySelector('.txt');document.onmouseup = function() {var selection = window.getSelection();var text = selection.toString().trim();if(text.length) {let rect = selection.getRangeAt(0).getBoundingClientRect();div.style.display = 'block';div.style.left = rect.left + scrollX + 'px';div.style.top = rect.bottom + 20 + scrollY + 'px';btn1.onclick = function(){javaConnector.find(text);};btn.onclick = function(){javaConnector.speak(text);};txt.innerHTML = javaConnector.trans(text);} else {div.style.display = 'none'}}</script></body></html>");
        return res.toString();
    }

    private static void processPhonetics(StringBuilder res, JSONArray phonetics) {
        JSONObject temp;
        res.append("<ul>");
        for (int i = 0; i < phonetics.length(); i++) {
            temp = phonetics.getJSONObject(i);
            if (temp.keySet().contains("text")) {
                res.append("<li>").append(temp.getString("text")).append("</li>");
            }
        }
        res.append("</ul>");
    }

    private static final HashMap<String, String> map = new HashMap<>();

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

        for (int i = 0; i < meanings.length(); i++) {
            res.append("<li>");

            temp = meanings.getJSONObject(i);
            res.append("<h3 style=\"color : blue\">Loại : ").append(map.get(temp.getString("partOfSpeech"))).append("</h3><ul>");


            JSONArray jsonArray = temp.getJSONArray("definitions");
            JSONObject temp1;

            res.append("<li><h4 style=\"color : orange\">Các định nghĩa : </h4><ul style=\"list-style : circle\">");
            for (int j = 0; j < jsonArray.length(); j++) {
                res.append("<li>");
                temp1 = jsonArray.getJSONObject(j);
                if (temp1.keySet().contains("definition")) {
                    res.append("<p>Nghĩa : ").append(temp1.getString("definition")).append("</p>");
                }
                if (temp1.keySet().contains("example")) {
                    res.append("<p>Ví dụ : ").append(temp1.getString("example")).append("</p>");
                }
                res.append("</li>");
            }
            res.append("</ul></li>");


            jsonArray = temp.getJSONArray("synonyms");
            res.append("<li><h4 style=\"color : orange\">Từ đồng nghĩa : </h4><ul style=\"list-style : circle\">");
            for (int j = 0; j < jsonArray.length(); j++) {
                res.append("<li>").append(jsonArray.getString(j)).append("</li>");
            }
            res.append("</ul></li>");


            jsonArray = temp.getJSONArray("antonyms");
            res.append("<li><h4 style=\"color : orange\">Từ trái nghĩa : </h4><ul style=\"list-style : circle\">");
            for (int j = 0; j < jsonArray.length(); j++) {
                res.append("<li>").append(jsonArray.getString(j)).append("</li>");
            }
            res.append("</ul></li>");

            res.append("</ul></li>");
        }

        res.append("</ul>");
    }
}
