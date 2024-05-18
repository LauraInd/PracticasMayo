<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"


         import="com.sanvalero.dao.Database"
         import="com.sanvalero.dao.ClientDao"
         import="java.util.List"
         import="java.sql.SQLException"
%>
<%@ page import="com.sanvalero.dao.ProductDao" %>
<%@ page import="com.sanvalero.domain.Product" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("AccesoDenegado.jsp");
    }
%>
<html lang="es">
<head>
    <!-- Para usar la hoja de estilos de  Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Listado de Productos</title>
</head>
<body>
    <div class="container">
        <h2>Listado de Producto</h2>
        <div class="alert alert-secondary" role="alert">
            Selecciona el producto que quieres consultar.
        </div>
            <ul class="list-group">
                <%
                    // Acceder a la base de datos y recuperar la información de los clientes
                    Database database = new Database();
                    ProductDao productDao = new ProductDao(database.getConnection());
                    try {
                        List<Product> products = productDao.findAllProduct();
                        for (Product product : products) {
                %>
                            <li class="list-group-item">
                            <a target="_blank" href="product.jsp?idProduct=<%= product.getIdProduct() %>"><%= product.getName() %></a>
                            </li>
                <%
                    }
                } catch (SQLException sqle) {

                %>
                <div class="alert alert-danger" role="alert">
                    <h6>Error de conexión con la base da datos.</h6>
                </div>
                <%
                        sqle.printStackTrace();
                    }
                %>
            </ul>
        <a href="index.jsp" class="btn btn-primary">Menú Principal</a>
    </div>
</body>
</html>
