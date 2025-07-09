package com.nestaway;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.LogManager;

public class Main {

    private static final String LOGGER_SETTINGS = "src/main/resources/properties/logging.properties";
    private static final String VIEW_TYPE = "VIEW_TYPE";
    private static final String DAO_TYPE = "DAO_TYPE";

    public static void main(String[] args) throws IOException {

        try (InputStream input = new FileInputStream(LOGGER_SETTINGS)) {
            LogManager.getLogManager().readConfiguration(input);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose VIEW_TYPE (CLI / FX): ");
        String viewType = scanner.nextLine().trim().toUpperCase();

        while (!viewType.equals("CLI") && !viewType.equals("FX")) {
            System.out.print("Invalid input. Please enter CLI or FX: ");
            viewType = scanner.nextLine().trim().toUpperCase();
        }

        System.out.print("Choose DAO_TYPE (JDBC / FS / DEMO): ");
        String daoType = scanner.nextLine().trim().toUpperCase();

        while (!daoType.equals("JDBC") && !daoType.equals("FS") && !daoType.equals("DEMO")) {
            System.out.print("Invalid input. Please enter JDBC, FS, or DEMO: ");
            daoType = scanner.nextLine().trim().toUpperCase();
        }

        System.setProperty(VIEW_TYPE, viewType);
        System.setProperty(DAO_TYPE, daoType);

        switch (MainType.valueOf(viewType)) {
            case FX:
                MainFX.run(args);
                break;
            case CLI:
                MainCLI.run();
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }
}
