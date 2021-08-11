package com.company.model;

public class Person extends Thread {
    // Person name
    private final String name;
    // Person sex
    private final Sex sex;
    // Bathroom
    private final Bathroom bathroom;
    // Can leave
    private boolean canLeave;
    // Need to go to the bathroom
    private boolean needBathroom ;


    public String getNamei() {
        return name;
    }

    public Person(String name, Sex sex, Bathroom bathroom) {
        this.name = name;
        this.sex = sex;
        this.bathroom = bathroom;
        this.canLeave = false;
        this.needBathroom = true;
    }

    public void useBathroom() {
        //Enter the Bathroom
        this.bathroom.addUser(this);
        if (this.bathroom.isInTheBathroom(this)) {
            // Spend the time inside
            try {
                Thread.sleep(100);
                this.canLeave = true;
                System.out.println(Thread.currentThread().getName() + "  " + getNamei() + " Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void leaveBathroom() {
        // Leave the bathroom
        this.bathroom.removeUser(this);
        this.canLeave = false;
        this.needBathroom = false;

    }

    public Sex getSex() {
        return sex;
    }

    @Override
    public void run() {
        System.out.println(this.getNamei());
        // If the person needs to go to the bathroom
        while (this.needBathroom) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if ((this.bathroom.getCurrentSex().equals(this.getSex()) || this.bathroom.getCurrentSex().equals(Sex.NONE)) && !this.bathroom.isFull() && !this.bathroom.isInTheBathroom(this)) {
                this.useBathroom();
            }
            // If they want to leave
            if (this.canLeave) {
                this.leaveBathroom();
            }
        }
    }
}
