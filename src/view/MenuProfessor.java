package view;

import app.CampoEmBrancoException;
import app.Professor;
import cadastros.CadastroProfessor;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuProfessor {

    //metodo que coleta os dados de um novo professor
    public static Professor dadosNovoProfessor() throws CampoEmBrancoException {

        //criacao de entradas para os dados do professor
        JTextField nomeField = new JTextField(15);
        JTextField cpfField = new JTextField(11);
        JTextField emailField = new JTextField(20);
        JTextField matriculaField = new JTextField(9);
        JTextField areaFormacaoField = new JTextField(20);

        //loop infinito para garantir que o usuario escreva os dados corretamente
        while (true) {

            //criacao do painel com layout para organizar os campos
            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

            //add campos ao painel
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

            //mostra o painel para o usuario e espera a acao (ok ou canccel)
            int result = JOptionPane.showConfirmDialog(null, panel, "Dados do Novo Professor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            //se o usuario clicar ok, valida os dados inseridos
            if (result == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText();
                if (nome.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'Nome' em branco.");
                }

                String cpf = cpfField.getText();
                if (cpf.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'CPF' em branco.");
                }

                String email = emailField.getText();
                if (email.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'Email' em branco.");
                }

                String matriculaFUB = matriculaField.getText();
                if (matriculaFUB.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'Matrícula' em branco.");
                }

                String areaFormacao = areaFormacaoField.getText();
                if (areaFormacao.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'Área de formação' em branco.");
                }

                //validacao de formato de cada campo
                boolean nomeValido = nome.matches("[\\p{L} .'-]{4,}") && nome.replace(" ", "").length() >= 4; //verifica se o nome contem, pelo menos, 4 caracteres. remove tos os espacos do nome e verifica a qtt de caracteres >= 4
                boolean cpfValido = cpf.matches("\\d{11}");
                boolean emailValido = email.contains("@");
                boolean matriculaValida = matriculaFUB.matches("\\d{9}");
                boolean areaFormacaoValida = areaFormacao.matches("[\\p{L} .'-]{4,}") && areaFormacao.replace(" ", "").length() >= 4; //verifica se a area de formacao contem, pelo menos, 4 caracteres. remove tos os espacos do nome e verifica a qtt de caracteres >= 4

                //verifica se todos os dados foram validos, cria um novo objeto professor
                if (nomeValido && cpfValido && emailValido && matriculaValida && areaFormacaoValida) {
                    return new Professor(nome, cpf, email, areaFormacao, matriculaFUB);
                } else {
                    //se algum dado for invalido, cria uma mensagem de erro
                    StringBuilder mensagemErro = new StringBuilder("Dados inválidos:\n");
                    if (!nomeValido) mensagemErro.append(" - Nome deve conter apenas letras e ter no mínimo 4 letras.\n");
                    if (!cpfValido) mensagemErro.append(" - CPF deve conter 11 dígitos.\n");
                    if (!emailValido) mensagemErro.append(" - Insira um email válido.\n");
                    if (!matriculaValida) mensagemErro.append(" - Matrícula FUB deve conter 9 dígitos.\n");
                    if (!areaFormacaoValida) mensagemErro.append(" - Área de Formação deve conter apenas letras e ter no mínimo 4 letras.\n");
                    JOptionPane.showMessageDialog(null, mensagemErro.toString());
                }
            } else {
                return null; //usuario cancelou a operacao ou fechou a janela
            }
        }
    }

    //metodo que lista todos os professores cadastrados
    private static void listarTodosProfessores(CadastroProfessor cadProfessor) {
        List<Professor> professores = cadProfessor.listarTodosProfessores();
        //verifica se ha professores cadastrados
        if (professores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum professor cadastrado.");
        } else {
            //concatena os dados de todos os professores em uma string
            StringBuilder listaProfessores = new StringBuilder("Professores cadastrados:\n\n");
            for (Professor professor : professores) {
                listaProfessores.append(professor.toString()).append("\n");
            }
            //mensagem com lista para o usuario
            JOptionPane.showMessageDialog(null, listaProfessores.toString(), "Lista de Professores", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //metodo que mostra menu de opcoes do professor
    public static void MenuProfessor(CadastroProfessor cadProfessor) {
        String txt = "Informe a opção desejada \n"
                + "1 - Cadastrar professor\n"
                + "2 - Pesquisar professor\n"
                + "3 - Atualizar professor\n"
                + "4 - Remover professor\n"
                + "5 - Ver todos os professores do sistema\n"
                + "0 - Voltar para menu anterior";

        int opcao;
        try {
            //loop ate que o usuario escolha a opcao '0' (voltar)
            do {
                //mostra o menu e le a opcao escolhida pelo usuario
                String strOpcao = JOptionPane.showInputDialog(txt);
                if (strOpcao == null) {
                    return; //usuario cancelou
                }

                if (strOpcao.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou um campo em branco.");
                }

                try {
                    opcao = Integer.parseInt(strOpcao);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha um número.");
                    opcao = -1; //mantem o loop
                    continue;
                }

                //swith com as diferentes opcoes do menu
                switch (opcao) {
                    case 1: {
                        //cadastrar professor
                        Professor novoProfessor = dadosNovoProfessor();
                        if (novoProfessor != null) {
                            int resultado = cadProfessor.cadastrarProfessor(novoProfessor);
                            switch (resultado) {
                                case -1:
                                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar professor. Matrícula já existente.");
                                    break;
                                case -2:
                                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar professor. CPF já existente.");
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso.");
                                    break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        //pesquisar professor
                        String matriculaFUB = lerMatriculaFUB();
                        Professor p = cadProfessor.pesquisarProfessor(matriculaFUB);
                        if (p != null) {
                            JOptionPane.showMessageDialog(null, p.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Professor não encontrado.");
                        }
                        break;
                    }
                    case 3: {
                        //atualizar professor
                        String matriculaFUB = lerMatriculaFUB();
                        Professor professorExistente = cadProfessor.pesquisarProfessor(matriculaFUB);
                        if (professorExistente != null) {
                            Professor novoCadastro = dadosNovoProfessor();
                            if (novoCadastro != null) {
                                boolean atualizado = cadProfessor.atualizarProfessor(matriculaFUB, novoCadastro);
                                JOptionPane.showMessageDialog(null, atualizado ? "Cadastro atualizado." : "Erro ao atualizar o cadastro.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Professor com essa matrícula não encontrado.");
                        }
                        break;
                    }
                    case 4: {
                        //remover professor
                        String matriculaFUB = lerMatriculaFUB();
                        Professor remover = cadProfessor.pesquisarProfessor(matriculaFUB);
                        if (remover != null) {
                            boolean removido = cadProfessor.removerProfessor(remover);
                            JOptionPane.showMessageDialog(null, removido ? "Professor removido com sucesso." : "Erro ao remover professor.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Professor não encontrado.");
                        }
                        break;
                    }
                    case 5: {
                        //ver todos os professores
                        listarTodosProfessores(cadProfessor);
                        break;
                    }
                    case 0: {
                        //voltar
                        break;
                    }
                    default: {
                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                        break;
                    }
                }
            } while (opcao != 0);
            //repete o loop ate a opcao 0 ser escolhida
        } catch (CampoEmBrancoException cbe) {
            JOptionPane.showMessageDialog(null, cbe.getMessage());
        }
    }

    //metodo auxiliar para ler a matricula do professor
    private static String lerMatriculaFUB() throws CampoEmBrancoException {
        while (true) {
            String matriculaFUB = JOptionPane.showInputDialog("Informe a matrícula do professor: ");
            if (matriculaFUB == null || matriculaFUB.isEmpty()) {
                throw new CampoEmBrancoException("Você deixou a matrícula em branco");
            }
            if (matriculaFUB.matches("\\d{9}")) {
                //verifica se a matricula tem 9 digitos
                return matriculaFUB;
            } else {
                //mensagem para o usuario
                JOptionPane.showMessageDialog(null, "Matrícula inválida. Deve conter 9 dígitos.");
            }
        }
    }
}
