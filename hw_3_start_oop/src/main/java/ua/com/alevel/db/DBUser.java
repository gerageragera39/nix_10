package ua.com.alevel.db;

import ua.com.alevel.controller.UserController;
import ua.com.alevel.entity.User;

import java.util.Arrays;
import java.util.UUID;

public class DBUser {

    private User[] users;
    private static DBUser instance;

    private DBUser() {
        users = new User[0];
    }

    public static DBUser getInstance() {
        if (instance == null) {
            instance = new DBUser();
        }
        return instance;
    }

    public void create(User user) {
        user.setId(generateId());
        users = Arrays.copyOf(users, users.length + 1);
        users[users.length-1] = user;
        //users.add(user);
    }

    public void update(User user) {
        User current = findById(user.getId());
        current.setName(user.getName());
        current.setAge(user.getAge());
        current.setEmail(user.getEmail());
    }

    public void delete(String id) {
        User userToDelete = findById(id);
        int index = -1;
        for (int i = 0; i < users.length; i++) {
            if(users[i].getId().equals(String.valueOf(userToDelete.getId()))){
                users[i] = null;
                index = i;
            }
        }
        User arrayWithDeletedUser[] = new User[users.length - 1];
        for (int i = 0; i < index; i++) {
           arrayWithDeletedUser[i] = users[i];
        }
        for (int i = index; i < arrayWithDeletedUser.length; i++) {
            arrayWithDeletedUser[i] = users[i+1];
        }
        users = Arrays.copyOf(arrayWithDeletedUser, users.length - 1);
//        users.removeIf(user -> user.getId().equals(id));
    }

    public User findById(String id) {

        for (int j = 0; j < users.length; j++) {
            if(id.equals(String.valueOf(users[j].getId()))){
                return users[j];
            }
        }

        System.out.println();
        System.out.println("USER NOT FOUND");
        UserController controller = new UserController();
        controller.run();
        return users[0];

//        return users.stream()
//                .filter(u -> u.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    public User[] findAll() {
        return users;
//        return users;
    }

    public boolean existByEmail(String email) {

        for (int i = 0; i < users.length; i++) {
            if(email.equals(String.valueOf(users[i].getEmail()))){
                return true;
            }
        }

        return false;
//        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    private String generateId() {

        String id = UUID.randomUUID().toString();
        for (int i = 0; i < users.length; i++) {
            if(id.equals(String.valueOf(users[i].getId()))){
                generateId();
            }
        }

        return id;
//        if (users.stream().anyMatch(user -> user.getId().equals(id))) {
//            return generateId();
//        }
    }

    public int numOfAllUsers() {
        return users.length;
    }
}
