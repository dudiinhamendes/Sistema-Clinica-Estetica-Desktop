
package Classes;

import java.sql.Time;
import java.util.Date;

public class Agendamento {

    int id_agendamento;
    int id_cliente;
    int id_profissional;
    int id_procedimento;
    Date data;
    Time hora;
    String status;

    private String nome_cliente;
    private String nome_profissional;
    private String nome_procedimento;
    
    public int getId_agendamento() {
        return id_agendamento;
    }

    public void setId_agendamento(int id_agendamento) {
        this.id_agendamento = id_agendamento;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_profissional() {
        return id_profissional;
    }

    public void setId_profissional(int id_profissional) {
        this.id_profissional = id_profissional;
    }

    public int getId_procedimento() {
        return id_procedimento;
    }

    public void setId_procedimento(int id_procedimento) {
        this.id_procedimento = id_procedimento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
     public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

     public String getNome_profissional() {
        return nome_profissional;
    }

    public void setNome_profissional(String nome_profissional) {
        this.nome_profissional = nome_profissional;
    }
    
     public String getNome_procedimento() {
        return nome_procedimento;
    }

    public void setNome_procedimento(String nome_procedimento) {
        this. nome_procedimento=  nome_procedimento;
    }
    
    public Agendamento() {
    }
    
    public Agendamento(int id_agendamento, int id_cliente, int id_profissional, int id_procedimento, Date data, Time hora, String status, String nome_cliente, String nome_profissional, String nome_procedimento) {
        this.id_agendamento = id_agendamento;
        this.id_cliente = id_cliente;
        this.id_profissional = id_profissional;
        this.id_procedimento = id_procedimento;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.nome_cliente = nome_cliente;
        this.nome_profissional = nome_profissional;
        this.nome_procedimento = nome_procedimento;
}
}
