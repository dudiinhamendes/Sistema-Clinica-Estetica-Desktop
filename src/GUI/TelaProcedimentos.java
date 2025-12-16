package GUI;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import DAO.DAO.ProcedimentoDAO;
import Classes.Procedimento;
import java.math.BigDecimal;
import javax.swing.UIManager;


public class TelaProcedimentos extends javax.swing.JFrame {

    private ArrayList<Integer> procedimentoIds = new ArrayList<>();

    public TelaProcedimentos() {
        initComponents();
        tabela_procedimento.setDefaultEditor(Object.class, null);

        carregarProcedimentosNoCombo();

        if (!procedimentoIds.isEmpty()) {
            combo_box_procedimento.setSelectedIndex(0);
            carregarTabelaProcedimento(procedimentoIds.get(0));
        }

        tabela_procedimento.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    tabela_procedimentoMouseClicked(evt);
                }
            }
        });

        tabela_procedimento.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) mostrarConfirmacaoExclusao(evt);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) mostrarConfirmacaoExclusao(evt);
            }
        });
    }

    private void mostrarConfirmacaoExclusao(MouseEvent evt) {
        int linha = tabela_procedimento.rowAtPoint(evt.getPoint());
        if (linha < 0) return;

        tabela_procedimento.setRowSelectionInterval(linha, linha);

        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este procedimento?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                int id = procedimentoIds.get(linha);
                ProcedimentoDAO dao = new ProcedimentoDAO();
                dao.ExcluirProcedimento(id);

                carregarProcedimentosNoCombo();

                if (!procedimentoIds.isEmpty()) {
                    combo_box_procedimento.setSelectedIndex(0);
                    carregarTabelaProcedimento(procedimentoIds.get(0));
                }

                JOptionPane.showMessageDialog(this, "Procedimento excluído!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private void carregarProcedimentosNoCombo() {
        try {
            combo_box_procedimento.removeAllItems();
            procedimentoIds.clear();

            ProcedimentoDAO dao = new ProcedimentoDAO();
            ArrayList<Procedimento> lista = dao.ListarProcedimento();

            for (Procedimento p : lista) {
                combo_box_procedimento.addItem(p.getNome_procedimento());
                procedimentoIds.add(p.getId_procedimento());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar procedimentos: " + e.getMessage());
        }
    }

    private void tabela_procedimentoMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {

            try {
                int index = combo_box_procedimento.getSelectedIndex();
                if (index < 0) return;

                int id = procedimentoIds.get(index);

                int row = tabela_procedimento.getSelectedRow();
                if (row < 0) return;

                String nome = tabela_procedimento.getValueAt(row, 0).toString();
                String duracao = tabela_procedimento.getValueAt(row, 1).toString();
                String preco = tabela_procedimento.getValueAt(row, 2).toString();

                String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", nome);
                if (novoNome == null || novoNome.trim().isEmpty()) return;

                String novaDuracao = JOptionPane.showInputDialog(this, "Nova duração:", duracao);
                if (novaDuracao == null || novaDuracao.trim().isEmpty()) return;

                String novoPreco = JOptionPane.showInputDialog(this, "Novo preço:", preco);
                if (novoPreco == null || novoPreco.trim().isEmpty()) return;

               Procedimento p = new Procedimento();
                 p.setId_procedimento(id);
                 p.setNome_procedimento(novoNome);
                 p.setDuracao(Integer.parseInt(novaDuracao));
                 p.setPreco(new BigDecimal(novoPreco));

                ProcedimentoDAO dao = new ProcedimentoDAO();
                dao.AlterarProcedimento(p);

                carregarTabelaProcedimento(id);

                JOptionPane.showMessageDialog(this, "Procedimento atualizado!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
            }
        }
    }

private void carregarTabelaProcedimento(int idSelecionado) {
    try {
        ProcedimentoDAO dao = new ProcedimentoDAO();
        ArrayList<Procedimento> procedimentos = dao.ListarProcedimento();

        DefaultTableModel modelo = (DefaultTableModel) tabela_procedimento.getModel();
        modelo.setRowCount(0);
        procedimentoIds.clear(); 

        for (Procedimento p : procedimentos) {
            modelo.addRow(new Object[]{
                p.getNome_procedimento(),
                p.getDuracao(),
                p.getPreco()
            });
            procedimentoIds.add(p.getId_procedimento()); // adiciona na mesma ordem da tabela
        }

        // Seleciona a linha do id que você quer
        if (idSelecionado > 0) {
            int index = procedimentoIds.indexOf(idSelecionado);
            if (index >= 0) tabela_procedimento.setRowSelectionInterval(index, index);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + e.getMessage());
    }
}

   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        combo_box_procedimento = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_procedimento = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Procedimento");

        combo_box_procedimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_procedimentoActionPerformed(evt);
            }
        });

        tabela_procedimento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "Duração", "Preço"
            }
        ));
        jScrollPane1.setViewportView(tabela_procedimento);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_box_procedimento, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(combo_box_procedimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void combo_box_procedimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_procedimentoActionPerformed
 int index = combo_box_procedimento.getSelectedIndex();
        if (index < 0 || procedimentoIds.isEmpty()) return;

        int id = procedimentoIds.get(index);
        carregarTabelaProcedimento(id);    }//GEN-LAST:event_combo_box_procedimentoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
         try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(TelaProcedimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(TelaProcedimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(TelaProcedimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(TelaProcedimentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new TelaProcedimentos().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combo_box_procedimento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela_procedimento;
    // End of variables declaration//GEN-END:variables
}
