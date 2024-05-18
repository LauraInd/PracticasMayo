package com.sanvalero.servlet;

import com.sanvalero.dao.Database;
import com.sanvalero.dao.ProductDao;
import com.sanvalero.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String nombre = request.getParameter("nombre");
        float precio = Float.parseFloat(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String supplierId = request.getParameter("idProveedor");

        Database database = new Database();
        ProductDao productDao = new ProductDao(database.getConnection());
        try {
            Product product = new Product(nombre.trim(), precio, stock, supplierId.trim());
            productDao.add(product);
            out.println("<div class='alert alert-success' role='alert'>Producto añadido correctamente \n <a href='showproducts.jsp' >Listado Productos</a>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-success' role='alert'>No se ha podido conectar con la base de datos. Verifique que todos los datos son correctos.</div>");
            sqle.printStackTrace();

        }
    }
}
