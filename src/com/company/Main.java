package com.company;

import com.company.model.Bathroom;
import com.company.model.Person;
import com.company.model.Sex;

import java.util.ArrayList;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        Bathroom bathroom = Bathroom.getInstance();
        ArrayList<Person> users = new ArrayList<>();

        for (int i = 1; i < 21; i++) {
            if (new Random().nextInt(2) == 0) {
                // Create man
                users.add(new Person("Bush " + i, Sex.MAN, bathroom));
            } else {
                // Create woman
                users.add(new Person("Meri " + i, Sex.WOMAN, bathroom));
            }
        }

        for(int i = 0; i < users.size(); i++){
            users.get(i).start();
        }
    }

}
