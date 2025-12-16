
package DAO.Util;

import Classes.Configuracao;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class AcessoArquivo {

        public static void grava(Configuracao CF) {
        try {
            FileOutputStream arquivo = new FileOutputStream("lib/clinica_estetica.cfg");
            ObjectOutputStream fluxo = new ObjectOutputStream(arquivo);
            fluxo.writeObject(CF);
            fluxo.flush();
            System.out.println("Dados gravados com sucesso no arquivo clinica_estetica.cfg");
        } catch (Exception e) {
            System.out.println("Falha na gravação do arquivo" + (e));

        }
    }

    public static Configuracao le() {
    String path = new java.io.File("lib/clinica_estetica.cfg").getAbsolutePath();
        
        Configuracao CF = new Configuracao ();
        try {
            FileInputStream arquivo = new FileInputStream("lib/clinica_estetica.cfg");
            ObjectInputStream fluxo = new ObjectInputStream(arquivo);
            CF = (Configuracao) fluxo.readObject();
            System.out.println("Dados lidos com sucesso no arquivo clinica_estetica.txt");
        } catch (Exception e) {
            System.out.println("Falha na leitura do arquivo" + (e));
        }
        return CF;
    }
}
