package GUI;

import DAO.DAO.AgendamentoDAO;
import DAO.DAO.ClienteDAO;
import DAO.DAO.ErpDAOException;
import DAO.DAO.ProcedimentoDAO;
import DAO.DAO.ProfissionalDAO;
import GUI.Configuracoes;
import GUI.InsereAgendamento;
import GUI.InsereClientes;
import GUI.InsereProcedimento;
import GUI.InsereProfissional;
import GUI.TelaAgendamentos;
import GUI.TelaCliente;
import GUI.TelaProcedimentos;
import GUI.TelaProfissional;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.*;
import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class Principal extends JFrame {

 
    private JLabel lblBoasVindas;
    private JLabel lblDataHora;

public Principal(String nomeProfissional) throws ErpDAOException {
    initComponents();

    JPanel painelHeader = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
          Color cor1 = new Color(220, 220, 220); 
Color cor2 = new Color(255, 210, 190); 

            GradientPaint gp = new GradientPaint(0, 0, cor1, 0, getHeight(), cor2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    };
    painelHeader.setPreferredSize(new Dimension(900, 120));

    lblBoasVindas = new JLabel("Bem Vindo, " + nomeProfissional + "!");
    lblBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 28));
    lblBoasVindas.setForeground(new Color(60, 60, 60));
    lblBoasVindas.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 0));

    lblDataHora = new JLabel(getDataHoraAtual());
    lblDataHora.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    lblDataHora.setForeground(new Color(60, 60, 60));
    lblDataHora.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDataHora.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 30));

    painelHeader.add(lblBoasVindas, BorderLayout.WEST);
    painelHeader.add(lblDataHora, BorderLayout.EAST);

    JPanel painelCentral = new JPanel();
    painelCentral.setBackground(new Color(245, 245, 245));
    painelCentral.setLayout(new GridLayout(1, 4, 20, 20));
    painelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    ClienteDAO clienteDAO = new ClienteDAO();
    ProfissionalDAO profissionalDAO = new ProfissionalDAO();
    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

    int totalClientes = 0;
    int totalProfissionais = 0;
    int totalProcedimentos = 0;
    int totalAgendamentos = 0;

    try {
        totalClientes = clienteDAO.contarClientes();
        totalProfissionais = profissionalDAO.contarProfissionais();
        totalProcedimentos = procedimentoDAO.contarProcedimentos();
        totalAgendamentos = agendamentoDAO.contarAgendamentos();
    } catch (ErpDAOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
    }

    painelCentral.add(criarCard("Agendamentos", String.valueOf(totalAgendamentos), new Color(255, 183, 128)));
    painelCentral.add(criarCard("Clientes", String.valueOf(totalClientes), new Color(129, 212, 250)));
    painelCentral.add(criarCard("Profissionais", String.valueOf(totalProfissionais), new Color(100, 181, 246)));
    painelCentral.add(criarCard("Procedimentos", String.valueOf(totalProcedimentos), new Color(255, 138, 128)));

    JLabel lblFrase = new JLabel("<html><div style='text-align:center;'>Proporcionando a melhor experiência para cada cliente.</div></html>");
    lblFrase.setFont(new Font("Segoe UI", Font.PLAIN, 20));
    lblFrase.setForeground(new Color(60, 60, 60));
    lblFrase.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    lblFrase.setHorizontalAlignment(SwingConstants.CENTER);

    getContentPane().setLayout(new BorderLayout());
    add(painelHeader, BorderLayout.NORTH);
    add(painelCentral, BorderLayout.CENTER);
    add(lblFrase, BorderLayout.SOUTH);

    Timer timer = new Timer(1000, e -> lblDataHora.setText(getDataHoraAtual()));
    timer.start();
}

private JPanel criarCard(String titulo, String valor, Color cor) {
   JPanel card = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
           Color cor1 = new Color(220, 220, 220); 
Color cor2 = new Color(255, 210, 190);

            GradientPaint gp = new GradientPaint(0, 0, cor1, 0, getHeight(), cor2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    };
    card.setLayout(new BorderLayout());
    card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    card.setCursor(new Cursor(Cursor.HAND_CURSOR));

JLabel lblTitulo = new JLabel("<html><u>" + titulo + "</u></html>");
    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
    lblTitulo.setForeground(Color.BLACK);

    JLabel lblValor = new JLabel(valor);
    lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
    lblValor.setForeground(Color.BLACK);
    lblValor.setHorizontalAlignment(SwingConstants.CENTER);

    card.add(lblTitulo, BorderLayout.NORTH);
    card.add(lblValor, BorderLayout.CENTER);
    return card;
}

