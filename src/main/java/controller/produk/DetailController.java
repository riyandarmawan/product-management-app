/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.produk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import main.Navigator;
import model.DaftarProduk;
import model.Produk;

/**
 * FXML Controller class
 *
 * @author Riyan
 */
public class DetailController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private TextField kodeProdukTextField;
    @FXML
    private TextField namaProdukTextField;
    @FXML
    private TextField hargaProdukTextField;
    @FXML
    private ComboBox<String> kategoriComboBox;
    @FXML
    private Label errorLabel;

    private Produk produk;

    private Navigator navigor;
    private DaftarProduk DaftarProduk;
    private String selectedImagePath = "";

    @FXML
    private Button resetButton;
    @FXML
    private Button simpanButton;
    @FXML
    private ImageView gambarProdukImageView;
    @FXML
    private Button editButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        if (errorLabel != null) {
            errorLabel.setText("");
        }

        if (kategoriComboBox != null) {
            kategoriComboBox.getItems().addAll("Makanan", "Minuman");
            kategoriComboBox.setValue("Makanan");
        }

        if (gambarProdukImageView != null) {
            gambarProdukImageView.setOnMouseClicked(event -> pilihGambarAction());
        }
    }

    public void setData(Navigator navigator, DaftarProduk daftarProduk, Produk produk) {
        this.navigor = navigator;
        this.DaftarProduk = daftarProduk;
        this.produk = produk;

        kodeProdukTextField.setText(produk.getKode());
        namaProdukTextField.setText(produk.getNama());
        hargaProdukTextField.setText(String.valueOf(produk.getHarga()));
        kategoriComboBox.setValue(produk.getKategori());

        this.selectedImagePath = produk.getGambarPath();
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            javafx.scene.image.Image image = new javafx.scene.image.Image(selectedImagePath);
            gambarProdukImageView.setImage(image);
        } else {
            gambarProdukImageView.setImage(null);
        }
    }

    @FXML
    private void backButtonClicked(ActionEvent event) {
        try {
            navigor.bukaDaftarProduk();
        } catch (IOException ex) {
            errorLabel.setText("Gagal membuka halaman daftar.");
            ex.printStackTrace();
        }
    }

    private void pilihGambarAction() {
        if (gambarProdukImageView.isDisable()) {
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih gambar produk");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images files", "*.png", "*.jpg", "*.jpeg")
        );

        java.io.File selectedFile = fileChooser.showOpenDialog(gambarProdukImageView.getScene().getWindow());

        if (selectedFile != null) {
            selectedImagePath = selectedFile.toURI().toString();

            javafx.scene.image.Image image = new javafx.scene.image.Image(selectedImagePath);
            gambarProdukImageView.setImage(image);
        }
    }

    @FXML
    private void simpanButtonClicked(ActionEvent event) {
        String kodeBaru = kodeProdukTextField.getText();
        String namaBaru = namaProdukTextField.getText();
        String kategoriBaru = kategoriComboBox.getValue();
        String hargaRaw = hargaProdukTextField.getText();

        if (kodeBaru.isEmpty() || namaBaru.isEmpty() || hargaRaw.isEmpty()) {
            errorLabel.setText("Semua field teks harus diisi!");
            return;
        }

        double harga = 0;
        try {
            harga = Double.parseDouble(hargaRaw);
            if (harga <= 0) {
                errorLabel.setText("Harga harus lebih dari 0!");
                return;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Harga harus berupa angka valid!");
            return;
        }

        Produk produkBaru = new Produk(kodeBaru, namaBaru, kategoriBaru, harga, selectedImagePath);

        this.DaftarProduk.updateProduk(produk, produkBaru);

        try {
            navigor.bukaDaftarProduk();
        } catch (IOException ex) {
            errorLabel.setText("Gagal memuat ulang halaman tabel daftar produk.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void editButtonClicked(ActionEvent event) {
        namaProdukTextField.setDisable(false);
        kategoriComboBox.setDisable(false);
        hargaProdukTextField.setDisable(false);
        resetButton.setDisable(false);
        simpanButton.setDisable(false);
        gambarProdukImageView.setDisable(false);
    }

    @FXML
    private void resetButtonClicked(ActionEvent event) {
        kodeProdukTextField.setDisable(true);
        namaProdukTextField.setDisable(true);
        kategoriComboBox.setDisable(true);
        hargaProdukTextField.setDisable(true);
        resetButton.setDisable(true);
        simpanButton.setDisable(true);
        gambarProdukImageView.setDisable(true);

        kodeProdukTextField.setText(produk.getKode());
        namaProdukTextField.setText(produk.getNama());
        kategoriComboBox.setValue(produk.getKategori());
        hargaProdukTextField.setText(String.valueOf(produk.getHarga()));
    }

}
