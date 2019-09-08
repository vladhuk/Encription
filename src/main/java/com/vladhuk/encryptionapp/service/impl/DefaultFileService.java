package com.vladhuk.encryptionapp.service.impl;

import com.vladhuk.encryptionapp.service.FileService;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DefaultFileService implements FileService {

    @Override
    public File getFileFromDialog(Window window) {
        return new FileChooser().showOpenDialog(window);
    }

    @Override
    public String getTextFromFile(File file) {
        try {
            final List<String> strings = Files.readAllLines(file.toPath());
            return String.join("\n", strings);
        } catch (NullPointerException e) {
            // Nothing
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveTextToFile(File file, String text) {
        try {
            Files.write(file.toPath(), text.getBytes());
        } catch (NullPointerException e) {
            // Nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
