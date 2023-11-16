package com.example.dictionary.achievement;

import com.example.dictionary.user.UserManager;
import javafx.scene.image.Image;

public enum AchievementEnum {
    LOGIN1("Chăm chỉ LV1", "login.png", "Đăng nhập 1 ngày", 1),
    LOGIN2("Chăm chỉ LV2", "login.png", "Đăng nhập 3 ngày", 3),
    LOGIN3("Chăm chỉ LV3", "login.png", "Đăng nhập 7 ngày", 7),
    GAME1("Vua trò chơi LV1", "game.png", "Hoàn thành 1 game", 1),
    GAME2("Vua trò chơi LV2", "game.png", "Hoàn thành 3 game", 3),
    GAME3("Vua trò chơi LV3", "game.png", "Hoàn thành 5 game", 5),
    FIND1("Tìm kiếm LV1", "find.png", "Tìm kiếm 5 từ online", 5),
    FIND2("Tìm kiếm LV2", "find.png", "Tìm kiếm 10 từ online", 10),
    FIND3("Tìm kiếm LV3", "find.png", "Tìm kiếm 15 từ online", 15),
    LEARN1("Học tập LV1", "learn.png", "Thêm 5 từ vào danh sách", 5),
    LEARN2("Học tập LV2", "learn.png", "Thêm 10 từ vào danh sách", 10),
    LEARN3("Học tập LV3", "learn.png", "Thêm 15 từ vào danh sách", 15);

    private final String name;
    private final Image image;
    private final String description;
    private final int max;

    public int getCurrent() {
        switch (this) {
            case LOGIN1, LOGIN2, LOGIN3 -> {
                return UserManager.getInstance().getCurrentUser().getLoginDays().size();
            }
            case GAME1, GAME2, GAME3 -> {
                return 0;
            }
            case FIND1, FIND2, FIND3 -> {
                return UserManager.getInstance().getCurrentUser().getCountOfSearchWords();
            }
            case LEARN1, LEARN2, LEARN3 -> {
                return UserManager.getInstance().getCurrentUser().getCountOfAddWords();
            }
        }
        return 0;
    }

    public int getMax() {
        return max;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    AchievementEnum(String name, String path, String description, int max) {
        this.name = name;
        this.image = new Image(getClass().getResourceAsStream(path));
        this.description = description;
        this.max = max;
    }
}
