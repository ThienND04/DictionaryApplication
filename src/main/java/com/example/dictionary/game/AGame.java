package com.example.dictionary.game;

import java.io.*;
import java.util.Map;

public abstract class AGame implements Serializable {
    private transient boolean isReady = true;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public static void readData(Map<Integer, Double> playersBestTime, String path, String splittingCharacters) {
        playersBestTime.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] tmp = line.split(splittingCharacters);
                playersBestTime.put(Integer.valueOf(tmp[0]), Double.valueOf(tmp[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeData(String path, Map<Integer, Double> playersBestTime, String splittingCharacters) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
//            System.out.println("Writing: " + path);
//            System.out.println(playersBestTime.size());
            playersBestTime.forEach((key, value) -> {
                try {
                    bw.write(String.format("%d%s%f\n", key, splittingCharacters, value));
//                    System.out.println(String.format("%d%s%f\n", key, splittingCharacters, value));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
