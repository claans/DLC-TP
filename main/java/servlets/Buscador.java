/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(name = "Buscador", urlPatterns = {"/buscar"})
public class Buscador extends HttpServlet {


/*    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logica.indexeadorController id = (logica.indexeadorController) request.getSession().getAttribute("indexador");
       /* Part fileName = request.getPart("file");

        if (fileName != null) {
            String path = fileName.getSubmittedFileName();

            File file = new File(path);

            request.getSession().setAttribute("file", file);

            id.readFile(file);
        }
        response.sendRedirect("index.jsp");*/
       /*
       File file = new File("C:\\Users\\Mu_Cl\\Desktop\\Imanol\\Facultad\\DLS 2021\\Archivos\\00ws110.txt");

       id.readFile(file);
    
        -- 
       
       
    }*/
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = "C:\\DocumentosTP1\\";

        File dir = new File( url );

        // Robado de internet :P
        String[ ] archivos = dir.list( new Filtro( ".txt" ) );

        controllers.indexController id = (controllers.indexController) request.getSession().getAttribute("indexador");

        boolean hola = id.leerArchivos( archivos, url );
        
        if( hola == true ) {
            hola = false;
        }

           /* new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String a : archivos) {
                        logica.indexeadorController in;
                        in = new logica.indexeadorController(url+a);
                        System.out.println(in.indexar());                        

                    }
                }

            }).start();
            out.print("procesando");*/
   
     }
     
     private class Filtro implements FilenameFilter {

        String extension;

        Filtro(String extension) {
            this.extension = extension;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(extension);
        }
    }
    

}
