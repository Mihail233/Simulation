package com.dobro;

import java.util.Scanner;

public class UserInput {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static int getInteger() {
        return SCANNER.nextInt();
    }

    public static float getFloat() {
        return SCANNER.nextFloat();
    }
}
