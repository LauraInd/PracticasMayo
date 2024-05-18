<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"

    import="com.svalero.cesped.domain.User"
%>
<%@ page import="java.util.Objects"
%>

<!-- Recuperamos la sesion y si es null lo llevamos a login.jsp -->
<%
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
        }
%>
<html lang="es">
<head>
     <!-- Para usar la hoja de estilos de  Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Aplicación Sanfrei</title>
</head>
<body>
    <div class="container">
        <h2>APLICACIÓN SANFREI</h2>
        <div class="alert alert-success" role="alert">
            Bienvenido <% if (currentUser != null) out.print(currentUser.getName()); %>! </br> Aqui podrás gestionar clientes, proveedores y productos de la empresa.
        </div>

        <ul>
        <li><a href="/sanfrei/showclients.jsp">Listado de Clientes</a></li>
        <li><a href="/sanfrei/showsuppliers.jsp">Listado de Proveedores</a></li>
        <li><a href="/sanfrei/showproducts.jsp">Listado de Productos</a></li>
        <li><a href="/sanfrei/searchclient.jsp">Buscar Clientes</a></li>
        <li><a href="/sanfrei/searchsupplier.jsp">Buscar Proveedores</a></li>
        <li><a href="/sanfrei/searchproduct.jsp">Buscar Productos</a></li>
        </ul>

        <%
            if ((currentUser != null) && Objects.equals(currentUser.getRole(), "admin")) {
        %>
            <ul>
                <li><a href="/sanfrei/addclient.jsp">Añadir Cliente</a></li>
                <li><a href="/sanfrei/addsupplier.jsp">Añadir Proveedor</a></li>
                <li><a href="/sanfrei/addproduct.jsp">Añadir un Producto</a></li>
            </ul>
        <%
             }
             if (currentUser != null) {
        %>
            <ul>
                <li><a href="/sanfrei/logout">Cerrar sesión</a></li>
            </ul>
        <%
             }
        %>
        <br/>
    </div>
</body>
</html>
