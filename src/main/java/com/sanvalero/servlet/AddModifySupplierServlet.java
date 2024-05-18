package com.sanvalero.servlet;

import com.sanvalero.dao.SupplierDao;
import com.sanvalero.domain.Supplier;
import com.sanvalero.exception.SupplierAlreadyExistException;
import com.sanvalero.dao.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-modify-supplier")
public class AddModifySupplierServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        String nombre = request.getParameter("nombre");
        String cif = request.getParameter("cif");
        String phone = request.getParameter("telefono");
        String email = request.getParameter("email");
        String action = request.getParameter("action");
        String supplierId = request.getParameter("supplierId");
        Supplier supplier = new Supplier(nombre, cif, phone, email); //creo el proveedor

        Database database = new Database();
        SupplierDao supplierDao = new SupplierDao(database.getConnection());
        try {
            if (action.equals("register")) {
                supplierDao.addSupplier(supplier);
                out.println("<div class='alert alert-success' role='alert'>El proveedor se ha añadido correctamente \n <a href='showsuppliers.jsp' >Listado Proveedores</a>");
            } else {
                supplierDao.modifyById(Integer.parseInt(supplierId), supplier);
                out.println("<div class='alert alert-success' role='alert'>El proveedor se ha modificado correctamente \n <a href='showsuppliers.jsp' >Listado Proveedores</a>");
            }
        } catch (SupplierAlreadyExistException saee) {
            out.println("<div class='alert alert-warning' role='alert'>El proveedor ya está registrado en el sistema. \n <a href='showsuppliers.jsp' >Listado Proveedores</a>");
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>No se ha podido conectar con la base de datos. Verifique que todos los datos son correctos.</div>");
            sqle.printStackTrace(); //TODO QUITAR DE LA VERSION FINAL
        }

    }
}
