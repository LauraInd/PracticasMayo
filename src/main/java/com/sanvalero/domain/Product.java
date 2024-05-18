package com.sanvalero.domain;

public class Product {

        private int idProduct;
        private String name;
        private float price;
        private int stock;
        private String idSupplier;

        private Supplier supplier; //para relacionar con Supplier


        public Product() {
        }

        public Product(String name, float price, int stock, String idSupplier) {
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.idSupplier = idSupplier;

        }

        public int getIdProduct() {
            return idProduct;
        }

        public void setIdProduct(int idProduct) {
            this.idProduct = idProduct;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getIdSupplier() {
            return idSupplier;
        }

        public void setIdSupplier(String idSupplier) {
            this.idSupplier = idSupplier;
        }

        public Supplier getSupplier() {
            return supplier;
        }

        public void setSupplier(Supplier supplier) {
            this.supplier = supplier;
        }

}
