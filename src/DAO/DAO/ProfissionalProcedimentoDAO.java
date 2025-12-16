package DAO.DAO;

import Classes.Procedimento;
import DAO.Util.ConexaoDAO;
import java.sql.*;
import java.util.ArrayList;

public class ProfissionalProcedimentoDAO {

    private Connection conn;

    public ProfissionalProcedimentoDAO() throws ErpDAOException {
        try {
            this.conn = ConexaoDAO.getConnection();
        } catch (Exception e) {
            throw new ErpDAOException("Erro de conexão: " + e.getMessage());
        }
    }

public void inserirVinculo(int idProfissional, int idProcedimento) throws ErpDAOException {
    PreparedStatement ps = null;
    try {
        String SQL = "INSERT INTO profissional_procedimento (id_profissional, id_procedimento) VALUES (?, ?)";
        ps = conn.prepareStatement(SQL);
        ps.setInt(1, idProfissional);
        ps.setInt(2, idProcedimento);
        ps.executeUpdate();
    } catch (SQLException e) {
        throw new ErpDAOException("Erro ao inserir vínculo: " + e.getMessage());
    } finally {
        ConexaoDAO.close(null, ps);
    }
}

public void removerVinculo(int idProfissional, int idProcedimento) throws ErpDAOException {
    PreparedStatement ps = null;
    try {
        String SQL = "DELETE FROM profissional_procedimento WHERE id_profissional = ? AND id_procedimento = ?";
        ps = conn.prepareStatement(SQL);
        ps.setInt(1, idProfissional);
        ps.setInt(2, idProcedimento);
        ps.executeUpdate();
    } catch (SQLException e) {
        throw new ErpDAOException("Erro ao remover vínculo: " + e.getMessage());
    } finally {
        ConexaoDAO.close(null, ps);
    }
}

        public void InserirProcedimento(Procedimento procedimento) throws ErpDAOException {
        if (procedimento == null) {
            throw new ErpDAOException("O objeto procedimento não pode ser nulo.");
        }

        PreparedStatement ps = null;

        try {
            String SQL = "INSERT INTO procedimento (nome_procedimento, duracao, preco) VALUES (?,?,?)";
            ps = conn.prepareStatement(SQL);

            ps.setString(1, procedimento.getNome_procedimento());
            ps.setInt(2, procedimento.getDuracao());
            ps.setBigDecimal(3, procedimento.getPreco());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ErpDAOException("Erro ao inserir procedimento: " + e.getMessage());
        } finally {
            ConexaoDAO.close(null, ps);
        }
    }

        public ArrayList<Procedimento> ListarProcedimentos() throws ErpDAOException {
        ArrayList<Procedimento> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String SQL = "SELECT * FROM procedimento ORDER BY nome_procedimento";
            ps = conn.prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_procedimento");
                String nome = rs.getString("nome_procedimento");
                int duracao = rs.getInt("duracao");
                java.math.BigDecimal preco = rs.getBigDecimal("preco");

                lista.add(new Procedimento(id, nome, duracao, preco));
            }

        } catch (SQLException e) {
            throw new ErpDAOException("Erro ao listar procedimentos: " + e.getMessage());
        } finally {
            ConexaoDAO.close(null, ps, rs);
        }

        return lista;
    }

        public ArrayList<Procedimento> ListarVinculos(int idProfissional) throws ErpDAOException {
        ArrayList<Procedimento> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String SQL = "SELECT p.id_procedimento, p.nome_procedimento, p.duracao, p.preco " +  "FROM procedimento p " + "JOIN profissional_procedimento pp ON pp.id_procedimento = p.id_procedimento " + "WHERE pp.id_profissional = ?";

            ps = conn.prepareStatement(SQL);
            ps.setInt(1, idProfissional);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_procedimento");
                String nome = rs.getString("nome_procedimento");
                int duracao = rs.getInt("duracao");
                java.math.BigDecimal preco = rs.getBigDecimal("preco");

                lista.add(new Procedimento(id, nome, duracao, preco));
            }

        } catch (SQLException e) {
            throw new ErpDAOException("Erro ao listar vínculos: " + e.getMessage());
        } finally {
            ConexaoDAO.close(null, ps, rs);
        }

        return lista;
    }
}
