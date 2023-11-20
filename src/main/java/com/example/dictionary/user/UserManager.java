package com.example.dictionary.user;

import com.example.dictionary.Application;
import com.example.dictionary.controller.Controller;
import com.example.dictionary.game.Game1;
import com.example.dictionary.game.Game2;
import com.example.dictionary.game.Game3;
import com.example.dictionary.game.GameManager;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserManager implements Serializable {
    private static final String DATA_PATH = "data/users.dat";
    private ArrayList<User> users;
    private static UserManager instance;

    private transient User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User user) {
        if (currentUser != null) {
            currentUser.writeData();
        }
        currentUser = user;
        user.login();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(DATA_PATH));
                instance = (UserManager) is.readObject();
                if (instance.users.size() != 0) {
                    User.lastUserId = instance.users.get(instance.users.size() - 1).getId();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
                setCurrentUser(user);
                return true;
            }
        }
        return false;
    }

    public boolean create(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        User user = new User(username, password);
        users.add(user);
        setCurrentUser(user);
        return true;
    }

    public void remove() {
        currentUser.remove();
        users.remove(currentUser);
        currentUser = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Users:");
        for (User user : users) {
            builder.append('\n');
            builder.append(user.toString());
        }
        return builder.toString();
    }

    public void writeData() {
        try {
            if(currentUser != null)
                currentUser.writeData();
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(DATA_PATH));
            os.writeObject(instance);
            GameManager.writeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UserManager userManager = new UserManager();
            userManager.users = new ArrayList<>();

            User user1 = new User("admin", "admin");
            userManager.users.add(user1);

            User user = new User("pmquy", "pmquy");
            user.getLoginDays().add(LocalDate.now().minusDays(1));
            user.getLoginDays().add(LocalDate.now().minusDays(2));
            user.getLoginDays().add(LocalDate.now().minusDays(3));
            user.getLoginDays().add(LocalDate.now().minusDays(4));
            userManager.users.add(user);
            System.out.println(user.getLoginDays());

            System.out.println(userManager);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(DATA_PATH));
            os.writeObject(userManager);
            os.close();
            GameManager gameManager = new GameManager();
            os = new ObjectOutputStream(new FileOutputStream(GameManager.DATA_PATH));
            os.writeObject(gameManager);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
