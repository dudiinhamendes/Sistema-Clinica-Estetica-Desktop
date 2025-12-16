
package Classes;

import java.util.Date;

public class Cliente {
    
    int id_cliente;
    String nome_cliente;
    char sexo;
    String telefone;
    String cpf;
    Date data_nascimento;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
   
    public Cliente() {
    }

    public Cliente(int id_cliente, String nome_cliente, char sexo, String telefone, String cpf, Date data_nascimento) {
        this.id_cliente = id_cliente;
        this.nome_cliente = nome_cliente;
        this.sexo = sexo;
        this.telefone = telefone;
        this.cpf = cpf;
        this.data_nascimento = data_nascimento;
    }
   
}