private String getDataHoraAtual() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy");
    return java.time.LocalDateTime.now().format(formatter);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        NavBar = new javax.swing.JMenuBar();
        menu_cadastro = new javax.swing.JMenu();
        JMenu_cliente = new javax.swing.JMenu();
        item_cadastrar_cliente = new javax.swing.JMenuItem();
        item_listar_cliente = new javax.swing.JMenuItem();
        menu__profissional = new javax.swing.JMenu();
        Item_cadastrar_profissional = new javax.swing.JMenuItem();
        item_listar_profissional = new javax.swing.JMenuItem();
        menu_item_vinculo = new javax.swing.JMenuItem();
        JMenu_procedimento = new javax.swing.JMenu();
        item_cadastrar_procedimento = new javax.swing.JMenuItem();
        item_listar_procedimento = new javax.swing.JMenuItem();
        item_menu_sair = new javax.swing.JMenuItem();
        menu_agendamento = new javax.swing.JMenu();
        item_cadastrar_agendamento = new javax.swing.JMenuItem();
        item_listar_agendamentos = new javax.swing.JMenuItem();
        menu_ferramentas = new javax.swing.JMenu();
        item_configurações = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clínica Estética");
        setFocusTraversalPolicyProvider(true);

        menu_cadastro.setMnemonic('C');
        menu_cadastro.setText("Cadastro");
        menu_cadastro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        JMenu_cliente.setMnemonic('e');
        JMenu_cliente.setText("Cliente");
        JMenu_cliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        item_cadastrar_cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_cadastrar_cliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_cadastrar_cliente.setMnemonic('C');
        item_cadastrar_cliente.setText("Cadastrar");
        item_cadastrar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_cadastrar_clienteActionPerformed(evt);
            }
        });
        JMenu_cliente.add(item_cadastrar_cliente);

        item_listar_cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_listar_cliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_listar_cliente.setMnemonic('L');
        item_listar_cliente.setText("Listar");
        item_listar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_listar_clienteActionPerformed(evt);
            }
        });
        JMenu_cliente.add(item_listar_cliente);

        menu_cadastro.add(JMenu_cliente);

        menu__profissional.setMnemonic('o');
        menu__profissional.setText("Profissional");
        menu__profissional.setToolTipText("");
        menu__profissional.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        Item_cadastrar_profissional.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Item_cadastrar_profissional.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Item_cadastrar_profissional.setMnemonic('C');
        Item_cadastrar_profissional.setText("Cadastrar");
        Item_cadastrar_profissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Item_cadastrar_profissionalActionPerformed(evt);
            }
        });
        menu__profissional.add(Item_cadastrar_profissional);

        item_listar_profissional.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_listar_profissional.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_listar_profissional.setMnemonic('L');
        item_listar_profissional.setText("Listar");
        item_listar_profissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_listar_profissionalActionPerformed(evt);
            }
        });
        menu__profissional.add(item_listar_profissional);

        menu_item_vinculo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_item_vinculo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        menu_item_vinculo.setMnemonic('V');
        menu_item_vinculo.setText("Vincular Procedimentos");
        menu_item_vinculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_item_vinculoActionPerformed(evt);
            }
        });
        menu__profissional.add(menu_item_vinculo);

        menu_cadastro.add(menu__profissional);

        JMenu_procedimento.setMnemonic('P');
        JMenu_procedimento.setText("Procedimento");
        JMenu_procedimento.setFocusable(false);
        JMenu_procedimento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        item_cadastrar_procedimento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_cadastrar_procedimento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_cadastrar_procedimento.setMnemonic('C');
        item_cadastrar_procedimento.setText("Cadastrar");
        item_cadastrar_procedimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_cadastrar_procedimentoActionPerformed(evt);
            }
        });
        JMenu_procedimento.add(item_cadastrar_procedimento);

        item_listar_procedimento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_listar_procedimento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_listar_procedimento.setMnemonic('L');
        item_listar_procedimento.setText("Listar");
        item_listar_procedimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_listar_procedimentoActionPerformed(evt);
            }
        });
        JMenu_procedimento.add(item_listar_procedimento);

        menu_cadastro.add(JMenu_procedimento);

        item_menu_sair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_menu_sair.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_menu_sair.setText("Sair");
        item_menu_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_menu_sairActionPerformed(evt);
            }
        });
        menu_cadastro.add(item_menu_sair);

        NavBar.add(menu_cadastro);

        menu_agendamento.setMnemonic('A');
        menu_agendamento.setText("Agendamento");
        menu_agendamento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        menu_agendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_agendamentoActionPerformed(evt);
            }
        });

        item_cadastrar_agendamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_cadastrar_agendamento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_cadastrar_agendamento.setMnemonic('C');
        item_cadastrar_agendamento.setText("Cadastrar");
        item_cadastrar_agendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_cadastrar_agendamentoActionPerformed(evt);
            }
        });
        menu_agendamento.add(item_cadastrar_agendamento);

        item_listar_agendamentos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_listar_agendamentos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_listar_agendamentos.setMnemonic('L');
        item_listar_agendamentos.setText("Listar ");
        item_listar_agendamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_listar_agendamentosActionPerformed(evt);
            }
        });
        menu_agendamento.add(item_listar_agendamentos);

        NavBar.add(menu_agendamento);

        menu_ferramentas.setMnemonic('F');
        menu_ferramentas.setText("Ferramentas");
        menu_ferramentas.setToolTipText("");
        menu_ferramentas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        item_configurações.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        item_configurações.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        item_configurações.setMnemonic('C');
        item_configurações.setText("Configurações");
        item_configurações.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_configuraçõesActionPerformed(evt);
            }
        });
        menu_ferramentas.add(item_configurações);

        NavBar.add(menu_ferramentas);

        setJMenuBar(NavBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void item_configuraçõesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_configuraçõesActionPerformed
        Configuracoes JanelaConfigura = new Configuracoes();
        JanelaConfigura.setLocationRelativeTo(null);
        JanelaConfigura.setVisible(true);    }//GEN-LAST:event_item_configuraçõesActionPerformed

    private void item_listar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_listar_clienteActionPerformed
        TelaCliente JanelaCliente = new TelaCliente();
        JanelaCliente.setLocationRelativeTo(null);
        JanelaCliente.setVisible(true);
    }//GEN-LAST:event_item_listar_clienteActionPerformed

    private void item_cadastrar_agendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_cadastrar_agendamentoActionPerformed
        InsereAgendamento JanelaAgendamento = new InsereAgendamento();
        JanelaAgendamento.setLocationRelativeTo(null);
        JanelaAgendamento.setVisible(true);
    }//GEN-LAST:event_item_cadastrar_agendamentoActionPerformed

    private void item_cadastrar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_cadastrar_clienteActionPerformed
        InsereClientes JanelaClientes = new InsereClientes();
        JanelaClientes.setLocationRelativeTo(null);
        JanelaClientes.setVisible(true);
    }//GEN-LAST:event_item_cadastrar_clienteActionPerformed

    private void Item_cadastrar_profissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Item_cadastrar_profissionalActionPerformed
        InsereProfissional JanelaProfissional = new InsereProfissional();
        JanelaProfissional.setLocationRelativeTo(null);
        JanelaProfissional.setVisible(true);
    }//GEN-LAST:event_Item_cadastrar_profissionalActionPerformed

    private void item_cadastrar_procedimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_cadastrar_procedimentoActionPerformed
        InsereProcedimento JanelaProcedimento = new InsereProcedimento();
        JanelaProcedimento.setLocationRelativeTo(null);
        JanelaProcedimento.setVisible(true);
    }//GEN-LAST:event_item_cadastrar_procedimentoActionPerformed

    private void item_listar_profissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_listar_profissionalActionPerformed
        TelaProfissional JanelaProfissional = new TelaProfissional();
        JanelaProfissional.setLocationRelativeTo(null);
        JanelaProfissional.setVisible(true);
    }//GEN-LAST:event_item_listar_profissionalActionPerformed

    private void item_listar_procedimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_listar_procedimentoActionPerformed
        TelaProcedimentos JanelaProcedimento = new TelaProcedimentos();
        JanelaProcedimento.setLocationRelativeTo(null);
           JanelaProcedimento.setVisible(true);     }//GEN-LAST:event_item_listar_procedimentoActionPerformed

    private void menu_agendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_agendamentoActionPerformed

    }//GEN-LAST:event_menu_agendamentoActionPerformed

    private void item_menu_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_menu_sairActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Saindo...");
        System.exit(0);    }//GEN-LAST:event_item_menu_sairActionPerformed

    private void item_listar_agendamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_listar_agendamentosActionPerformed
        TelaAgendamentos JanelaAgendamento = new TelaAgendamentos();
        JanelaAgendamento.setLocationRelativeTo(null);
           JanelaAgendamento.setVisible(true);      }//GEN-LAST:event_item_listar_agendamentosActionPerformed

    private void menu_item_vinculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_item_vinculoActionPerformed
