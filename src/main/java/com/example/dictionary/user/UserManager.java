package com.example.dictionary.user;

import java.io.*;
import java.util.ArrayList;

public class UserManager implements Serializable {
    private ArrayList<User> users;
    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream("data/users.dat"));
                instance = (UserManager) is.readObject();
                if(instance.users.size() != 0) {
                    User.lastUserId = instance.users.get(instance.users.size() - 1).getId();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
                Data.getInstance().setUser(user);
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
        File t = new File("data/user-" + user.getId() + ".txt");
        try {
            t.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Data.getInstance().setUser(user);
            return true;
        }
    }

    public void remove() {
        File t = new File("data/user-" + Data.getInstance().getUser().getId() + ".txt");
        t.delete();
        users.remove(Data.getInstance().getUser());
        Data.getInstance().setUser(null);
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
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
            os.writeObject(instance);
            Data.getInstance().writeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UserManager userManager = new UserManager();
            userManager.setUsers(new ArrayList<>());
            userManager.create("pmquy", "pmquy");
            userManager.create("admin", "admin");
            System.out.println(userManager);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
            os.writeObject(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
