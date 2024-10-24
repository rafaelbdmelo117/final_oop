package view;

import app.Aluno;
import app.Professor;
import app.Turma;
import cadastros.CadastroAluno;
import cadastros.CadastroProfessor;
import cadastros.CadastroTurma; // Supondo que você tenha uma classe Aluno
import java.awt.*;
import java.util.List;
import java.util.Set;
import javax.swing.*;

public class MenuTurmas {

    public static Turma dadosNovaTurma() {
        JTextField codTurmaField = new JTextField(3);
        JTextField salaField = new JTextField(3);
        JTextField matriculaFUBField = new JTextField(30);
        JTextField codigoDiscField = new JTextField(7);
        JTextField qtdMaxAlunosField = new JTextField(3);

        while (true) {
            JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
            painel.add(new JLabel("Código da turma: "));
            painel.add(codTurmaField);
            painel.add(new JLabel("Sala: "));
            painel.add(salaField);
            painel.add(new JLabel("Matrícula FUB do professor: "));
            painel.add(matriculaFUBField);
            painel.add(new JLabel("Código da disciplina: "));
            painel.add(codigoDiscField);
            painel.add(new JLabel("Quantidade limite de alunos: "));
            painel.add(qtdMaxAlunosField);

            int confirma = JOptionPane.showConfirmDialog(null, painel, "Dados da nova turma", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (confirma == JOptionPane.OK_OPTION) {
                String codTurma = codTurmaField.getText();
                String sala = salaField.getText();
                String matriculaFUB = matriculaFUBField.getText();
                String codDisciplina = codigoDiscField.getText();
                String recebeQtdMaxAlunos = qtdMaxAlunosField.getText();

                boolean codTurmaValido = codTurma.matches("^[Tt][A-Za-z0-9]{2}$");
                boolean salaValida = sala.length() >= 2;
                boolean matriculaFUBValida = matriculaFUB.replace(".", "").replace("-", "").replace(" ", "").length() <= 10;
                boolean codDisciplinaValido = codDisciplina.length() == 7; // Considerando apenas o comprimento para simplicidade
                boolean qtdMaxAlunosValida = false;

                try {
                    int qtdMaxAlunos = Integer.parseInt(recebeQtdMaxAlunos);
                    qtdMaxAlunosValida = qtdMaxAlunos > 0 && qtdMaxAlunos <= 120;
                } catch (NumberFormatException e) {
                    qtdMaxAlunosValida = false;
                }

                if (codTurmaValido && salaValida && matriculaFUBValida && codDisciplinaValido && qtdMaxAlunosValida) {
                    int qtdMaxAlunos = Integer.parseInt(recebeQtdMaxAlunos);
                    return new Turma(codTurma, sala, matriculaFUB, codDisciplina, qtdMaxAlunos);
                } else {
                    StringBuilder msgDeErro = new StringBuilder("Dados inválidos:\n");
                    if (!codTurmaValido) msgDeErro.append(" - O código da turma deve estar no formato TXX, onde X é um caractere alfanumérico.\n");
                    if (!salaValida) msgDeErro.append(" - A sala deve conter pelo menos 2 caracteres.\n");
                    if (!matriculaFUBValida) msgDeErro.append(" - A matrícula do professor deve conter no máximo 10 caracteres, excluindo espaços, pontos e hífens.\n");
                    if (!codDisciplinaValido) msgDeErro.append(" - O código da disciplina deve conter exatamente 7 caracteres.\n");
                    if (!qtdMaxAlunosValida) msgDeErro.append(" - A quantidade máxima de alunos deve ser um número entre 1 e 120.\n");
                    JOptionPane.showMessageDialog(null, msgDeErro.toString());
                    // Retorna com os campos preenchidos
                    // Retorna ao loop para reexibir a tela com os inputs preenchidos

                }
            } else {
                return null;
            }
        }
    }
    
    private static void imprimirChamada(CadastroTurma cadTurma, CadastroProfessor cadProfessor) {
        String codTurma = lerCodigoTurma();
        if (codTurma == null) {
            return; // Sai se o usuário cancelar
        }
        Turma turma = cadTurma.procurarTurma(codTurma);
        if (turma == null) {
            JOptionPane.showMessageDialog(null, "Turma não encontrada.");
            return;
        }

        Set<Aluno> alunos = turma.getAlunos();
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "A turma não possui alunos.");
        } else {
            StringBuilder chamada = new StringBuilder("Chamada da turma " + turma.getCodigoTurma() + ":\n\n");

            String matriculaProfessor = turma.getProfessorAssociado();
            Professor professor = cadProfessor.pesquisarProfessor(matriculaProfessor);
            if (professor != null) {
                chamada.append("Professor:\n");
                chamada.append(professor.getMatriculaFUB()).append(" - ");
                chamada.append(professor.getNome()); 
                chamada.append("\n");
            } else {
                chamada.append("Professor não encontrado.\n");
            }

            chamada.append("Alunos:\n");
            for (Aluno aluno : alunos) {
                chamada.append(aluno.getMatricula())
                       .append(" - ").append(aluno.getNome())
                       .append("\n");
            }
            JOptionPane.showMessageDialog(null, chamada.toString(), "Chamada da Turma", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    
    private static String lerCodigoTurma() {
        while (true) {
            String codTurma = JOptionPane.showInputDialog("Insira o código da turma (Formato: TXX):");
            if (codTurma == null) {
                return null; // Trata o caso do botão "Cancelar"
            }
            if (codTurma.matches("^[Tt][A-Za-z0-9]{2}$")) { // Valida o formato do código da turma
                return codTurma;
            } else {
                JOptionPane.showMessageDialog(null, "Inválido. O código da turma deve estar no formato TXX, onde X é um caractere alfanumérico.");
            }
        }
    }

    private static void listarTurmasCadastradas(CadastroTurma cadTurma) {
        List<Turma> turmas = cadTurma.getTurmas();
        if (turmas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ainda não há nenhuma turma cadastrada.");
        } else {
            StringBuilder listaTurmas = new StringBuilder("Turmas cadastradas:\n\n");
            for (Turma turma : turmas) {
                listaTurmas.append(turma.toString()).append('\n');
            }
            JOptionPane.showMessageDialog(null, listaTurmas.toString(), "Lista de turmas", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void adicionarOuRemoverAlunos(CadastroTurma cadTurma, CadastroAluno cadAluno) {
        String codTurma = lerCodigoTurma();
        if (codTurma == null) {
            return; // Sai se o usuário cancelar
        }
        Turma turma = cadTurma.procurarTurma(codTurma);
        if (turma == null) {
            JOptionPane.showMessageDialog(null, "Turma não encontrada.");
            return;
        }

        String opcoes = """
                        Informe a op\u00e7\u00e3o desejada:
                        1 - Adicionar aluno
                        2 - Remover aluno
                        0 - Voltar""";
        int opcao = -1;
        do {
            String strOpcao = JOptionPane.showInputDialog(opcoes);
            if (strOpcao == null) {
                return; // Trata o caso do botão "Cancelar"
            }
            try {
                opcao = Integer.parseInt(strOpcao);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, insira um dígito válido.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    String matriculaAlunoAdd = JOptionPane.showInputDialog("Digite a matrícula do aluno para adicionar:");
                    if (matriculaAlunoAdd == null) {
                        return; // Sai se o usuário cancelar
                    }
                    Aluno alunoAdd = cadAluno.pesquisarAluno(matriculaAlunoAdd); 
                    if (alunoAdd != null) {
                        boolean sucessoAdd = turma.adicionarAluno(alunoAdd);
                        if (sucessoAdd) {
                            JOptionPane.showMessageDialog(null, "Aluno adicionado com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível adicionar o aluno. Verifique se a turma atingiu o limite máximo de alunos.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
                    }
                }
                case 2 -> {
                    String matriculaAlunoRem = JOptionPane.showInputDialog("Digite a matrícula do aluno para remover:");
                    if (matriculaAlunoRem == null) {
                        return; // Sai se o usuário cancelar
                    }
                    Aluno alunoRem = cadAluno.pesquisarAluno(matriculaAlunoRem); 
                    if (alunoRem != null) {
                        boolean sucessoRem = turma.removeAluno(alunoRem);
                        if (sucessoRem) {
                            JOptionPane.showMessageDialog(null, "Aluno removido com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível remover o aluno. Verifique se o aluno está na turma.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, insira um dígito válido.");
            }
        } while (opcao < 0 || opcao > 2);
    }

    public static void MenuTurma(CadastroTurma cadTurma, CadastroAluno cadAluno, CadastroProfessor cadProfessor) {
        while (true) {
            String opcoes = """
                            Informe a op\u00e7\u00e3o desejada:
                            1 - Adicionar turma
                            2 - Apagar turma
                            3 - Adicionar ou remover alunos \u00e0 turma
                            4 - Procurar turma
                            5 - Listar todas as turmas cadastradas
                            6 - Imprima a Chamada da Turma
                            0 - Voltar""";
            int opcao = -1;
            do {
                String strOpcao = JOptionPane.showInputDialog(opcoes);
                if (strOpcao == null) {
                    return; // Trata o caso do botão "Cancelar"
                }
                try {
                    opcao = Integer.parseInt(strOpcao);
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, insira um dígito válido.");
                    continue;
                }

                switch (opcao) {
                    case 1 -> {
                        Turma novaTurma = dadosNovaTurma();
                        if (novaTurma != null) {
                            int sucesso = cadTurma.cadastrarTurma(novaTurma);
                            switch (sucesso) {
                                case -3 -> JOptionPane.showMessageDialog(null, "Erro: Já existe uma turma cadastrada com esse código. Por favor, insira um código de turma diferente.");
                                case -4 -> JOptionPane.showMessageDialog(null, "Erro: A disciplina associada à turma não foi encontrada.");
                                case -5 -> JOptionPane.showMessageDialog(null, "Erro: Não há professores cadastrados no sistema. Por favor, cadastre pelo menos um professor antes de adicionar uma turma.");
                                case -6 -> JOptionPane.showMessageDialog(null, "Erro: Professor não encontrado com a matrícula fornecida. Verifique se a matrícula está correta ou cadastre o professor no sistema.");
                                case -7 -> JOptionPane.showMessageDialog(null, "Erro: Ocorreu um erro ao cadastrar a turma. Tente novamente.");
                                default -> JOptionPane.showMessageDialog(null, "Turma adicionada com sucesso.");
                            }
                        }
                    }
                    case 2 -> {
                        String codigoTurma = lerCodigoTurma();
                        if (codigoTurma == null) {
                            break; // Sai do loop se o usuário cancelar
                        }
                        Turma excluir = cadTurma.procurarTurma(codigoTurma);
                        if (excluir != null) {
                            boolean apagou = cadTurma.excluirTurma(excluir);
                            if (apagou) {
                                JOptionPane.showMessageDialog(null, "Turma removida do sistema com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro: Não foi possível remover a turma. Tente novamente.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro: Não foi encontrada turma com esse código.");
                        }
                    }
                    case 3 -> adicionarOuRemoverAlunos(cadTurma, cadAluno);
                    case 4 -> {
                        String codTur = lerCodigoTurma();
                        if (codTur == null) {
                            break; // Sai do loop se o usuário cancelar
                        }
                        Turma turmaProcura = cadTurma.procurarTurma(codTur);
                        if (turmaProcura != null) {
                            JOptionPane.showMessageDialog(null, turmaProcura.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro: Turma não cadastrada.");
                        }
                    }
                    case 5 -> listarTurmasCadastradas(cadTurma);
                    
                    case 6 -> imprimirChamada(cadTurma, cadProfessor);

                    case 0 -> {
                        return;
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, insira um dígito válido.");
                }
            } while (opcao < 0 || opcao > 5);
        }
    }
}
