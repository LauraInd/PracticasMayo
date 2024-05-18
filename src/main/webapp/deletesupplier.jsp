<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"

%>
<%@ page import="com.sanvalero.dao.Database" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.sanvalero.dao.SupplierDao" %>
<%@ page import="com.sanvalero.domain.Supplier" %>

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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- libreria de ajax es como una libreria de js con cosas ya hechas -->
    <title>Eliminar Proveedor</title>
</head>
<body>
    <script type="text/javascript">
        $(document).ready(function () {
            $("form").on("submit", function (event) {
                event.preventDefault();
                var formValue = $(this).serialize();
                $.post("delete-supplier", formValue, function (data) { <!-- servlet que recibe todos los datos del formulario -->
                    $("#result").html(data); <!-- Lo usamos para enviar la respuesta al div en la misma página -->
                });
            });
        });
    </script>
    <%
        String supplierId = request.getParameter("id");
        Database database = new Database();
        SupplierDao supplierDao = new SupplierDao((database.getConnection()));
        Supplier supplier = null;
        try {
             Optional<Supplier> optionalSupplier = supplierDao.findById(Integer.parseInt(supplierId));
             supplier = optionalSupplier.get();
    %>
    <div class="container">
        <h2>Eliminar Proveedor</h2>
        <div class="card text-center">
            <div class="card-header">¿Estás seguro que quieres eliminar el proveedor?</div>
            <div class="card-body">
                <a href="delete-supplier?id=<%= supplier.getId() %>" class="btn btn-danger">Si</a>
                <a href="showsuppliers.jsp?id<%= supplier.getId() %>" class="btn btn-outline-danger">No</a>
            </div>
        </div>
    </div>
        <div id="result"></div>
    </div>
    <%
    } catch (SQLException sqle) {
    %>
    <div class='alert alert-danger' role='alert'>No se ha podido conectar con la base de datos. Verifique que todos los datos son correctos.</div>
    <%
        }
    %>
</body>
</html>