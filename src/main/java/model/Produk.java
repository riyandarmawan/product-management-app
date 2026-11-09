    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package model;

    /**
     *
     * @author Riyan
     */
    public class Produk {

    public String getGambarPath() {
        return gambarPath;
    }

        private String kode, nama, kategori, gambarPath;
        double harga;

        public Produk(String kode, String nama, String kategori, double harga, String gambarPath) {
            this.kode = kode;
            this.nama = nama;
            this.kategori = kategori;
            this.harga = harga;
            this.gambarPath = gambarPath;
        }

        public String getKode() {
            return kode;
        }

        public String getNama() {
            return nama;
        }

        public String getKategori() {
            return kategori;
        }

        public double getHarga() {
            return harga;
        }

    }
