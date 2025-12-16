
package DAO.DAO;

import Classes.Profissional;
import DAO.Util.ConexaoDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfissionalDAO {
    
    private Connection conn;
    
    public ProfissionalDAO() throws ErpDAOException {
      try{
            this.conn = ConexaoDAO.getConnection();
        }catch(Exception e){
            throw new ErpDAOException("Erro de conexão: " + ":\n" + e.getMessage());
        }
}
    
    public ArrayList<Profissional> ListarProfissional() throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;
    
        try {
            String SQL = "SELECT * FROM profissional ORDER BY nome_profissional";
            connL = this.conn;

            ps = connL.prepareStatement(SQL);
            rs = ps.executeQuery();

            ArrayList CadastroProfissional = new ArrayList();
            
            while (rs.next()) {
                int id_profissional = rs.getInt("id_profissional");
                String nome_profissional = rs.getString("nome_profissional");
                char sexo;
                if (rs.getString("sexo") == null) {
                      sexo =' ' ;
                }
              else {
                  sexo = (rs.getString("sexo")).charAt(0);
                }
                String especialidade = rs.getString("especialidade");
                String telefone = rs.getString("telefone");
                
                CadastroProfissional.add(new Profissional(id_profissional, nome_profissional, sexo, especialidade, telefone));
            }
            
            return CadastroProfissional;
            
        }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao listar Profissional " + sqle);
        } finally{
           ConexaoDAO.close(connL,ps);
        }   
}

    public void InserirProfissional(Profissional profissional) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;        
        if (profissional == null){
            throw new ErpDAOException("O objeto profissional não pode ser nulo.");
        }
         try{
         String SQL = "INSERT INTO profissional (nome_profissional, sexo, especialidade, telefone)" + "values (?,?,?,?)";
             connL = this.conn;
            ps = connL.prepareStatement(SQL);           
            ps.setString(1, profissional.getNome_profissional());
            ps.setString(2, Character.toString(profissional.getSexo()));
            ps.setString(3, profissional.getEspecialidade());
            ps.setString(4, profissional.getTelefone());
            ps.executeUpdate();
            
         }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao inserir uma novo profissional " + sqle);
        }finally{
           ConexaoDAO.close(connL,ps);
        }
    }
    
    public void AlterarProfissional(Profissional profissional) throws ErpDAOException {
           PreparedStatement ps = null;
        Connection connL = null;
        if (profissional == null){
            throw new ErpDAOException("O objeto profissional não pode ser nulo.");
        }
        try{
            String SQL = "UPDATE profissional set nome_profissional=?, sexo=?, especialidade=?, telefone=? WHERE id_profissional=?";
            connL = this.conn;
            ps = connL.prepareStatement(SQL); 
            ps.setString(1, profissional.getNome_profissional());
            ps.setString(2, Character.toString(profissional.getSexo()));
            ps.setString(3, profissional.getEspecialidade());
            ps.setString(4, profissional.getTelefone());
            ps.setInt(5, profissional.getId_profissional());
            ps.executeUpdate();

        }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao editar profissional" + sqle);
        }
        finally{
           ConexaoDAO.close(connL,ps);
        }
    }
    
    public Profissional ProcurarProfissional(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;
          
        try{
         String SQL = "SELECT id_profissional, nome_profissional, sexo, especialidade, telefone FROM profissional WHERE id_profissional = ?";
         connL = this.conn;
         ps = connL.prepareStatement(SQL);
         ps.setInt(1, codigo);
         rs = ps.executeQuery();
         Profissional profissional = new Profissional();
         profissional = null;
            
         while (rs.next()) {
             int id_profissional = rs.getInt("id_profissional");
             String nome_profissional = rs.getString("nome_profissional");
              char sexo;
              if (rs.getString("sexo") == null) {
                    sexo =' ' ;
                }
              else {
                    sexo = (rs.getString("sexo")).charAt(0);
                }
              String especialidade = rs.getString("especialidade");
              String telefone = rs.getString("telefone");
              
              profissional = new Profissional(id_profissional, nome_profissional, sexo, especialidade, telefone);
         }
         
         return profissional;
         
        }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao procurar profissional" + sqle);
        }finally{
          // ConexaoAulaDAO.close(connL,ps);
        }  
    }
    
    public void ExcluirProfissional(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (codigo == 0){
            throw new ErpDAOException("O objeto profissional não pode ser nulo.");
        }
        
        try {
            String SQL = "DELETE FROM profissional WHERE id_profissional=?";
            connL = this.conn;
            
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, codigo);
            ps.executeUpdate();            
            
        }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao excluir profissional " + sqle);
        }finally{
          ConexaoDAO.close(connL,ps);
        }
    }
    
    public int contarProfissionais() {
    int total = 0;
    String sql = "SELECT COUNT(*) AS total FROM profissional";

    try (Connection conn = ConexaoDAO.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            total = rs.getInt("total");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return total;
}

}