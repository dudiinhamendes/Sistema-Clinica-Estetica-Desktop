
package DAO.Util;

import Classes.Configuracao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexaoDAO {
 
    
    public static Connection getConnection() {
        
        Configuracao CF = new Configuracao();
        CF = AcessoArquivo.le();
        String server = CF.getServer();
        String porta = CF.getPorta();
        String banco = CF.getBanco();
        String user = CF.getUser();
        String password = CF.getPassword();

        String strcon="jdbc:mysql://"+server+":"+porta+"/"+banco;
        
    	Connection conexao = null;

        try{
           conexao = DriverManager.getConnection(strcon, user , password );
           System.out.println("Conexão ok");
       }catch(Exception e){
          System.out.println(e.getMessage());
          conexao = null;
       }
       
       return conexao;
       
   }

   public static void close(Connection conn, Statement stmt, ResultSet rs) {
       try{
           if (rs != null) rs.close();
           if (stmt != null) stmt.close();
           if (conn != null) conn.close();
       }catch(Exception e){
    	   System.out.println(e.getMessage());
       }
   }
   
   
   public static void close(Connection conn, Statement stmt) {
       try{
           if (stmt != null) stmt.close();
           if (conn != null) conn.close();
       }catch(Exception e){
    	   System.out.println(e.getMessage());
       }
   }
}
