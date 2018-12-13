package ba.unsa.etf.rpr.tutorijal08;

import com.sun.javafx.scene.control.skin.ChoiceBoxSkin;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProzorZaSlanjeController implements Initializable {

    public TextField ime;
    public TextField prezime;
    public Button potvrdjivanje;
    public TextField kontaktAdresa;
    public TextField postanskiBroj;
    public ComboBox grad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }

    public void potvrdaSlanja(ActionEvent actionEvent) {
        if (validnoIme(ime.getText()) && validnoPrezime(prezime.getText()) && validanEMail(eMail.getText())
                && validanIndex(brojIndexa.getText()) && validanTelefonskiBroj(kontaktTelefon.getText())
                && validanJMBG(jmbg.getText())) {
            System.out.println("Informacije o upisanom studentu:");
            System.out.println("Ime i prezime: " + ime.getText() + prezime.getText());
            System.out.println("Index: " + brojIndexa.getText());
            System.out.println("JMBG: " + jmbg.getText());
            System.out.println("Datum rodjenja: " + datumRodjenja.getValue());
            System.out.println("Grad rodjenja: " + mjestoRodjenja.getSelectionModel().getSelectedItem());
            System.out.println("Adresa: " + kontaktAdresa.getText());
            System.out.println("Telefon: " + kontaktTelefon.getText());
            System.out.println("email: " + eMail.getText());
            System.out.println("Odsjek: " + odsjek.getSelectionModel().getSelectedItem());
            System.out.println("Godina: " + godinaStudija.getSelectionModel().getSelectedItem());
            System.out.println("Ciklus: " + ciklusStudija.getSelectionModel().getSelectedItem());
            if (redovan.isSelected()) System.out.println("Status: redovni");
            else System.out.println("Status: redovni samofinansirajuci");
            if (borackaKategorija.isSelected()) System.out.println("Pripada borackim kategorijama.");
            else System.out.println("Ne pripada borackim kategorijama.");
        }
        else {
            prikazProzoraZaGresku(actionEvent);
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
        if (!Character.isLetter(n.charAt(0))) return false;
        return true;
    }
}
