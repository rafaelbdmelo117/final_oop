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
        try { //esse try aqui vai detectar se em algum momento um campo ficar em branco e vai jogar para o catch
            do { //esse loop mantem o metodo funcionando para que o usuario possa interagir com os menus. tambem permite que o programa resete caso ele caia em algum erro
                String strEscolhido = JOptionPane.showInputDialog(txt); //recebe a escolha do usuario como uma String

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

                switch (escolhido) { //define o caminho que sera tomado a partir da escolha do usuario
                    case 1 -> { //entra no cadastro de um novo aluno
                            Aluno novoAluno = dadosNovoAluno(); //puxa o metodo dadosNovoAluno para exibir uma interface na qual o usuario insere os dados
                            int resultado = cadAluno.cadastrarAluno(novoAluno); //joga os dados para o metodo cadastrarAluno de CadastroAluno e armazena o retorno num elemento int
                                                                                // que sera testado
                                switch (resultado) { //testa o que o metodo cadastrarAluno retornou
                                    case -1 -> //erro de matricula ja existente (conf. linha 20 da classe CadastroAluno)
                                            JOptionPane.showMessageDialog(null, "Erro ao cadastrar aluno. Matrícula já existente."); //mensagem de matricula ja existente
                                    case -2 -> //erro de cpf ja existente (conf. linha 23 da classe CadastroAluno)
                                            JOptionPane.showMessageDialog(null, "Erro ao cadastrar aluno. CPF já existente."); //mensagem de cpf ja existente
                                    default -> JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso."); //aqui o metodo retornou o tamanho da lista de alunos, ou seja, sucesso
                                }
                    }

                    case 2 -> { //entra na pesquisa de um aluno (cadastrado ou nao)
                        String matricula = lerMatricula(); //chama o metodo letMatricula desta mesma classe para receber a matricula que o usuario deseja inserir
                        Aluno a = cadAluno.pesquisarAluno(matricula); //chama o metodo pesquisarAluno da classe CadastroAluno passando a matricula inserida pelo usuario como parametro
                        if (a != null) { //se a matricula existe na lista do cadastro
                            JOptionPane.showMessageDialog(null, a.toString()); //passa as informacoes do aluno para uma String e exibe para o usuario
                        } else { //se a matricula nao for encontrada
                            JOptionPane.showMessageDialog(null, "Aluno não encontrado."); //mensagem indicando que a matricula nao esta cadastrada
                        }
                    }

                    case 3 -> { //entra na atualizacao de um aluno ja cadastrado (exclui aquele aluno e substitui por um novo)
                        String matricula = lerMatricula(); //puxa a matricula que o usuario inseriu
                        Aluno alunoExistente = cadAluno.pesquisarAluno(matricula); //procura pelo aluno, assim como no Case 2
                        if (alunoExistente != null) { //se a matricula existir no cadastro
                            Aluno novoCadastro = dadosNovoAluno(); //cria um novo aluno e chama o metodo dadosNovoAluno
                            if (novoCadastro != null) { //se a matricula existe
                                boolean atualizado = cadAluno.atualizarAluno(matricula, novoCadastro); //chama o metodo atualizarAluno da classe CadastroAluno
                                if (atualizado) { //se o retorno for true
                                    JOptionPane.showMessageDialog(null, "Cadastro atualizado."); //excluiu o antigo e inseriu o novo
                                } else { //se o retorno for false
                                    JOptionPane.showMessageDialog(null, "Erro ao atualizar o cadastro."); //nada ocorreu
                                }
                            }
                        } else { //se a matricula nao existe
                            JOptionPane.showMessageDialog(null, "Aluno com essa matrícula não encontrado."); //mensagem de que nao existe esse aluno a ser atualizado
                        }
                    }

                    case 4 -> { //entra na remocao de um aluno ja cadastrado
                        String matricula = lerMatricula(); //puxa a matricula que o usuario inseriu
                        Aluno remover = cadAluno.pesquisarAluno(matricula); //chama o metodo pesquisarAluno da classe CadastroAluno
                        if (remover != null) { //se a matricula existir no cadastro
                            boolean removido = cadAluno.removerAluno(remover); //chama o metodo removerAluno da classe CadastroAluno e armazena o retorno num boolean
                            if (removido) { //se o retorno for true
                                JOptionPane.showMessageDialog(null, "Aluno removido do cadastro."); //informa que deu certo
                            } else { //se o retorno for false
                                JOptionPane.showMessageDialog(null, "Erro ao remover aluno."); //informa que nada ocorreu
                            }
                        } else { //se a matricula nao existir no cadastro
                            JOptionPane.showMessageDialog(null, "Aluno com essa matrícula não encontrado."); //informa que nao existe aluno no cadastro
                        }
                    }

                    case 5 -> listarTodosAlunos(cadAluno); //chama o metodo de listarTodosAlunos desta classe

                    case 0 -> { //sai do loop
                    }

                    default -> JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente."); //caso o usuario digite uma opcao fora do escopo ou algo que nao seja um numero, informa
                }
            } while (escolhido != 0); //fecha o loop quando o usuario digita 0
        } catch (CampoEmBrancoException cbe) { //chama a CampoEmBrancoException quando o usuario deixa algum campo em branco
            JOptionPane.showMessageDialog(null, cbe.getMessage()); //puxa a mensagem definida nos metodos
        }
    }

    private static String lerMatricula()  throws CampoEmBrancoException {
        while (true) { //enquanto o usuario nao digitar um conjunto de numeros inteiros, o metodo roda
            String matricula = JOptionPane.showInputDialog("Informe a matrícula do aluno: "); //recebe uma String com a matricula do aluno
            if (matricula.isEmpty()) { //testa se o campo de matricula ficou vazio
                throw new CampoEmBrancoException("Você deixou a matrícula em branco"); //lanca a CampoEmBrancoException
            }
            if (matricula.matches("\\d{9}")) { //se a matricula inserida tiver exatamente 9 digitos
                return matricula; //retorna essa matricula
            } else { //se a matricula nao tiver exatamente 9 digitos
                JOptionPane.showMessageDialog(null, "Matrícula inválida. Deve conter 9 dígitos."); //mensagem de erro
            }
        }
    }
}