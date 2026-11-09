/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Riyan
 */
public class DaftarProduk {
    private ArrayList<Produk> produks = new ArrayList<>();
    
    public void tambahProduk(Produk produk) {
        produks.add(produk);
    }
    
    public void hapusProduk(Produk produk) {
        produks.remove(produk);
    }
    
    public void updateProduk(Produk produkLama, Produk produkBaru) {
     int index = produks.indexOf(produkLama);
     if (index != -1) {
         produks.set(index, produkBaru);
     }
    }
    
    public ArrayList<Produk> getAllProduk() {
        return produks;
    }
    
    public Produk getProduk(String kode) {
        for (Produk produk : produks) {
            if (produk.getKode().equals(kode)) return produk;
        }
        
        return null;
    }
    
    public int getJumlahProduk() {
        return produks.size();
    }
}
