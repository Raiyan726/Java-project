package service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageStorage {
    private static final String DATA_FOLDER = "data";
    private static final String FILE_PATH = DATA_FOLDER + File.separator + "messages.txt";

    // Prepare folder and file before saving
    private static void prepareStorage() throws IOException {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public static void saveMessage(String message) {
        try {
            prepareStorage();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                writer.write(timestamp + " - " + message);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
}
