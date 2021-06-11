

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    if(session.getAttribute("indexador") == null){
        controllers.indexController id = new controllers.indexController();
        session.setAttribute("indexador", id);
    }
    
    
    if(session.getAttribute("file")!=null){
        out.print("<h2> ARCHIVO CARGADO </h2>");
    }

%>


<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscador</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

    <nav>
        <form action="buscar" method="POST" id="form_files" enctype="multipart/form-data">
            <div class="div_file">
                <input type="file" name="file" id="cargar_archivos" accept="txt" multiple="true">
                <input type="submit" value="Enviar">
            </div>
        </form>
        <p id="archivos_cargados">Hay <%= controllers.indexController.countFile()%> archivos cargados ASD</p>
    </nav>

    <div class="contenedor">

        <header>
            <h1 id="titulo"><span id="bus">BUS</span><span id="ca">CA</span><span id="dor">DOR</span></h1>
        </header>

        <form action="" method="GET">
            <div class="buscador">
                <input type="search" name="q">
            </div>
            <input type="submit" value="Buscar">
        </form>
    </div>
    <footer>
        <p>Trabajo practico de DLC</p>
    </footer>

</body>

</html>