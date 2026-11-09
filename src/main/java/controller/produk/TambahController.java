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
public class TambahController implements Initializable {

    private Navigator navigator;
    private DaftarProduk daftarProduk;
    private String selectedImagePath = "";

    @FXML
    private Label errorLabel;
    @FXML
    private TextField kodeProdukTextField;
    @FXML
    private TextField namaProdukTextField;
    @FXML
    private TextField hargaProdukTextField;
    @FXML
    private ComboBox<String> kategoriComboBox;
    @FXML
    private Button backButton;
    @FXML
    private Button simpanButtonClicked;
    @FXML
    private ImageView gambarProdukImageView;

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

        if (kodeProdukTextField != null) {
            kodeProdukTextField.setEditable(false);
        }
    }

    public void setContext(Navigator navigator, DaftarProduk daftarProduk) {
        this.navigator = navigator;
        this.daftarProduk = daftarProduk;

        int urutan = daftarProduk.getJumlahProduk() + 1;
        String kodeOtomatis = String.format("PRD%03d", urutan);

        kodeProdukTextField.setText(kodeOtomatis);
    }

    private void pilihGambarAction() {
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
    private void backButtonClicked(ActionEvent event) {
        try {
            navigator.bukaDaftarProduk();
        } catch (IOException ex) {
            errorLabel.setText("Gagal membuka halaman daftar.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void simpanButtonClicked(ActionEvent event) {
        String kode = kodeProdukTextField.getText();
        String nama = namaProdukTextField.getText();
        String kategori = kategoriComboBox.getValue();
        String hargaRaw = hargaProdukTextField.getText();

        if (kode.isEmpty() || nama.isEmpty() || hargaRaw.isEmpty()) {
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

        Produk produk = new Produk(kode, nama, kategori, harga, selectedImagePath);

        this.daftarProduk.tambahProduk(produk);

        try {
            this.navigator.bukaDaftarProduk();
        } catch (IOException ex) {
            errorLabel.setText("Gagal membuka halaman daftar.");
            ex.printStackTrace();
        }
    }

}
