package GUI;

import DAO.DAO.AgendamentoDAO;
import DAO.DAO.ProfissionalDAO;
import Classes.Agendamento;
import Classes.Procedimento;
import Classes.Profissional;
import DAO.DAO.ErpDAOException;
import DAO.DAO.ProcedimentoDAO;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class TelaAgendamentos extends javax.swing.JFrame {

    private ArrayList<Integer> profissionalIds = new ArrayList<>();
    private ArrayList<Integer> agendamentoIds = new ArrayList<>();

    public TelaAgendamentos() {

        initComponents();

        c_procedimento.setVisible(false);

        tabela_agendamentos.setDefaultEditor(Object.class, null);
        carregarComboProfissionais();

        if (combo_profissional.getItemCount() > 0) {
            combo_profissional.setSelectedIndex(0);

            int idProfissional = pegarIdProfissionalSelecionado();

        } else {
            return;
        }

        calendario.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            @Override
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                atualizarDataAgendamento();
            }
        });

        tabela_agendamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int click = evt.getClickCount();

                if (click == 1) {
                    carregarDataNoCalendario();
                } else if (click == 2) {
                    editarAgendamento();
                }
            }
        });

        tabela_agendamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    int row = tabela_agendamentos.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        tabela_agendamentos.setRowSelectionInterval(row, row);

                        int idAgendamento = agendamentoIds.get(row);

                        int confirm = JOptionPane.showConfirmDialog(
                                TelaAgendamentos.this,
                                "Tem certeza que deseja excluir o agendamento?",
                                "Excluir",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                AgendamentoDAO dao = new AgendamentoDAO();
                                dao.ExcluirAgendamento(idAgendamento);

                                JOptionPane.showMessageDialog(TelaAgendamentos.this,
                                        "Agendamento excluído com sucesso!");

                                carregarTabelaAgendamentos();

                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(TelaAgendamentos.this,
                                        "Erro ao excluir: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

    private void calendarioOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {
        atualizarDataAgendamento();
    }

    
   // private void carregarProcedimentosPorProfissional(int idProfissional) {
   //     try {
     //       c_procedimento.removeAllItems();

       //     ProcedimentoDAO dao = new ProcedimentoDAO();
        //    ArrayList<Procedimento> lista = dao.listarProcedimentosPorProfissional(idProfissional);

//            for (Procedimento p : lista) {
  //              c_procedimento.addItem(p.getNome_procedimento());
    //        }

            // se não tiver procedimento, mostra o combo mesmo assim
     //       c_procedimento.setVisible(true);

       // } catch (Exception e) {
         //   JOptionPane.showMessageDialog(this,
           //         "Erro ao carregar procedimentos: " + e.getMessage());
     //   }
     //}

    private void carregarComboProfissionais() {
        try {
            combo_profissional.removeAllItems();
            profissionalIds.clear();

            ProfissionalDAO pdao = new ProfissionalDAO();
            ArrayList<Profissional> lista = pdao.ListarProfissional();

            for (Profissional p : lista) {
                combo_profissional.addItem(p.getNome_profissional());
                profissionalIds.add(p.getId_profissional());
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar profissionais: " + e.getMessage());
        }
    }

    private void carregarTabelaAgendamentos() {
        try {
            int index = combo_profissional.getSelectedIndex();
            if (index < 0) {
                return;
            }

            int idProfissional = profissionalIds.get(index);

            AgendamentoDAO dao = new AgendamentoDAO();
            ArrayList<Agendamento> lista = dao.ListarAgendamentoPorProfissional(idProfissional);

            DefaultTableModel modelo = (DefaultTableModel) tabela_agendamentos.getModel();
            modelo.setRowCount(0);
            agendamentoIds.clear();

            for (Agendamento a : lista) {
                modelo.addRow(new Object[]{
                    a.getNome_cliente(),
                    a.getNome_profissional(),
                    a.getNome_procedimento(),
                    a.getHora(),
                    a.getStatus()
                });

                agendamentoIds.add(a.getId_agendamento());
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar agendamentos: " + e.getMessage());
        }
    }

    private void carregarDataNoCalendario() {
        int row = tabela_agendamentos.getSelectedRow();
        if (row < 0) {
            return;
        }

        int idAgendamento = agendamentoIds.get(row);

        try {
            AgendamentoDAO dao = new AgendamentoDAO();
            Agendamento a = dao.ProcurarAgendamento(idAgendamento);

            if (a != null && a.getData() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(a.getData());
                calendario.setSelectedDate(cal);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar data: " + e.getMessage());
        }
    }

    private void editarAgendamento() {
        int row = tabela_agendamentos.getSelectedRow();
        if (row < 0) {
            return;
        }

        int idAgendamento = agendamentoIds.get(row);

        try {
            AgendamentoDAO dao = new AgendamentoDAO();
            Agendamento a = dao.ProcurarAgendamento(idAgendamento);

            if (a == null) {
                return;
            }

            String novaHoraStr = JOptionPane.showInputDialog(this, "Nova hora (HH:MM):", a.getHora().toString());
            if (novaHoraStr == null) {
                return;
            }

            novaHoraStr = novaHoraStr.trim();

            if (!novaHoraStr.matches("\\d{2}:\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Formato inválido! Use HH:MM (exemplo: 09:30)");
                return;
            }

            Time novaHora = Time.valueOf(novaHoraStr + ":00");

            String novoStatus = JOptionPane.showInputDialog(this, "Status:", a.getStatus());
            if (novoStatus == null) {
                return;
            }

            a.setHora(novaHora);
            a.setStatus(novoStatus);

            dao.AlterarAgendamento(a);

            carregarTabelaAgendamentos();

            JOptionPane.showMessageDialog(this, "Agendamento atualizado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }

    private void atualizarDataAgendamento() {
        int row = tabela_agendamentos.getSelectedRow();
        if (row < 0) {
            return;
        }

        int idAgendamento = agendamentoIds.get(row);

        try {
            Calendar cal = calendario.getSelectedDate();
            Date novaData = cal.getTime();

            AgendamentoDAO dao = new AgendamentoDAO();
            Agendamento a = dao.ProcurarAgendamento(idAgendamento);

            if (a != null) {
                a.setData(novaData);
                dao.AlterarAgendamento(a);
                System.out.println("Data atualizada: " + novaData);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar data: " + e.getMessage());
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

        dateChooserPanel1 = new datechooser.beans.DateChooserPanel();
        combo_profissional = new javax.swing.JComboBox<>();
        calendario = new datechooser.beans.DateChooserPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_agendamentos = new javax.swing.JTable();
        c_procedimento = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Agendamento");

        combo_profissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_profissionalActionPerformed(evt);
            }
        });

        tabela_agendamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cliente", "Profissional", "Procedimento", "Hora", "Status"
            }
        ));
        jScrollPane1.setViewportView(tabela_agendamentos);

        c_procedimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        c_procedimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_procedimentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_profissional, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(c_procedimento, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(combo_profissional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(c_procedimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private int pegarIdProfissionalSelecionado() {
        int index = combo_profissional.getSelectedIndex();

        if (index < 0 || profissionalIds.isEmpty()) {
            return -1;
        }

        return profissionalIds.get(index);
    }


    private void c_procedimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_procedimentoActionPerformed

    }//GEN-LAST:event_c_procedimentoActionPerformed

    private void combo_profissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_profissionalActionPerformed
        carregarTabelaAgendamentos();

        int id = pegarIdProfissionalSelecionado();
      //  carregarProcedimentosPorProfissional(id);

    }//GEN-LAST:event_combo_profissionalActionPerformed

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
            java.util.logging.Logger.getLogger(TelaAgendamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAgendamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAgendamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAgendamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TelaAgendamentos().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> c_procedimento;
    private datechooser.beans.DateChooserPanel calendario;
    private javax.swing.JComboBox<String> combo_profissional;
    private datechooser.beans.DateChooserPanel dateChooserPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela_agendamentos;
    // End of variables declaration//GEN-END:variables
}
