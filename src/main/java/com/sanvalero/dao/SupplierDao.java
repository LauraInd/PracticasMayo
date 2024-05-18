package com.sanvalero.dao;

import com.sanvalero.domain.Supplier;
import com.sanvalero.exception.SupplierAlreadyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SupplierDao {

    private Connection connection;

    //Constructor para conectar a la BBDD
    public SupplierDao(Connection connection) {
        this.connection = connection;
    }

    //Metodo añadir proveedor
    public void addSupplier (Supplier supplier) throws SQLException, SupplierAlreadyExistException {
        //comprobación si existe el proveedor.
        if(existSupplier(supplier.getCif()))
            throw new SupplierAlreadyExistException();

        String sql = "INSERT INTO PROVEEDORES (NOMBRE, CIF, TELEFONO, EMAIL) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getCif());
        statement.setString(3, supplier.getPhone());
        statement.setString(4, supplier.getEmail());
        statement.executeUpdate();
    }

    //Metodo modificar el proveedor sabiendo su id
    public  boolean modifyById(int id, Supplier supplier) throws SQLException {
        String sql = "UPDATE PROVEEDORES SET NOMBRE = ?, CIF = ?, TELEFONO = ?, EMAIL = ? WHERE ID_PROVEEDOR = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getCif());
        statement.setString(3, supplier.getPhone());
        statement.setString(4, supplier.getEmail());
        statement.setInt(5, id);
        //nº filas modificadas
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    //Metodo modificar proveedor (que cif quiero modificar y que nuevos datos quiero). No usado en la AA
    public  boolean modifySupplier(String cif, Supplier supplier) throws SQLException {
        String sql = "UPDATE PROVEEDORES SET NOMBRE = ?, CIF = ?, TELEFONO = ?, EMAIL = ? WHERE CIF = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getCif());
        statement.setString(3, supplier.getPhone());
        statement.setString(4, supplier.getEmail());
        statement.setString(5, cif);
        //nº filas modificadas
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    //Metodo eliminar el proveedor sabiendo su cif.
    public boolean deleteSupplier(String cif) throws SQLException {
        String sql = "DELETE FROM PROVEEDORES WHERE CIF = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cif);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    //Metodo eliminar el proveedor sabiendo su id.
    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM PROVEEDORES WHERE ID_PROVEEDOR = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    //Metodo para buscar y listar los proveedores resultantes de la busqueda del texto indicado por el usuario. Se buscará el todas las columnas.
    public ArrayList<Supplier> findAllSupplier(String searchText) throws SQLException {
        String sql = "SELECT * FROM PROVEEDORES WHERE INSTR(NOMBRE, ?) != 0 OR INSTR(CIF, ?) != 0 OR INSTR(TELEFONO, ?) != 0 OR INSTR(EMAIL, ?) != 0 ORDER BY nombre";
        ArrayList<Supplier> suppliers = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        statement.setString(3, searchText);
        statement.setString(4, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Supplier supplier = fromResultSet(resultSet);
            suppliers.add(supplier);
        }

        return suppliers;
    }

    //Metodo para buscar y listar todos los proveedores
    public ArrayList<Supplier> findAll() throws SQLException{
        String sql = "SELECT * FROM PROVEEDORES";
        ArrayList<Supplier> suppliers = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultset = statement.executeQuery();
        while (resultset.next()) {
            Supplier supplier = fromResultSet(resultset);
            suppliers.add(supplier);
        }
        statement.close();

        return suppliers;
    }

    //Método para buscar por CIF
    public Optional<Supplier> findByCif(String cif) throws SQLException {
        String sql = "SELECT * FROM PROVEEDORES WHERE CIF = ?";
        Supplier supplier = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,cif);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            supplier = new Supplier();
            supplier.setId(Integer.parseInt(resultSet.getString("ID_PROVEEDOR")));
            supplier.setName(resultSet.getString("NOMBRE"));
            supplier.setCif(resultSet.getString("CIF"));
            supplier.setPhone(resultSet.getString("TELEFONO"));
            supplier.setEmail(resultSet.getString("EMAIL"));
        }

        return  Optional.ofNullable(supplier);
    }

    //Método para buscar por id.
    public Optional<Supplier> findById(int id) throws SQLException {
        String sql = "SELECT * FROM PROVEEDORES WHERE ID_PROVEEDOR = ?";
        Supplier supplier = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            supplier = fromResultSet(resultSet);
        }

        return  Optional.ofNullable(supplier);
    }

    //Metodo para determinar si el proveedor ya existe en base a su CIF.
    private boolean existSupplier(String cif) throws SQLException { //private porque es para uso interno
        Optional<Supplier> supplier = findByCif(cif);
        return supplier.isPresent();
    }

    //Metodo para usar en los listados que devuelven ResultSet
    private Supplier fromResultSet(ResultSet resultSet) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setId(resultSet.getInt("id_proveedor"));
        supplier.setName(resultSet.getString("nombre"));
        supplier.setCif(resultSet.getString("cif"));
        supplier.setPhone(resultSet.getString("telefono"));
        supplier.setEmail(resultSet.getString("email"));
        return supplier;
    }

}