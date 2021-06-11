package repositories;

import entities.Vocabulary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VocabularyRepository {

    public static boolean insertAll(HashMap<String, Vocabulary> map, int idArchivo) {

        Statement stat = null;
        ResultSet rs = null;

        try {
            Connection conn = repositories.d_connection.getConnection();
            stat = conn.createStatement();

            Iterator it = map.entrySet().iterator();
            
            /*
                La perfección seria que cuando cargue el primer archivo, me devuelva 30mil palabras
                Insertar en la DB lo que haga falta
                Pero cuando tenga el hashmap del segundo archivo, empiece a contar desde las 30 mil palabras anterior
                Sino tengo la lectura de 30mil palabras que no sirve ...
            */
            
            while (it.hasNext()) {
            
                
                /*
                    TF = Maxima frecuencia de esa palabra en un documento
                    NR = En cuantos documentos esta
                */
                
                Map.Entry e = (Map.Entry) it.next();
                
                Vocabulary v = (Vocabulary) map.get( e.getKey() );
                
                // Chequeamos si esta en DB
                if( v.isBaseDeDatos() ) {
                    
                    // Si esta en la DB basicamente no hacemos nada ... 
                    
                } else {
                    // Peso si no esta en la DB debemos insertar la palabra y recuperar el ID que utilizamos para guardarlo en memoria ..
                    
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO dbo.vocabulario (nr, palabra, tf) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, v.getNr() );
                    ps.setString(2, (String) e.getKey());
                    ps.setInt(3, v.getTf());

                    ps.executeUpdate();

                    rs = ps.getGeneratedKeys();

                    if (rs.next()) {
                        // Obtenemos el ID de la palabra en base de datos, y la guardamos en memoria ..
                        v.setId( rs.getInt(1) );
                    }
                    
                    // Seteamos que existe en la base de datos
                    v.setBaseDeDatos(true);
                }
                
                /*
                    Con la siguiente instrucción generamos dos procedimientos importantes
                
                    1)  SOLO vamos a guardar en la tabla POST aquellas palabras que contengan un TF mayor a cero, 
                        dado que significa que existen en el documento recientemiente leido
                
                    2)  Vamos a aumentar el NR de la palabra. Para hacerlo debemos ver si el TF es mayor a 0 ...
                        Dado que Sigifnica que en este documento aparecio 
                */
                
                if( v.getTf() > 0 ) {
                    // Obviamente debemos obtener su valor de NR y sumarle 1 (Dado que esta en 1 documento MAS)
                    v.setNr( v.getNr() + 1 );
                    
                    // Insertamos el posteo ...
                    stat.executeUpdate("insert into post values ( " + idArchivo + ", " + v.getId() + ", " + v.getTf() + " );");
                    
                    // Seteamos el TF en 0 dado que si existe esta palabra en otro documento, le sumara un UNO 
                    // que correspondera a la repeticion de esa palabra en ese documento

                    // Es diferente a cuando lo creamos que se setea con 1 ..
                    v.setTf( 0 );
                }
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
            return false;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { }
            if (stat != null) try { stat.close(); } catch (SQLException e) { }
        }
    }

    public static void actualizarNr(HashMap<String, Vocabulary> map) {
        Statement stat = null;
        ResultSet rs = null;

        try {
            Connection conn = repositories.d_connection.getConnection();
            stat = conn.createStatement();

            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                
                /*
                    Asumimos que todas las palabras se repetiran aunque sea mas de una vez en la cantidad de documentos
                    Entonces en vez de chequear si el NR de esa palabra es diferente al que tenemos en memoria
                    Directamente updateamos todo ..
                */
                
                Map.Entry e = (Map.Entry) it.next();
                
                Vocabulary v = (Vocabulary) map.get( e.getKey() );

                PreparedStatement ps = conn.prepareStatement( "UPDATE dbo.vocabulario SET nr = ? WHERE id = ?" );
                ps.setInt(1, v.getNr() );
                ps.setInt(2, v.getId() );
                
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { }
            if (stat != null) try { stat.close(); } catch (SQLException e) { }
        }

    }
    
    public boolean consultarDocumento(String name) {
        return false;
    }
}
