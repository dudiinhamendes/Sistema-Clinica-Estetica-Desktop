
package DAO.DAO;

import Classes.Procedimento;
import DAO.Util.ConexaoDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcedimentoDAO {
    
    private Connection conn;

    public ProcedimentoDAO() throws ErpDAOException{
        try{
            this.conn = ConexaoDAO.getConnection();
        }catch(Exception e){
            throw new ErpDAOException("Erro de conexão: " + ":\n" + e.getMessage());
        }
    }
    
    public boolean procedimentoPertenceAoProfissional(int idProcedimento, int idProfissional) throws ErpDAOException {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        String SQL = "SELECT 1 FROM profissional_procedimento " + "WHERE id_profissional = ? AND id_procedimento = ?";

        ps = this.conn.prepareStatement(SQL);
        ps.setInt(1, idProfissional);
        ps.setInt(2, idProcedimento);
        rs = ps.executeQuery();

        return rs.next(); 

    } catch (SQLException e) {
        throw new ErpDAOException("Erro ao validar: " + e.getMessage());
    } finally {
        ConexaoDAO.close(null, ps, rs);
    }
}

    
public ArrayList<Procedimento> listarProcedimentosPorProfissional(int idProfissional) throws ErpDAOException {
    ArrayList<Procedimento> lista = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
String SQL = "SELECT p.id_procedimento, p.nome_procedimento, p.duracao, p.preco "
             + "FROM procedimento p "
             + "JOIN profissional_procedimento pp ON pp.id_procedimento = p.id_procedimento "
             + "WHERE pp.id_profissional = ?";

        ps = this.conn.prepareStatement(SQL);
        ps.setInt(1, idProfissional);
        rs = ps.executeQuery();

        while (rs.next()) {
            Procedimento proc = new Procedimento();
            proc.setId_procedimento(rs.getInt("id_procedimento"));
            proc.setNome_procedimento(rs.getString("nome_procedimento"));
            proc.setDuracao(rs.getInt("duracao"));
            proc.setPreco(rs.getBigDecimal("preco"));
            lista.add(proc);
        }

    } catch (SQLException e) {
        throw new ErpDAOException("Erro ao listar procedimentos por profissional: " + e.getMessage());
    } finally {
        ConexaoDAO.close(null, ps, rs);
    }

    return lista;
}

    
    public ArrayList ListarProcedimento() throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;

        try{
            String SQL = "SELECT * FROM procedimento ORDER BY nome_procedimento";
            connL = this.conn;
            
            ps = connL.prepareStatement(SQL);
            rs = ps.executeQuery();

            ArrayList CadastroProcedimentos = new ArrayList();

            while( rs.next()) {
                int id_procedimento = rs.getInt("id_procedimento");
                String nome_procedimento= rs.getString("nome_procedimento");
                int duracao = rs.getInt("duracao");
                BigDecimal preco = rs.getBigDecimal("preco");
                
                CadastroProcedimentos.add(new Procedimento(id_procedimento, nome_procedimento, duracao, preco));
            }
            
            return CadastroProcedimentos;
            
        }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao listar procedimento " + sqle);
        }
        finally{
           ConexaoDAO.close(connL,ps);
        }
}

    public void InserirProcedimento(Procedimento procedimento) throws ErpDAOException {      

    PreparedStatement ps = null;
    Connection connL = null;        

    if (procedimento == null){
        throw new ErpDAOException("O objeto procedimento não pode ser nulo.");
    }

    try{
        String SQL = "INSERT INTO procedimento (nome_procedimento, duracao, preco) "
                   + "VALUES (?, ?, ?)";

        connL = this.conn;
        ps = connL.prepareStatement(SQL);           

        ps.setString(1, procedimento.getNome_procedimento());
        ps.setInt(2, procedimento.getDuracao());
        ps.setBigDecimal(3, procedimento.getPreco());
        
        ps.executeUpdate();

    } catch(SQLException sqle){
       throw new ErpDAOException("Erro ao inserir um novo procedimento: " + sqle.getMessage());
    } finally {
       ConexaoDAO.close(connL, ps);
    }
}

    
    public void AlterarProcedimento(Procedimento procedimento) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (procedimento == null){
            throw new ErpDAOException("O objeto procedimento não pode ser nulo.");
        }
        
        try {
      String SQL = "UPDATE procedimento set nome_procedimento=?, duracao=?, preco=? WHERE id_procedimento=?";
      connL = this.conn;
      ps = connL.prepareStatement(SQL); 
      ps.setString(1, procedimento.getNome_procedimento());
      ps.setInt(2, procedimento.getDuracao());
      ps.setBigDecimal(3, procedimento.getPreco());
      ps.setInt(4, procedimento.getId_procedimento());      
      ps.executeUpdate();
      
        } catch(SQLException sqle){
           throw new ErpDAOException("Erro ao editar procedimento " + sqle);
        }
        finally{
           ConexaoDAO.close(connL,ps);
        }
      }
    
    public Procedimento ProcurarProcedimento(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;
 
        try {
            String SQL = "SELECT id_procedimento, nome_procedimento, duracao, preco FROM procedimento WHERE id_procedimento = ?";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            Procedimento procedimento = new Procedimento();
            procedimento = null;      
            
            while( rs.next()){
                int id_procedimento = rs.getInt("id_procedimento");
                String nome_procedimento = rs.getString("nome_procedimento");
                int duracao = rs.getInt("duracao");
                BigDecimal preco = rs.getBigDecimal("preco");
                
                procedimento = new Procedimento(id_procedimento, nome_procedimento, duracao, preco);
            
            }
            
            return procedimento;
            
        }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao procurar procedimento " + sqle);
        }
        finally{
          // ConexaoAulaDAO.close(connL,ps);
        }
    }
    
    public void ExcluirProcedimento(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (codigo == 0){
            throw new ErpDAOException("O objeto procedimento não pode ser nulo.");
        }
        
        try{
            String SQL = "DELETE FROM procedimento WHERE id_procedimento=?";
            connL = this.conn;
            
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, codigo);
            ps.executeUpdate();
        }catch(SQLException sqle){
           throw new ErpDAOException("Erro ao excluir procedimento " + sqle);
        }
        finally{
          ConexaoDAO.close(connL,ps);
        }
    
}
    
    public int contarProcedimentos() {
    int total = 0;
    String sql = "SELECT COUNT(*) AS total FROM procedimento";

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