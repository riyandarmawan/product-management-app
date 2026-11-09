/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.produk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Navigator;
import model.DaftarProduk;
import model.Produk;

/**
 * FXML Controller class
 *
 * @author Riyan
 */
public class DaftarController implements Initializable {

    private Navigator navigator;

    DaftarProduk daftarProduk;

    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Produk> daftarProdukTable;
    @FXML
    private TableColumn<Produk, String> kodeColumn;
    @FXML
    private TableColumn<Produk, String> namaColumn;
    @FXML
    private TableColumn<Produk, String> kategoriColumn;
    @FXML
    private TableColumn<Produk, Double> hargaColumn;
    @FXML
    private TextField cariTextField;
    @FXML
    private ComboBox<String> kategoriFilterComboBox;
    @FXML
    private Button detailButton;
    @FXML
    private Button hapusButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kodeColumn.setCellValueFactory(new PropertyValueFactory<>("kode"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        kategoriColumn.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));

        kategoriFilterComboBox.getItems().addAll("Semua kategori", "Makanan", "Minuman");

        kategoriFilterComboBox.setValue("Semua kategori");

        if (errorLabel != null) {
            errorLabel.setText("");
        }

        if (detailButton != null && hapusButton != null) {
            detailButton.disableProperty().bind(daftarProdukTable.getSelectionModel().selectedItemProperty().isNull());
            hapusButton.disableProperty().bind(daftarProdukTable.getSelectionModel().selectedItemProperty().isNull());
        }
    }

    public void setContext(Navigator navigator, DaftarProduk daftarProduk) {
        this.navigator = navigator;
        this.daftarProduk = daftarProduk;

        if (daftarProdukTable != null && daftarProduk != null) {
            daftarProdukTable.setItems(FXCollections.observableArrayList(daftarProduk.getAllProduk()));
        }
    }

    @FXML
    private void cariButtonClicked(ActionEvent event) {
        String keyword = cariTextField.getText().trim().toLowerCase();
        String kategoriFilter = kategoriFilterComboBox.getValue();

        javafx.collections.ObservableList<Produk> hasilFilter = FXCollections.observableArrayList();

        for (Produk produk : daftarProduk.getAllProduk()) {
            boolean matchesText = keyword.isEmpty()
                    || produk.getKode().toLowerCase().contains(keyword)
                    || produk.getNama().toLowerCase().contains(keyword)
                    || String.valueOf(produk.getHarga()).contains(keyword);

            boolean matchesCategory = kategoriFilter == null
                    || kategoriFilter.equalsIgnoreCase("Semua kategori")
                    || produk.getKategori().equalsIgnoreCase(kategoriFilter);

            if (matchesText && matchesCategory) {
                hasilFilter.add(produk);
            }
        }

        daftarProdukTable.setItems(hasilFilter);
    }

    @FXML
    private void tambahButtonClicked(ActionEvent event) {
        try {
            this.navigator.bukaTambahForm();
        } catch (IOException ex) {
            errorLabel.setText("Gagal membuka halaman tambah.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void detailButtonClicked(ActionEvent event) {
        Produk produk = this.daftarProdukTable.getSelectionModel().getSelectedItem();

        if (produk == null) {
            errorLabel.setText("Silahkan pilih produk terlebih dahulu untuk dapat melihat detail produk!");
            return;
        }

        try {
            this.navigator.bukaDetailProduk(produk);
        } catch (IOException ex) {
            errorLabel.setText("Gagal membuka halaman detail.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void hapusButtonClicked(ActionEvent event) {
        Produk produk = this.daftarProdukTable.getSelectionModel().getSelectedItem();

        if (produk == null) {
            errorLabel.setText("Silahkan pilih produk yang ingin dihapus terlebih dahulu!");
            return;
        }

        daftarProduk.hapusProduk(produk);

        errorLabel.setText("");

        daftarProdukTable.setItems(FXCollections.observableArrayList(daftarProduk.getAllProduk()));
    }

    @FXML
    private void resetButtonClicked(ActionEvent event) {
        cariTextField.setText("");
        kategoriFilterComboBox.setValue("Semua kategori");

        daftarProdukTable.setItems(FXCollections.observableArrayList(daftarProduk.getAllProduk()));
    }

}
