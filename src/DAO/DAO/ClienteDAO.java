package DAO.DAO;

import Classes.Cliente;
import DAO.Util.ConexaoDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {

    private Connection conn;

    public ClienteDAO() throws ErpDAOException {
        try {
            this.conn = ConexaoDAO.getConnection();
        } catch (Exception e) {
            throw new ErpDAOException("Erro de conexão: " + ":\n" + e.getMessage());
        }
    }

    public ArrayList ListarCliente() throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;

        try {
            String SQL = "SELECT * FROM cliente ORDER BY nome_cliente";
            connL = this.conn;

            ps = connL.prepareStatement(SQL);
            rs = ps.executeQuery();

            ArrayList CadastroCliente = new ArrayList();

            while (rs.next()) {
                int id_cliente = rs.getInt("id_cliente");
                String nome_cliente = rs.getString("nome_cliente");
                char sexo;
              if (rs.getString("sexo") == null) {
                    sexo =' ' ;
                }
              else {
                    sexo = (rs.getString("sexo")).charAt(0);
                }
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");
                Date data_nascimento = rs.getDate("data_nascimento");

                CadastroCliente.add(new Cliente(id_cliente, nome_cliente, sexo, telefone, cpf, data_nascimento));
            }

            return CadastroCliente;

        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao listar Clientes" + sqle);
        } finally {
            ConexaoDAO.close(connL, ps);
        }
    } 

    public void InserirCliente(Cliente cliente) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (cliente == null) {
            throw new ErpDAOException("O objeto cliente não pode ser nulo.");
        }

        try {
            String SQL = "INSERT INTO cliente (nome_cliente, sexo, telefone, cpf, data_nascimento)" + "values (?,?,?,?,?)";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setString(1, cliente.getNome_cliente());
            ps.setString(2, Character.toString(cliente.getSexo()));
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getCpf());
            java.util.Date dataJAVA = cliente.getData_nascimento();  
            java.sql.Date dataSQL = new java.sql.Date(dataJAVA.getTime()); 
            ps.setDate(5, dataSQL);
            ps.executeUpdate();

        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao inserir um novo cliente " + sqle);
        } finally {
            ConexaoDAO.close(connL, ps);
        }
    }

    public void AlterarCliente(Cliente cliente) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (cliente == null) {
            throw new ErpDAOException("O objeto cliente não pode ser nulo.");
        }

        try {
            String SQL = "UPDATE cliente set nome_cliente=?, sexo=?, telefone=?, cpf=?, data_nascimento=? WHERE id_cliente=?";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setString(1, cliente.getNome_cliente());
            ps.setString(2, Character.toString(cliente.getSexo()));
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getCpf());
            java.util.Date dataJAVA = cliente.getData_nascimento();
            java.sql.Date dataSQL = new java.sql.Date(dataJAVA.getTime());
            ps.setDate(5, dataSQL);
            ps.setInt(6, cliente.getId_cliente());
            ps.executeUpdate();
            
        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao editar cliente" + sqle);
        } finally {
            ConexaoDAO.close(connL, ps);
        }
    }

    public Cliente ProcurarCliente(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        ResultSet rs = null;

        try {
            String SQL = "SELECT id_cliente, nome_cliente, sexo, telefone, cpf, data_nascimento FROM cliente WHERE id_cliente = ?";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            Cliente cliente = new Cliente();
            cliente = null;

            while (rs.next()) {
                int id_cliente = rs.getInt("id_cliente");
                String nome_cliente = rs.getString("nome_cliente");
                char sexo;
              if (rs.getString("sexo") == null) {
                    sexo =' ' ;
                }
              else {
                    sexo = (rs.getString("sexo")).charAt(0);
                }
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");
                Date data_nascimento = rs.getDate("data_nascimento");

                cliente = new Cliente(id_cliente, nome_cliente, sexo, telefone, cpf, data_nascimento);
            }

            return cliente;

        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao procurar cliente" + sqle);
        } finally {
            // ConexaoAulaDAO.close(connL,ps);
        }
    }

    public void ExcluirCliente(int codigo) throws ErpDAOException {
        PreparedStatement ps = null;
        Connection connL = null;
        if (codigo == 0) {
            throw new ErpDAOException("O objeto cliente não pode ser nulo.");
        }

        try {
            String SQL = "DELETE FROM cliente WHERE id_cliente=?";
            connL = this.conn;
            ps = connL.prepareStatement(SQL);
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new ErpDAOException("Erro ao excluir cliente" + sqle);
        } finally { 
            ConexaoDAO.close(connL, ps);
        }
    }
    
        public int contarClientes() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM cliente"; 

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
