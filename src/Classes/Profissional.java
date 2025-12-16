
package Classes;

public class Profissional {
    
    int id_profissional;
    String nome_profissional;
    char sexo;
    String especialidade;
    String telefone;

    public int getId_profissional() {
        return id_profissional;
    }

    public void setId_profissional(int id_profissional) {
        this.id_profissional = id_profissional;
    }

    public String getNome_profissional() {
        return nome_profissional;
    }

    public void setNome_profissional(String nome_profissional) {
        this.nome_profissional = nome_profissional;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Profissional() {
    }

    public Profissional(int id_profissional, String nome_profissional, char sexo, String especialidade, String telefone) {
        this.id_profissional = id_profissional;
        this.nome_profissional = nome_profissional;
        this.sexo = sexo;
        this.especialidade = especialidade;
        this.telefone = telefone;
    }
    
 @Override
    public String toString() {
        return nome_profissional; 
    }

}

