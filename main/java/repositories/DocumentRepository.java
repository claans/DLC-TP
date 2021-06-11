/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mu_Cl
 */
public class DocumentRepository {
    
    public static int saveDocument(Document document) {
        
        int idArchivo = 0;
        
        //String path = file.getPath();
        try {
            Connection conn = repositories.d_connection.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO dbo.documentos VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, document.getName());
            ps.setString(2, document.getPath());
            
            idArchivo = ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                idArchivo = rs.getInt(1);
            }

            ps.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(VocabularyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idArchivo;
    }
    
    public static int countDocuments() throws ClassNotFoundException {
        int count = 0;
        try {
            
            Connection conn = repositories.d_connection.getConnection();
             
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT count(*) FROM dbo.documentos");

            rs.next();
            count = rs.getInt(1);

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(VocabularyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
}
