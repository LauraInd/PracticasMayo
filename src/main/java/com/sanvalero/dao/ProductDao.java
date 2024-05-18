package com.sanvalero.dao;

import com.sanvalero.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ProductDao {

    private Connection connection;

    //Constructor para conectar a la BBDD
    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    //Metodo para añadir Productos
    public void add(Product product) throws SQLException {
        String sql = "INSERT INTO PRODUCTOS (NOMBRE, PRECIO, STOCK, ID_PROVEEDOR) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, product.getName());
        statement.setFloat(2, product.getPrice());
        statement.setInt(3, product.getStock());
        statement.setString(4, product.getIdSupplier());
        statement.executeUpdate();
    }

    //Metodo para eliminar Productos sabiendo el id.
    public boolean deleteById(int idProduct) throws SQLException {
        String sql = "DELETE FROM PRODUCTOS WHERE ID_PRODUCTO = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idProduct);
        int rows = statement.executeUpdate();

        return rows == 1;
    }

    //Metodo para buscar y listar todos los productos
    public ArrayList<Product> findAllProduct() throws SQLException {
        String sql = "SELECT * FROM PRODUCTOS ORDER BY NOMBRE";
        ArrayList<Product> products = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Product product = fromResultSet(resultSet);
            products.add(product);
        }

        return products;
    }

    //Metodo para buscar y listar producto en función del texto que indique el usuario. Se buscará el todas las columnas.
    public ArrayList<Product> findAllProduct(String searchText) throws SQLException {
        String sql = "SELECT * FROM PRODUCTOS INSTR(NOMBRE, ?) != 0 OR INSTR(PRECIO, ?) != 0 OR INSTR(STOCK, ?) != 0 OR INSTR(ID_PROVEEDOR, ?) != 0 ORDER BY nombre";
        ArrayList<Product> products = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        statement.setString(3, searchText);
        statement.setString(4, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Product product = fromResultSet(resultSet);
            products.add(product);
        }
        statement.close();

        return products;
    }

    //Método para buscar por Nombre
    public Optional<Product> findByName(String name) throws SQLException {
        String sql = "SLECT * FROM PRODUCTOS WHERE NOMBRE = ?";
        Product product = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            product = new Product();
            product.setIdProduct(resultSet.getInt("ID_PRODUCTO"));
            product.setName(resultSet.getString("NOMBRE"));
            product.setPrice(resultSet.getFloat("PRECIO"));
            product.setStock(resultSet.getInt("STOCK"));
        }

        return Optional.ofNullable(product);
    }

    //Método para buscar por id.
    public Optional<Product> findById(int idProduct) throws SQLException {
        String sql = "SELECT * FROM PRODUCTOS WHERE ID_PRODUCTO = ?";
        Product product = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idProduct);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            product = new Product();
            product.setIdProduct(resultSet.getInt("ID_PRODUCTO"));
            product.setName(resultSet.getString("NOMBRE"));
            product.setPrice(resultSet.getFloat("PRECIO"));
            product.setStock(resultSet.getInt("STOCK"));
            product.setIdSupplier(resultSet.getString("ID_PROVEEDOR"));
        }

        return Optional.ofNullable(product);
    }

    //Metodo para usar en los listados que devuelven ResultSet
    private Product fromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setIdProduct(resultSet.getInt("id_producto"));
        product.setName(resultSet.getString("nombre"));
        product.setPrice(resultSet.getFloat("precio"));
        product.setStock(resultSet.getInt("stock"));
        product.setIdSupplier(resultSet.getString("id_proveedor"));
        return product;
    }
}

