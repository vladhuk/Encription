package com.vladhuk.service;

import javafx.stage.Window;

import java.io.File;

public interface FileService {

    File getFileFromDialog(Window currentWindow);

    String getTextFromFile(File file);

    void saveTextToFile(File file, String text);

}
