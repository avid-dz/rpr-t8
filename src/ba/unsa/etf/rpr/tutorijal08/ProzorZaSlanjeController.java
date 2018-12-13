package ba.unsa.etf.rpr.tutorijal08;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class ProzorZaSlanjeController implements Initializable {

    public TextField ime;
    public TextField prezime;
    public Button potvrdjivanje;
    public TextField kontaktAdresa;
    public TextField postanskiBroj;
    public ComboBox grad;
    private boolean validanPostanski;

    public ProzorZaSlanjeController() { validanPostanski = false; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grad.setItems(FXCollections.observableArrayList("Sarajevo", "Gorazde", "Zenica", "Tuzla", "Mostar"));
        ime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String o, String n) {
                if (validnoIme(n)) {
                    ime.getStyleClass().removeAll("invalidField");
                    ime.getStyleClass().add("validField");
                } else {
                    ime.getStyleClass().removeAll("validField");
                    ime.getStyleClass().add("invalidField");
                }
            }
        });
        prezime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String o, String n) {
                if (validnoPrezime(n)) {
                    prezime.getStyleClass().removeAll("invalidField");
                    prezime.getStyleClass().add("validField");
                } else {
                    prezime.getStyleClass().removeAll("validField");
                    prezime.getStyleClass().add("invalidField");
                }
            }
        });
        kontaktAdresa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String o, String n) {
                if (validnaAdresa(n)) {
                    kontaktAdresa.getStyleClass().removeAll("invalidField");
                    kontaktAdresa.getStyleClass().add("validField");
                } else {
                    kontaktAdresa.getStyleClass().removeAll("validField");
                    kontaktAdresa.getStyleClass().add("invalidField");
                }
            }
        });
        postanskiBroj.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    new Thread(() -> validacijaPostanskog(postanskiBroj.getText())).start();
                }
            }
        });
    }

    public void potvrdaSlanja(ActionEvent actionEvent) {
        if (!validnoIme(ime.getText()) || !validnoPrezime(prezime.getText())
                || !validnaAdresa(kontaktAdresa.getText()) || !validanPostanski)
            System.out.println("INVALID");
        else
            System.out.println("VALID");
    }

    private void validacijaPostanskog(String n) {
        try {
            BufferedReader ulaz = new BufferedReader
                    (new InputStreamReader(new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj="
                            + postanskiBroj.getText()).openStream(), StandardCharsets.UTF_8));
            String procitano = "", line = null;
            while ((line = ulaz.readLine()) != null) procitano = procitano + line;
            if (procitano.contains("NOT")) {
                Platform.runLater(() -> {
                    postanskiBroj.getStyleClass().removeAll("validField");
                    postanskiBroj.getStyleClass().add("invalidField");
                    validanPostanski = false;
                });
                Thread.sleep(180);
            }
            else {
                Platform.runLater(() -> {
                    postanskiBroj.getStyleClass().removeAll("invalidField");
                    postanskiBroj.getStyleClass().add("validField");
                    validanPostanski = true;
                });
                Thread.sleep(180);
            }
            System.out.println(procitano);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validnoIme(String n) {
        if (n.trim().equals("")) return false;
        if (n.length() > 20) return false;
        if (!(n.charAt(0) >= 65 && n.charAt(0) <= 90)) return false;
        for (int i = 1; i < n.length(); i++){
            if (!(n.charAt(i) >= 97 && n.charAt(i) <= 122)) return false;
        }
        return true;
    }
    private boolean validnoPrezime(String n) {
        if (n.trim().equals("")) return false;
        if (n.length() > 20) return false;
        if (!(n.charAt(0) >= 65 && n.charAt(0) <= 90)) return false;
        for (int i = 1; i < n.length(); i++){
            if (!(n.charAt(i) >= 97 && n.charAt(i) <= 122)) return false;
        }
        return true;
    }
    private boolean validnaAdresa(String n) {
        if (n.trim().equals("")) return true;
        if (!(n.charAt(0) >= 65 && n.charAt(0) <= 90)) return false;
        if (!Character.isLetter(n.charAt(0))) return false;
        return true;
    }
}
