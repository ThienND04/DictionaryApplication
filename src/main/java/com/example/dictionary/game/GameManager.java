package com.example.dictionary.game;

import com.example.dictionary.user.User;
import com.example.dictionary.user.UserManager;

import java.io.*;
import java.util.ArrayList;

public class GameManager implements Serializable {
    public static final String DATA_PATH = "data/games.dat";
    private final ArrayList<GameInfo> playersHistory = new ArrayList<>();
    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(DATA_PATH))) {
                instance = (GameManager) is.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    public double getBestTime(int gameId, int playerId) {
        return playersHistory.stream().
                filter(gameInfo -> gameInfo.getPlayerId() == playerId
                        && gameInfo.getGameId() == gameId
                        && gameInfo.getStatus() == GameInfo.Status.WIN).
                map(GameInfo::getTime).min(Double::compare).orElse(Double.MAX_VALUE);
    }

    public ArrayList<GameInfo> getPlayersHistory() {
        return playersHistory;
    }

    public static void writeData() {
        try (ObjectOutputStream is = new ObjectOutputStream(new FileOutputStream(DATA_PATH))) {
            is.writeObject(getInstance());
            System.out.println(getInstance().getPlayersHistory().size());
            System.out.println("wrote");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
