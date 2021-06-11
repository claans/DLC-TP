package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Imanol Rodriguez
 */
public class d_connection {
    
    public static final String SQL_SERVER_CONNECTION = "jdbc:sqlserver://DESKTOP-BDSOHOM\\SQLEXPRESS;databaseName=DLCJava;integratedSecurity=false";
    public static final String USER = "UsuarioJavaNetBeans";
    public static final String PWD = "asd123";
    
    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            String dbURL = "jdbc:sqlserver://DESKTOP-BDSOHOM\\SQLEXPRESS:1434;databaseName=DLCJava;user=UsuarioJavaNetBeans;password=asd123;integratedSecurity=false";
        
            conn = DriverManager.getConnection(dbURL);
            
           // conn = DriverManager.getConnection(SQL_SERVER_CONNECTION, USER, PWD);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VocabularyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
}
