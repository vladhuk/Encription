package com.vladhuk.controller;

import com.vladhuk.encription.Encryption;
import com.vladhuk.encription.Encryptor;
import com.vladhuk.service.FileService;
import com.vladhuk.service.PrinterService;
import com.vladhuk.service.impl.DefaultFileService;
import com.vladhuk.service.impl.DefaultPrinterService;
import com.vladhuk.util.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private ComboBox<Language> languageComboBox;

    @FXML
    private ComboBox<Encryption> encryptionComboBox;

    @FXML
    private TextField keyField;

    private PrinterService printerService;
    private FileService fileService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillComboBoxWithEnumValues(Language.class, languageComboBox);
        fillComboBoxWithEnumValues(Encryption.class, encryptionComboBox);

        this.printerService = new DefaultPrinterService();
        this.fileService = new DefaultFileService();
    }

    private void fillComboBoxWithEnumValues(Class<?> instance, ComboBox comboBox) {
        try {
            Method valuesMethod = instance.getMethod("values");

            ObservableList list = FXCollections.observableArrayList((Object[]) valuesMethod.invoke(null));
            comboBox.setItems(list);

            if (list.size() > 0) {
                comboBox.setValue(list.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Encryptor getEncrypter() {
        final Language language = languageComboBox.getValue();
        final int key = Integer.valueOf(keyField.getText());

        return encryptionComboBox.getValue().getEncryptor(language, key);
    }

    private Window getWindowByEvent(ActionEvent event) {
        return ((Node) event.getSource()).getScene().getWindow();
    }

    @FXML
    void decode(ActionEvent event) {
        final Encryptor encryptor = getEncrypter();
        final String decodedText = encryptor.decode(inputTextArea.getText());

        outputTextArea.setText(decodedText);
    }

    @FXML
    void encode(ActionEvent event) {
        final Encryptor encryptor = getEncrypter();
        final String decodedText = encryptor.encode(inputTextArea.getText());

        outputTextArea.setText(decodedText);
    }

    @FXML
    void pasteFromFile(ActionEvent event) {
        final Window currentWindow = getWindowByEvent(event);
        final File file = fileService.getFileFromDialog(currentWindow);
        final String text = fileService.getTextFromFile(file);

        inputTextArea.setText(text);
    }

    @FXML
    void print(ActionEvent event) {
        final String text = outputTextArea.getText();
        printerService.print(text);
    }

    @FXML
    void saveToFile(ActionEvent event) {
        final Window window = getWindowByEvent(event);
        final File file = fileService.getFileFromDialog(window);
        final String text = outputTextArea.getText();

        fileService.saveTextToFile(file, text);
    }

}
