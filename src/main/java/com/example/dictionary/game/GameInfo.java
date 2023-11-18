package com.example.dictionary.game;

import com.example.dictionary.user.UserManager;

import java.io.Serializable;

public class GameInfo implements Serializable {
    public enum Status{
        WIN,
        LOSE;
    }

    private int gameId;
    private int playerId;
    private double time;
    private Status status;

    public GameInfo(int gameId, int playerId, int time, Status status) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.time = time;
        this.status = status;
    }

    public GameInfo(int gameId, double time, Status status) {
        this.gameId = gameId;
        this.playerId = UserManager.getInstance().getCurrentUser().getId();
        this.time = time;
        this.status = status;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}