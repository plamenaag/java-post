package javapost.utils;

import javapost.ScannerFactory;

public class MenuUtils {

    public static Integer printRegLogInMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Please select option: \n");
        stringBuilder.append("1. Registration \n");
        stringBuilder.append("2. Log in \n");
        stringBuilder.append("3. Exit");
        System.out.println(stringBuilder);
        return selectOption(3);
    }

    public static Integer printUserTypeMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Choose profile type: \n");
        stringBuilder.append("1. Office employee \n");
        stringBuilder.append("2. Courier \n");
        stringBuilder.append("3. Go back");

        System.out.println(stringBuilder);
        return selectOption(3);
    }

    public static Integer printCourierMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Choose option: \n");
        stringBuilder.append("1. Send a message \n");
        stringBuilder.append("2. Show all users \n");
        stringBuilder.append("3. Read all messages \n");
        stringBuilder.append("4. Read all sent messages \n");
        stringBuilder.append("5. Read all received messages \n");
        stringBuilder.append("6. Read all unread messages \n");
        stringBuilder.append("7. Kilometers traveled \n");
        stringBuilder.append("8. Go back");

        System.out.println(stringBuilder);
        return selectOption(8);
    }

    public static Integer printOfficeEmployeeMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Choose option: \n");
        stringBuilder.append("1. Send a message \n");
        stringBuilder.append("2. Show all users \n");
        stringBuilder.append("3. Read all messages \n");
        stringBuilder.append("4. Read all sent messages \n");
        stringBuilder.append("5. Read all received messages \n");
        stringBuilder.append("6. Read all unread messages \n");
        stringBuilder.append("7. Search for courier \n");
        stringBuilder.append("8. Go back");

        System.out.println(stringBuilder);
        return selectOption(8);
    }

    public static Integer selectOption(Integer optionCount) {
        Boolean isCorrect = true;
        System.out.printf("Choose option from 1 to %d: ", optionCount);
        Integer input = null;
        try {
            input = Integer.parseInt(ScannerFactory.getInstance().nextLine());
        } catch (Exception ex) {
            isCorrect = false;
        }
        if (input !=null && (input < 1 || input > optionCount)) {
            isCorrect = false;
        }

        if (!isCorrect) {
            System.out.println("Wrong option input! Do you want to try again(y/n)?");
            if (ScannerFactory.getInstance().nextLine().equalsIgnoreCase("y")) {
                return selectOption(optionCount);
            } else {
                return optionCount;
            }
        }

        return input;

    }
}
