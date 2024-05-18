package com.sanvalero.servlet;

import com.sanvalero.dao.Database;
import com.sanvalero.dao.ProductDao;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String productId = request.getParameter("idProduct");

        Database database = new Database();
        ProductDao productDao = new ProductDao(database.getConnection());
        try {
            productDao.deleteById(Integer.parseInt(productId));
            out.println("<div class='alert alert-success role='alert'>El producto se ha borrado correctamente</div> \n <a href='showproducts.jsp' class='btn btn-primary'>Listado Productos</a>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha podido conectar con la base de datos. Verifique que todos los datos son correctos.</div>");
            sqle.printStackTrace(); //TODO QUITAR DE LA VERSION FINAL
        }
    }
}


