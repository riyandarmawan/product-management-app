/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controller.produk.DaftarController;
import controller.produk.DetailController;
import controller.produk.TambahController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DaftarProduk;
import model.Produk;

/**
 *
 * @author Riyan
 */
public class Navigator {
    private final Stage stage;
    private final DaftarProduk daftarProduk;
    private Scene scene;

    public Navigator(Stage stage, DaftarProduk daftarProduk) {
        this.stage = stage;
        this.daftarProduk = daftarProduk;
    }
    
    private FXMLLoader loader (String nama) {
        return new FXMLLoader(getClass().getResource("/view/produk/" + nama + ".fxml"));
    }
    
    private void tampil(Parent root) {
        if (scene == null) {
            scene = new Scene(root, 800, 480);
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Toko");
            stage.setMinWidth(640);
            stage.setMinHeight(480);
        } else {
            scene.setRoot(root);
        }
        stage.show();
    }
    
    public void bukaTambahForm() throws IOException {
        FXMLLoader l = loader("tambah");
        Parent root = l.load();
        TambahController tambahController = l.getController();
        tambahController.setContext(this, daftarProduk);
        tampil(root);
    }
    
    public void bukaDaftarProduk() throws IOException {
        FXMLLoader l = loader("daftar");
        Parent root = l.load();
        DaftarController daftarController = l.getController();
        daftarController.setContext(this, daftarProduk);
        tampil(root);
    }
    
    public void bukaDetailProduk(Produk produk) throws IOException {
        FXMLLoader l = loader("detail");
        Parent root = l.load();
        DetailController detailController = l.getController();
        detailController.setData(this, daftarProduk, produk);
        tampil(root);
    }
}
