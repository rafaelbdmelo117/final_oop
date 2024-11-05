package view;

import app.Aluno;
import app.CampoEmBrancoException;
import cadastros.CadastroAluno;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;

public class MenuAluno {

    public static Aluno dadosNovoAluno() throws CampoEmBrancoException{ //metodo que cria uma interface para o usuario inserir os dados de cadastro de um aluno
                                            //seja para adicionar um novo aluno ou para atualizar um aluno

        //os elementos abaixo configuram os campos que vao receber as informacoes do usuario
        JTextField nomeField = new JTextField(15);
        JTextField cpfField = new JTextField(11);
        JTextField emailField = new JTextField(20);
        JTextField matriculaField = new JTextField(9);
        JTextField cursoField = new JTextField(15);

        while (true) {
            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10)); //cria uma interface para receber os campos acima

            //os elementos abaixo criam uma interface para o usuario interagir com os campos que foram criados
            panel.add(new JLabel("Nome:"));
            panel.add(nomeField);
            panel.add(new JLabel("CPF:"));
            panel.add(cpfField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Matrícula:"));
            panel.add(matriculaField);
            panel.add(new JLabel("Curso:"));
            panel.add(cursoField);

            //botao ok ou cancelar
            int result = JOptionPane.showConfirmDialog(null, panel, "Dados do Novo Aluno", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);


            if (result == JOptionPane.OK_OPTION) { //se o usuario apertar em ok, entra na parte de verificacao de dados inseridos para o novo aluno

                String nome = nomeField.getText(); //o elemento nome recebe o que o usuario digitou no campo nomeField
                if (nome.isEmpty()) { //verifica se o campo ficou em branco
                    throw new CampoEmBrancoException("Você deixou o campo 'Nome' em branco"); //lanca a CampoEmBrancoException
                }
                //o mesmo processo descrito acima se repete para cada um dos campos

                String cpf = cpfField.getText();
                if (cpf.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'CPF' em branco");
                }

                String email = emailField.getText();
                if (email.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'Email' em branco");
                }

                String matricula = matriculaField.getText();
                if (matricula.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'Matrícula' em branco");
                }

                String curso = cursoField.getText();
                if (curso.isEmpty()) {
                    throw new CampoEmBrancoException("Você deixou o campo 'Curso' em branco");
                }

                boolean nomeValido = nome.matches("[\\p{L} .'-]{4,}"); //aceita letras, acentos e alguns caracteres especiais
                boolean cpfValido = cpf.matches("\\d{11}"); //aceita 11 digitos
                boolean emailValido = email.contains("@"); //so valida o email se este possuir @
                boolean matriculaValida = matricula.matches("\\d{9}"); //aceita 9 digitos
                boolean cursoValido = curso.matches("[\\p{L} .'-]{4,}"); //aceita letras, acentos e alguns caracteres especiais

                if (nomeValido && cpfValido && emailValido && matriculaValida && cursoValido) { //verifica se tudo esta validado
                    return new Aluno(nome, cpf, email, matricula, curso); //retorna um novo objeto aluno com os dados passados
                } else { //cria uma mensagem de erro especifica para cada problema
                    StringBuilder mensagemErro = new StringBuilder("Dados inválidos:\n");
                    if (!nomeValido) mensagemErro.append(" - Nome deve conter pelo menos 4 caracteres e pode incluir letras acentuadas e caracteres especiais como espaço, ponto, apóstrofo e hífen.\n");
                    if (!cpfValido) mensagemErro.append(" - CPF deve conter 11 dígitos.\n");
                    if (!emailValido) mensagemErro.append(" - Insira um email válido.\n");
                    if (!matriculaValida) mensagemErro.append(" - Matrícula deve conter 9 dígitos.\n");
                    if (!cursoValido) mensagemErro.append(" - Curso deve conter pelo menos 4 caracteres e pode incluir letras acentuadas e caracteres especiais.\n");
                    JOptionPane.showMessageDialog(null, mensagemErro.toString());
                }
            } else { //se o usuario aperta em cancelar, retorna null
                return null;
            }
        }
    }

    private static void listarTodosAlunos(CadastroAluno cadAluno) { //metodo que exibe uma lista na interface para ver todos os alunos
        List<Aluno> alunos = cadAluno.listarTodosAlunos(); //cria uma lista de alunos que recebe a lista de alunos cadastrada (a .listarTodosAlunos() vem de CadastroAluno)
        if (alunos.isEmpty()) { //verifica se a lista esta vazia
            JOptionPane.showMessageDialog(null, "Nenhum aluno cadastrado."); //exibe uma mensagem informando que a lista esta vazia
        } else { //caso a lista nao esteja vazia
            StringBuilder listaAlunos = new StringBuilder("Alunos cadastrados:\n\n"); //usa a classe StringBuilder para criar um novo objeto StringBuilder referenciado por listaAlunos
            for (Aluno aluno : alunos) { //itera sobre toda a lista de alunos
                listaAlunos.append(aluno.toString()).append("\n"); //adiciona as informacoes de cada aluno para a lista a ser exibida na interface do usuario
            }
            JOptionPane.showMessageDialog(null, listaAlunos.toString(), "Lista de Alunos", JOptionPane.INFORMATION_MESSAGE); //exibe a lista completa
        }
    }

    public static void MenuAluno(CadastroAluno cadAluno) throws CampoEmBrancoException { //metodo que exibe o menu de opcoes para alunos ao usuario
        String txt = "Informe a opção desejada \n"
                + "1 - Cadastrar aluno\n"
                + "2 - Pesquisar aluno\n"
                + "3 - Atualizar aluno\n"
                + "4 - Remover aluno\n"
                + "5 - Ver todos os alunos do sistema\n"
                + "0 - Voltar para menu anterior";
        //txt e a String que 'conversa' com o usuario

        int escolhido; //elemento de referencia para o switch e para o do while (neste ultimo, e para manter o programa rodando)
        try {
            do {
                String strEscolhido = JOptionPane.showInputDialog(txt);

                if (strEscolhido == null) { //verifica se o usuario clicou em 'cancelar' ou fechou a janela
                    return; //sai do metodo sem exibir 'opcao invalida'
                }

                if (strEscolhido.isEmpty()) { //se a String de escolha estiver vazia
                    throw new CampoEmBrancoException("Você deixou um campo em branco"); //lanca uma CampoEmBrancoException
                }

                try { //tenta converter o que o usuario digitou de String para int
                    escolhido = Integer.parseInt(strEscolhido);
                } catch (NumberFormatException e) { //se o usuario tiver digitado qualquer coisa que nao seja um numero inteiro, chama uma NumberFormatException
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha um número."); //exibe uma mensagem avisando que o que foi digitado nao foi um numero inteiro
                    escolhido = -1; //define escolhido como -1 para que o loop seja reiniciado
                    continue; //continua o loop (que vai recomecar do zero)
                }

                switch (escolhido) {
                    case 1 -> {
                            Aluno novoAluno = dadosNovoAluno();
                            int resultado = cadAluno.cadastrarAluno(novoAluno);
                                switch (resultado) {
                                    case -1 ->
                                            JOptionPane.showMessageDialog(null, "Erro ao cadastrar aluno. Matrícula já existente.");
                                    case -2 ->
                                            JOptionPane.showMessageDialog(null, "Erro ao cadastrar aluno. CPF já existente.");
                                    default -> JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso.");
                                }
                    }

                    case 2 -> {
                        String matricula = lerMatricula();
                        Aluno a = cadAluno.pesquisarAluno(matricula);
                        if (a != null) {
                            JOptionPane.showMessageDialog(null, a.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
                        }
                    }

                    case 3 -> {
                        String matricula = lerMatricula();
                        Aluno alunoExistente = cadAluno.pesquisarAluno(matricula);
                        if (alunoExistente != null) {
                            Aluno novoCadastro = dadosNovoAluno();
                            if (novoCadastro != null) {
                                boolean atualizado = cadAluno.atualizarAluno(matricula, novoCadastro);
                                if (atualizado) {
                                    JOptionPane.showMessageDialog(null, "Cadastro atualizado.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Erro ao atualizar o cadastro.");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Aluno com essa matrícula não encontrado.");
                        }
                    }

                    case 4 -> {
                        String matricula = lerMatricula();
                        Aluno remover = cadAluno.pesquisarAluno(matricula);
                        if (remover != null) {
                            boolean removido = cadAluno.removerAluno(remover);
                            if (removido) {
                                JOptionPane.showMessageDialog(null, "Aluno removido do cadastro.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao remover aluno.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Aluno com essa matrícula não encontrado.");
                        }
                    }

                    case 5 -> listarTodosAlunos(cadAluno);

                    case 0 -> {
                    }

                    default -> JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                }
            } while (escolhido != 0);
        } catch (CampoEmBrancoException cbe) {
            JOptionPane.showMessageDialog(null, cbe.getMessage());
        }
    }

    private static String lerMatricula()  throws CampoEmBrancoException {
        while (true) {
            String matricula = JOptionPane.showInputDialog("Informe a matrícula do aluno: ");
            if (matricula.isEmpty()) {
                throw new CampoEmBrancoException("Você deixou a matrícula em branco");
            }
            if (matricula.matches("\\d{9}")) {
                return matricula;
            } else {
                JOptionPane.showMessageDialog(null, "Matrícula inválida. Deve conter 9 dígitos.");
            }
        }
    }
}