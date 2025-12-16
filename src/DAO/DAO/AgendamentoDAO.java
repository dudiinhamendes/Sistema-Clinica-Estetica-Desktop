package DAO.DAO;

import Classes.Agendamento;
import Classes.Procedimento;
import DAO.Util.ConexaoDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Time;
import java.time.LocalDate;

public class AgendamentoDAO {

    private Connection conn;

    public AgendamentoDAO() throws ErpDAOException {
        try {
            this.conn = ConexaoDAO.getConnection();
        } catch (Exception e) {
            throw new ErpDAOException("Erro de conexão: " + ":\n" + e.getMessage());
        }
    }

    public ArrayList ListarAgendamento() throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;

        try {
            String SQL = "SELECT agendamento.id_agendamento, " + "agendamento.id_cliente, " + "agendamento.id_profissional, " + "agendamento.id_procedimento, " + "agendamento.data, " + "agendamento.hora, " + "agendamento.status, " + "cliente.nome_cliente, " + "profissional.nome_profissional, " + "procedimento.nome_procedimento " + "FROM agendamento, cliente, profissional, procedimento " + "WHERE agendamento.id_cliente = cliente.id_cliente " + "AND agendamento.id_profissional = profissional.id_profissional " + "AND agendamento.id_procedimento = procedimento.id_procedimento;";

            connL = this.conn;

            ps = connL.prepareStatement(SQL);
            rs = ps.executeQuery();

            ArrayList CadastroAgendamento = new ArrayList();

            while (rs.next()) {
                int id_agendamento = rs.getInt("id_agendamento");
                int id_cliente = rs.getInt("id_cliente");
                int id_profissional = rs.getInt("id_profissional");
                int id_procedimento = rs.getInt("id_procedimento");
                Date data = rs.getDate("data");
                Time hora = rs.getTime("hora");
                String status = rs.getString("status");
                String nome_cliente = rs.getString("nome_cliente");
                String nome_profissional = rs.getString("nome_profissional");
                String nome_procedimento = rs.getString("nome_procedimento");

                CadastroAgendamento.add(new Agendamento(id_agendamento, id_cliente, id_profissional, id_procedimento, data, hora, status, nome_cliente, nome_profissional, nome_procedimento));
            }

            return CadastroAgendamento;

        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao listar agendamento " + sqle);
        } finally {
            ConexaoDAO.close(connL, ps);
        }
    }

    public ArrayList<Agendamento> ListarAgendamentoPorProfissional(int idProfissional) throws ErpDAOException {
        ArrayList<Agendamento> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
 String sql = "SELECT a.id_agendamento, a.id_cliente, a.id_profissional, a.id_procedimento, "
               + "a.data, a.hora, a.status, "
               + "c.nome_cliente AS nome_cliente, "
               + "p.nome_profissional AS nome_profissional, "
               + "pr.nome_procedimento AS nome_procedimento "
               + "FROM agendamento a "
               + "JOIN cliente c ON a.id_cliente = c.id_cliente "
               + "JOIN profissional p ON a.id_profissional = p.id_profissional "
               + "JOIN procedimento pr ON a.id_procedimento = pr.id_procedimento "
               + "WHERE p.id_profissional = ?";
 
 ps = this.conn.prepareStatement(sql);
            ps.setInt(1, idProfissional);
            rs = ps.executeQuery();

            while (rs.next()) {
                Agendamento a = new Agendamento();
                a.setId_agendamento(rs.getInt("id_agendamento"));
                a.setId_cliente(rs.getInt("id_cliente"));
                a.setId_profissional(rs.getInt("id_profissional"));
                a.setId_procedimento(rs.getInt("id_procedimento"));
                a.setData(rs.getDate("data"));
                a.setHora(rs.getTime("hora"));
                a.setStatus(rs.getString("status"));
                a.setNome_cliente(rs.getString("nome_cliente"));
                a.setNome_profissional(rs.getString("nome_profissional"));
                a.setNome_procedimento(rs.getString("nome_procedimento"));
                lista.add(a);
            }

        } catch (SQLException e) {
            throw new ErpDAOException("Erro ao listar agendamento por profissional: " + e.getMessage());
        } finally {
            ConexaoDAO.close(null, ps, rs);
        }

        return lista;
    }

    public void InserirAgendamento(Agendamento agendamento) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (agendamento == null) {
            throw new ErpDAOException("O objeto agendamento não pode ser nulo.");
        }
        try {
            String SQL = "INSERT INTO agendamento (id_cliente, id_profissional, id_procedimento, data, hora, status)" + "values (?,?,?,?,?,?)";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, agendamento.getId_cliente());
            ps.setInt(2, agendamento.getId_profissional());
            ps.setInt(3, agendamento.getId_procedimento());
            java.util.Date dataJAVA = agendamento.getData();
            java.sql.Date dataSQL = new java.sql.Date(dataJAVA.getTime());
            ps.setDate(4, dataSQL);
            ps.setTime(5, agendamento.getHora());
            ps.setString(6, agendamento.getStatus());
            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao inserir um novo agendamento " + sqle);
        } finally {
            ConexaoDAO.close(connL, ps);
        }
    }

    public void AlterarAgendamento(Agendamento agendamento) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (agendamento == null) {
            throw new ErpDAOException("O objeto agendamento não pode ser nulo.");
        }
        try {
            String SQL = "UPDATE agendamento set id_cliente=?, id_profissional=?, id_procedimento=?, data=?, hora=?, status=? WHERE id_agendamento=?";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, agendamento.getId_cliente());
            ps.setInt(2, agendamento.getId_profissional());
            ps.setInt(3, agendamento.getId_procedimento());
            java.util.Date dataJAVA = agendamento.getData();
            java.sql.Date dataSQL = new java.sql.Date(dataJAVA.getTime());
            ps.setDate(4, dataSQL);
            ps.setTime(5, agendamento.getHora());
            ps.setString(6, agendamento.getStatus());
            ps.setInt(7, agendamento.getId_agendamento());

            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao editar agendamento" + sqle);
        } finally {
            ConexaoDAO.close(connL, ps);
        }
    }

    public Agendamento ProcurarAgendamento(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;

        try {
            String SQL = "SELECT agendamento.id_agendamento, " + "agendamento.id_cliente, " + "agendamento.id_profissional, " + "agendamento.id_procedimento, " + "agendamento.data, " + "agendamento.hora, " + "agendamento.status, " + "cliente.nome_cliente, " + "profissional.nome_profissional, " + "procedimento.nome_procedimento " + "FROM agendamento, cliente, profissional, procedimento " + "WHERE agendamento.id_cliente = cliente.id_cliente " + "AND agendamento.id_profissional = profissional.id_profissional " + "AND agendamento.id_procedimento = procedimento.id_procedimento " + "AND agendamento.id_agendamento = ?;";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            Agendamento agendamento = new Agendamento();
            agendamento = null;

            while (rs.next()) {
                int id_agendamento = rs.getInt("id_agendamento");
                int id_cliente = rs.getInt("id_cliente");
                int id_profissional = rs.getInt("id_profissional");
                int id_procedimento = rs.getInt("id_procedimento");
                Date data = rs.getDate("data");
                Time hora = rs.getTime("hora");
                String status = rs.getString("status");
                String nome_cliente = rs.getString("nome_cliente");
                String nome_profissional = rs.getString("nome_profissional");
                String nome_procedimento = rs.getString("nome_procedimento");

                agendamento = new Agendamento(id_agendamento, id_cliente, id_profissional, id_procedimento, data, hora, status, nome_cliente, nome_profissional, nome_procedimento);

            }

            return agendamento;

        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao procurar agendamento" + sqle);
        } finally {
            // ConexaoAulaDAO.close(connL,ps);
        }
    }

    public void ExcluirAgendamento(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (codigo == 0) {
            throw new ErpDAOException("O objeto agendamento não pode ser nulo.");
        }

        try {
            String SQL = "DELETE FROM agendamento WHERE id_agendamento=?";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao excluir agendamento" + sqle);
        } finally {
            ConexaoDAO.close(connL, ps);
        }
    }

    public int contarAgendamentos() throws ErpDAOException {
        int total = 0;
        try {
            String SQL = "SELECT COUNT(*) FROM agendamento";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new ErpDAOException("Erro ao contar agendamentos: " + e.getMessage());
        }
        return total;
    }

}
