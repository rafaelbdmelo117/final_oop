package view;

import app.Professor;
import cadastros.CadastroProfessor;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;

public class MenuProfessor {

    public static Professor dadosNovoProfessor() {
        JTextField nomeField = new JTextField(15);
        JTextField cpfField = new JTextField(11);
        JTextField emailField = new JTextField(20);
        JTextField matriculaField = new JTextField(9);
        JTextField areaFormacaoField = new JTextField(20);

        while (true) {
            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
            
            panel.add(new JLabel("Nome:"));
            panel.add(nomeField);
            panel.add(new JLabel("CPF:"));
            panel.add(cpfField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Matrícula FUB:"));
            panel.add(matriculaField);
            panel.add(new JLabel("Área de Formação:"));
            panel.add(areaFormacaoField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Dados do Novo Professor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                String email = emailField.getText();
                String matriculaFUB = matriculaField.getText();
                String areaFormacao = areaFormacaoField.getText();

                boolean nomeValido = nome.matches("[\\p{L} .'-]{4,}") && nome.replace(" ", "").length() >= 4;
                boolean cpfValido = cpf.matches("\\d{11}");
                boolean matriculaValida = matriculaFUB.matches("\\d{9}");
                boolean areaFormacaoValida = areaFormacao.matches("[\\p{L} .'-]{4,}") && areaFormacao.replace(" ", "").length() >= 4;
                
                if (nomeValido && cpfValido && matriculaValida && areaFormacaoValida) {
                    return new Professor(nome, cpf, email, areaFormacao, matriculaFUB);
                } else {
                    StringBuilder mensagemErro = new StringBuilder("Dados inválidos:\n");
                    if (!nomeValido) mensagemErro.append(" - Nome deve conter apenas letras e ter no mínimo 4 letras.\n");
                    if (!cpfValido) mensagemErro.append(" - CPF deve conter 11 dígitos.\n");
                    if (!matriculaValida) mensagemErro.append(" - Matrícula FUB deve conter 9 dígitos.\n");
                    if (!areaFormacaoValida) mensagemErro.append(" - Área de Formação deve conter apenas letras e ter no mínimo 4 letras.\n");
                    JOptionPane.showMessageDialog(null, mensagemErro.toString());
                }
            } else {
                return null; // Usuário cancelou ou fechou a janela
            }
        }
    }

    private static void listarTodosProfessores(CadastroProfessor cadProfessor) {
        List<Professor> professores = cadProfessor.listarTodosProfessores();
        if (professores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum professor cadastrado.");
        } else {
            StringBuilder listaProfessores = new StringBuilder("Professores cadastrados:\n\n");
            for (Professor professor : professores) {
                listaProfessores.append(professor.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, listaProfessores.toString(), "Lista de Professores", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void MenuProfessor(CadastroProfessor cadProfessor) {
        String txt = "Informe a opção desejada \n"
                + "1 - Cadastrar professor\n"
                + "2 - Pesquisar professor\n"
                + "3 - Atualizar professor\n"
                + "4 - Remover professor\n"
                + "5 - Ver todos os professores do sistema\n"
                + "0 - Voltar para menu anterior";

        int opcao = -1;
        do {
            String strOpcao = JOptionPane.showInputDialog(txt);
            if (strOpcao == null) {
                return; // Usuário cancelou ou fechou a janela
            }

            try {
                opcao = Integer.parseInt(strOpcao);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    Professor novoProfessor = dadosNovoProfessor();
                    if (novoProfessor != null) {
                        cadProfessor.cadastrarProfessor(novoProfessor);
                        JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso.");
                    }
                    break;

                case 2:
                    String matriculaFUB = lerMatriculaFUB();
                    if (matriculaFUB != null) {
                        Professor p = cadProfessor.pesquisarProfessor(matriculaFUB);
                        if (p != null) {
                            JOptionPane.showMessageDialog(null, p.toString());
                        }
                    }
                    break;

                case 3:
                    matriculaFUB = lerMatriculaFUB();
                    if (matriculaFUB != null) {
                        Professor novoCadastro = dadosNovoProfessor();
                        if (novoCadastro != null) {
                            boolean atualizado = cadProfessor.atualizarProfessor(matriculaFUB, novoCadastro);
                            if (atualizado) {
                                JOptionPane.showMessageDialog(null, "Cadastro atualizado com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao atualizar o cadastro.");
                            }
                        }
                    }
                    break;

                case 4:
                    matriculaFUB = lerMatriculaFUB();
                    if (matriculaFUB != null) {
                        Professor remover = cadProfessor.pesquisarProfessor(matriculaFUB);
                        if (remover != null) {
                            boolean removido = cadProfessor.removerProfessor(remover);
                            if (removido) {
                                JOptionPane.showMessageDialog(null, "Professor removido com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao remover o professor.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Professor não encontrado.");
                        }
                    }
                    break;

                case 5:
                    listarTodosProfessores(cadProfessor);
                    break;

                case 0:
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private static String lerMatriculaFUB() {
        while (true) {
            try {
                String matriculaFUB = JOptionPane.showInputDialog("Informe a matrícula FUB do professor:");
                if (matriculaFUB == null) {
                    return null; // Usuário cancelou ou fechou a janela
                }
                if (matriculaFUB.matches("\\d{9}")) {
                    return matriculaFUB;
                } else {
                    JOptionPane.showMessageDialog(null, "Matrícula FUB inválida. Deve conter 9 dígitos.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao ler a matrícula FUB. Tente novamente.");
            }
        }
    }
}
