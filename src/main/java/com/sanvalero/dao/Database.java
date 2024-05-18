package com.sanvalero.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    //Constructor para conectar a la BBDD
    private Connection connection;

    public Connection getConnection() {
        try {
            Class.forName(ORACLE_DRIVER);
            connection = DriverManager.getConnection(ORACLE_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("No se ha podido cargar el driver de conexión. Verifique que todo esta correcto.");
            cnfe.printStackTrace(); //me dice que linea de codigo ha fallado. Se recomienda quitarlo de la versión final
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con la bases de datos. Verifique que todos los datos con correctos.");
            sqle.printStackTrace();
        }

        return connection;
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con la base de dato. Verifique que todos los datos con correctos.");
            sqle.printStackTrace();
        }
    }
}
