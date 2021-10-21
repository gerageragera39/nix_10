package ua.com.alevel.controller;

import ua.com.alevel.dao.UserDao;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static int indexForCreate = 0;

    private final UserService userService = new UserService();

    public void run() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position = "0";
        runNavigation();
        try {
            while (position != null) {
                System.out.print("select your option : ");
                position = reader.readLine();
                if(!position.equals("0")){
                    crud(position, reader);
                }
                if (position.equals("0")) {
                    System.exit(0);
                }
                //crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {

        System.out.println();
        System.out.println("if you want create user, please enter 1");
        System.out.println("if you want update user, please enter 2");
        System.out.println("if you want delete user, please enter 3");
        System.out.println("if you want findById user, please enter 4");
        System.out.println("if you want findAll user, please enter 5");
        System.out.println("if you want exit, please enter 0");
        System.out.println();
    }

    private void crud(String position, BufferedReader reader) {

        switch (position) {
            case "1" : create(reader); break;
            case "2" : update(reader); break;
            case "3" : delete(reader); break;
            case "4" : findById(reader);break;
            case "5" : findAll(reader);break;
        }

        runNavigation();
    }

    private void create(BufferedReader reader) {

        try {
            System.out.print("Please, enter your name : ");
            String name = reader.readLine();
            System.out.print("Please, enter your email : ");
            String email = reader.readLine();
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            boolean emailRegex =  matcher.find();
            while(!emailRegex){
                System.out.print("Email entered incorrectly, please, enter your email : ");
                email = reader.readLine();
                matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                emailRegex =  matcher.find();
            }
            System.out.print("Please, enter your age (from 0 to 117) : ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            while ((age<0) || (age>117)){
                System.out.print("Wrong age, please, enter your age (from 0 to 117) : ");
                ageString = reader.readLine();
                age = Integer.parseInt(ageString);
            }
            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setEmail(email);
            User[] users = userService.findAll();
            indexForCreate = userService.create(user, users, indexForCreate);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void update(BufferedReader reader) {

        try {
            System.out.print("Please, enter id : ");
            String id = reader.readLine();
            System.out.print("Please, enter your name : ");
            String name = reader.readLine();
            System.out.print("Please, enter your email : ");
            String email = reader.readLine();
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            boolean emailRegex =  matcher.find();
            while(!emailRegex){
                System.out.print("Email entered incorrectly, please, enter your email : ");
                email = reader.readLine();
                matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                emailRegex =  matcher.find();
            }
            System.out.print("Please, enter your age (from 0 to 117) : ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            while ((age<0) || (age>117)){
                System.out.print("Wrong age, please, enter your age (from 0 to 117) : ");
                ageString = reader.readLine();
                age = Integer.parseInt(ageString);
            }
            User user = new User();
            user.setId(id);
            user.setAge(age);
            user.setName(name);
            if(indexForCreate!=0){
                if (!existByEmail(email)) {
                    user.setEmail(email);
                    userService.update(user);
                } else {
                    System.out.println("USER DOES NOT EXIST AT THIS EMAIL");
                }
            }
            if(indexForCreate == 0){
                user.setEmail(email);
                userService.update(user);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {

        try {
            System.out.print("Please, enter id : ");
            String id = reader.readLine();
            userService.delete(id);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {

        try {
            System.out.print("Please, enter id : ");
            String id = reader.readLine();
            User user = userService.findById(id);
            System.out.println(user.toString());
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findAll(BufferedReader reader) {

        User[] users = userService.findAll();
        int indexOfUser = 1;
        if (users != null && users.length != 0) {
            for (User user : users) {
                System.out.println(user.toString(indexOfUser));
                indexOfUser++;
            }
        } else {
            System.out.println("USERS EMPTY");
        }
    }

    private boolean existByEmail(String email) {

        UserDao user = new UserDao();
        return user.existByEmail(email);
    }
}
