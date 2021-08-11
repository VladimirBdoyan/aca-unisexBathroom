package com.company.model;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Bathroom {

    // Bathroom capacity
    private static final int CAPACITY = 3;

    // Singleton
    private static Bathroom instance = new Bathroom(CAPACITY);

    //Using sex
    private Sex currentSex;
    // Bathroom capacity
    private final int capacity;
    // Users list
    private ArrayList<Person> users;

    // Semaphore
    private Semaphore semaphore;
    private Semaphore leftMutex;
    private Semaphore enterMutex;

    public Bathroom(int capacity) {
        this.capacity = capacity;
        this.currentSex = Sex.NONE;
        this.users = new ArrayList<>();
        this.semaphore = new Semaphore(this.capacity, true);
        this.leftMutex = new Semaphore(1, true);
        this.enterMutex = new Semaphore(1, true);
    }

    public static Bathroom getInstance() {
        return instance;
    }

    public void addUser(Person person) {
        try {
            this.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //if it is the first person to enter the bathroom
        if (this.users.isEmpty()) {
            this.currentSex = person.getSex();
        }
        // Check if the bathroom isn't full
        if (!this.isFull() && !this.users.contains(person) && getCurrentSex().equals(person.getSex())) {
            try {
                this.enterMutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Add the person
            if (this.users.add(person)) {
                System.out.println((Thread.currentThread().getName() + "  " + person.getNamei() + " entered the bathroom"));
            }
            this.enterMutex.release();
            // Check if the bathroom is full
            if (this.isFull()) {
                System.out.println("The bathroom is full");
            }
        }
    }

    public void removeUser(Person person) {
        this.semaphore.release();
        // Check if the bathroom isn't empty
        if (!this.users.isEmpty()) {
            try {
                this.leftMutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.users.remove(person)) {
                System.out.println((Thread.currentThread().getName() + "  " + person.getNamei() + " left the bathroom"));
            }
            this.leftMutex.release();
            // Check if the bathroom is empty
            if (this.users.isEmpty()) {
                System.out.println("The bathroom is empty");
                this.currentSex = Sex.NONE;
            }
        }
    }

    public boolean isFull() {
        return this.capacity == this.users.size();
    }

    public Sex getCurrentSex() {
        return this.currentSex;
    }

    public boolean isInTheBathroom(Person person) {
        return this.users.contains(person);
    }
}
