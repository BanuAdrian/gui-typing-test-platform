package services;

import models.enums.ActionType;

import java.io.*;
import java.time.LocalTime;

public class LoggingService {
    private static LoggingService instance = null;
    private static final String CSV_PATH = "logs.csv";

    private LoggingService() {
        try {
            File file = new File(CSV_PATH);
            try (BufferedWriter csv = new BufferedWriter(new FileWriter(CSV_PATH, true))) {
                if (file.length() == 0) {
                    csv.write("action_name, timestamp\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static LoggingService getInstance() {
        if (instance == null) {
            instance = new LoggingService();
        }

        return instance;
    }

    public void log(ActionType action) {
        try (BufferedWriter csv = new BufferedWriter(new FileWriter(CSV_PATH, true))) {
            csv.write(action.name() + ", " + LocalTime.now() + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

