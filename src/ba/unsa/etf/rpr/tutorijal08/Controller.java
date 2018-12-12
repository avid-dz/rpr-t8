package ba.unsa.etf.rpr.tutorijal08;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    public Label labela;
    public ListView lista;
    public Button dugme;
    public TextField tekst;
    public File korijenskiDirektorij;
    private ObservableList<String> observabilnaLista;

    public Controller() {
        korijenskiDirektorij = new File(System.getProperty("user.home"));
        observabilnaLista = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        lista.setItems(observabilnaLista);
    }

    private void pretraga(String put, String uzorak) {
        File file = new File(put);
        if (file.isDirectory()) {
            try {
                for (File file1 : file.listFiles()) {
                    pretraga(file1.getAbsolutePath(), uzorak);
                }
            } catch (Exception exception) {
            }
        }
        else if (file.isFile()) {
            if (file.getName().contains(uzorak)) {
                observabilnaLista.add(file.getAbsolutePath());
            }
        }
    }

    public void pretragaF(ActionEvent actionEvent) {
        new Thread(() -> {
            try {
                Platform.runLater(() -> pretraga(korijenskiDirektorij.getAbsolutePath(), tekst.getText()));
                //Thread.sleep(500);
            } catch (Exception e) {

            }
        }).start();
    }
}
