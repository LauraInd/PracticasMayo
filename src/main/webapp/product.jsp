<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"

%>
<%@ page import="com.sanvalero.dao.Database" %>
<%@ page import="com.sanvalero.dao.ProductDao" %>
<%@ page import="com.sanvalero.domain.Product" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("AccesoDenegado.jsp");
    }
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Información Producto</title>
</head>
<body>
    <%
        String productoId = request.getParameter("idProduct");
        Database database = new Database();
        ProductDao productDao = new ProductDao((database.getConnection()));
        Product product = null;
        try {
            Optional<Product> optionalProduct = productDao.findById(Integer.parseInt(productoId));
            product = optionalProduct.get();
    %>
    <div class="container">
        <div class="card text-center">
            <div class="card-header">Información Producto</div>
            <div class="card-body">
                <h5 class="card-title"><%= product.getName() %></h5>
                <h6 class="card-subtitle mb-2 text-muted">Precio: <%= product.getPrice() + " €" %></h6>
                <h6 class="card-subtitle mb-2 text-muted">Stock: <%= product.getStock() + " unidades" %></h6>
                <a href="deleteproduct.jsp?idProduct=<%= product.getIdProduct() %>" class="btn btn-outline-danger">Eliminar</a>
            </div>
            <a href="showproducts.jsp" class="btn btn-primary">Volver</a>
        </div>
    </div>
        <%
        } catch (SQLException sqle) {
        %>
    <div class='alert alert-danger' role='alert'>No se ha podido conectar con la base de datos. Verifique que todos los datos son correctos.</div>
        <%
            sqle.printStackTrace();}
        %>
</body>
</html>