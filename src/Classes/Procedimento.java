
package Classes;

import java.math.BigDecimal;

public class Procedimento {

int id_procedimento;
String nome_procedimento;
int duracao;
BigDecimal preco;

    public int getId_procedimento() {
        return id_procedimento;
    }

    public void setId_procedimento(int id_procedimento) {
        this.id_procedimento = id_procedimento;
    }

    public String getNome_procedimento() {
        return nome_procedimento;
    }

    public void setNome_procedimento(String nome_procedimento) {
        this.nome_procedimento = nome_procedimento;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Procedimento() {
    }

    public Procedimento(int id_procedimento, String nome_procedimento, int duracao, BigDecimal preco) {
        this.id_procedimento = id_procedimento;
        this.nome_procedimento = nome_procedimento;
        this.duracao = duracao;
        this.preco = preco;
    }

    public int getId_profissional() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
