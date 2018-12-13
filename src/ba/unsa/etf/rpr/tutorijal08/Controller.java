package ba.unsa.etf.rpr.tutorijal08;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller {

    public Label labela;
    public ListView lista;
    public Button dugmeTrazi;
    public TextField tekst;
    private File korijenskiDirektorij;
    private ObservableList<String> observabilnaLista;
    public Button dugmePrekini;

    public Controller() {
        korijenskiDirektorij = new File(System.getProperty("user.home"));
        observabilnaLista = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        lista.setItems(observabilnaLista);
        dugmePrekini.setDisable(true);
        dugmeTrazi.setDisable(false);
        lista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                prikazProzoraZaSlanje();
            }
        });
    }

    private void prikazProzoraZaSlanje() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("prozorZaSlanje.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Slanje poštom");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pretraga(String put, String uzorak) {
        if (dugmePrekini.isDisabled()) Thread.currentThread().stop();
        File file = new File(put);
        if (file.isFile()) {
            if (file.getName().contains(uzorak)) {
                try {
                    Platform.runLater(() -> observabilnaLista.add(file.getAbsolutePath()));
                    Thread.sleep(180);
                } catch (Exception exception) {
                }
            }
        }
        else if (file.isDirectory()) {
            try {
                for (File file1 : file.listFiles()) {
                    pretraga(file1.getAbsolutePath(), uzorak);
                }
            } catch (Exception exception) {
            }
        }
        if (file.getAbsolutePath().equals(korijenskiDirektorij.getAbsolutePath())) {
            dugmePrekini.setDisable(true);
            dugmeTrazi.setDisable(false);
        }
    }

    public void pretragaF(ActionEvent actionEvent) {
        observabilnaLista.clear();
        dugmePrekini.setDisable(false);
        dugmeTrazi.setDisable(true);
        new Thread(() -> pretraga(korijenskiDirektorij.getAbsolutePath(), tekst.getText())).start();
    }

    public void prekiniF(ActionEvent actionEvent) {
        dugmePrekini.setDisable(true);
        dugmeTrazi.setDisable(false);
    }
}
