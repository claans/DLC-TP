package controllers;

import entities.Document;
import entities.Vocabulary;

import repositories.VocabularyRepository;
import repositories.DocumentRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class indexController {

    private HashMap<String, Vocabulary> map = new HashMap<>();
    private StringTokenizer tokens;

    public boolean leerArchivos(String[] archivos, String directorio) {
        
        Arrays.stream(archivos)
            .forEach(x -> indexar(x, directorio));
        
        // Una vez que terminamos de leer todos los documentos nos toca actualizar la DB de vocabulario con los NR
        VocabularyRepository.actualizarNr(map);
        
        return true;
    }
    
    private void indexar(String archivo, String directorio) {
        
        
        try {
            
            Document document = new entities.Document(archivo, directorio);
            
            File dir = new File( document.getPath() );
            
            //if (!datos.d_indexeador.consultarDocumento(dir.getName())) 
              //  return "Documento ya procesado";
            
            if (dir.exists()) {
                
                try (BufferedReader info = new BufferedReader(new InputStreamReader(new FileInputStream(new File( document.getPath() )), "ISO-8859-1"))) {
                    
                    String linea = info.readLine();
                    while (linea != null) {
                        
                        //Obtiene un token por cada palabra que encuentre
                        tokens = new StringTokenizer(linea);
                        
                        //Mientras existan tokens
                        while (tokens.hasMoreTokens()) {
                            
                            String palabra = tokens.nextToken().toUpperCase();
                            
                             //Expresion regular para eliminar todos los caracteres y solo dejar letras
                            Pattern exp = Pattern.compile("[^ÑÁÉÍÓÚA-Z]");
                            Matcher match = exp.matcher(palabra);
                            
                            if (match.find()) {
                                palabra = match.replaceAll("");
                            }
                            
                            if(palabra.length() > 1 && palabra.compareTo(" ")!=0){
                                
                                Vocabulary v;
                                
                                if (!map.containsKey(palabra)) {
                                    
                                    v = new Vocabulary( );
                                    map.put( palabra, v );
            
                                } else {
                                    v = map.get(palabra);
                                    v.setTf(v.getTf() + 1);
                                    map.put(palabra, v);
                                }
                            }
                        }
                        linea = info.readLine();
                    }
                    
                    //datos.d_indexeador.insertarTabla(map, dir.getName(),dir.getAbsolutePath() );
                    
                    indexController.insertar(map, document );

                }
            }
        } catch (IOException e) {
           
        }
        
    }
   
    private static boolean insertar(HashMap<String, Vocabulary> map, Document document) {
        
        
        int idFile = DocumentRepository.saveDocument( document );
        
        /*
            TF = Maxima frecuencia de esa palabra en un documento
            NR = En cuantos documentos esta
        */
        
        VocabularyRepository.insertAll(map, idFile);

        return true;
    }
    
    /**
     *
     * @param file 
     * Recibimos un archivo por parametro para comenzar la lectura del mismo
     */  

    public void readDirectory(File file) {
        if (file == null) {
            new NullPointerException("ERROR");
        }

        if (!file.isDirectory()) {
            System.err.println("ERROR");
            return;
        }

      //  Arrays.stream(file.listFiles())
        //        .forEach(x -> readFile(x));

    }
    
   /* public void readFile(File file) {
        
        // No repite palabras ...
        HashSet<String> palabras = new HashSet<>();
        
        if (file == null) {
            new NullPointerException("ERROR-ARCHIVO NO ENCONTRADO");
        }

        try (Scanner sc = new Scanner(file)) {

            sc.useDelimiter("\\s|,|\\*|\\{|\\}|\\[|\\]|\\.|\\(|\\)|\\;|:|/|¡|!|\\?|¿|\\#|\\=|-|'|\"");

            sc.tokens()
                    .filter(x -> x.length() > 1)
                    .map(String::toLowerCase)
                    
                    // Recorremos palabras ...
                    .forEach((x) -> {
                        // Sumamos TF de cada PALABRA para ESTE documento
                        sumTf(x);
                        palabras.add(x);
                    });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Guardamos el nombre del archivo y devolver ID del archivo
        int idArchivo = 1;//datos.d_indexeador.loadFile(file); 
        
        // Recorremos PALABRAS que no estan repetidas en este DOCUMENTO
        palabras.forEach(
                (x)-> {
                    // Guardamos palabras y le pasamos el ID del archivo para hacer la inserción de POSTEO
                    loadWord(x, idArchivo);
                    
                   
                });
                        
        sumNr(palabras);
        vocabulario.forEach((x, y) -> y.setTf(0));
    }*/

   
    public static int countFile( ) throws ClassNotFoundException {
        return repositories.DocumentRepository.countDocuments();
    }
    
}