VincularProcedimentoProfissional JanelaVinculos = new VincularProcedimentoProfissional();
        JanelaVinculos.setLocationRelativeTo(null);
           JanelaVinculos.setVisible(true);      
    }//GEN-LAST:event_menu_item_vinculoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {

             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    java.awt.EventQueue.invokeLater(() -> {
        try {
            new Principal("Usuário").setVisible(true);
        } catch (ErpDAOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage());
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Item_cadastrar_profissional;
    private javax.swing.JMenu JMenu_cliente;
    private javax.swing.JMenu JMenu_procedimento;
    private javax.swing.JMenuBar NavBar;
    private javax.swing.JMenuItem item_cadastrar_agendamento;
    private javax.swing.JMenuItem item_cadastrar_cliente;
    private javax.swing.JMenuItem item_cadastrar_procedimento;
    private javax.swing.JMenuItem item_configurações;
    private javax.swing.JMenuItem item_listar_agendamentos;
    private javax.swing.JMenuItem item_listar_cliente;
    private javax.swing.JMenuItem item_listar_procedimento;
    private javax.swing.JMenuItem item_listar_profissional;
    private javax.swing.JMenuItem item_menu_sair;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenu menu__profissional;
    private javax.swing.JMenu menu_agendamento;
    private javax.swing.JMenu menu_cadastro;
    private javax.swing.JMenu menu_ferramentas;
    private javax.swing.JMenuItem menu_item_vinculo;
    // End of variables declaration//GEN-END:variables

}
