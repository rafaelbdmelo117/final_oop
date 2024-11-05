package cadastros;

import app.Aluno;
import app.CampoEmBrancoException;

import java.util.ArrayList;
import java.util.List;

public class CadastroAluno {
    private List<Aluno> alunos; //cria a referencia "alunos" para uma lista de tipo "Aluno"

    public CadastroAluno() {
        alunos = new ArrayList<>(); //cria a lista do tipo "Aluno" referenciada por "alunos"
    }

    public int cadastrarAluno(Aluno a)  { //metodo para realizar o cadastro do aluno com base no cpf e matricula
        //verifica se a matricula ou o cpf ja existem na lista
        for (Aluno alunoTeste : alunos) { //itera todos os alunos da lista de alunos para fazer os testes
            if (alunoTeste.getMatricula().equalsIgnoreCase(a.getMatricula())) { //testa se as matriculas sao iguais
                return -1; //retorna -1 se a matricula ja existir
            }
            if (alunoTeste.getCpf().equalsIgnoreCase(a.getCpf())) { //testa se os cpfs sao iguais
                return -2; //retorna -2 se o cpf ja existir
            }
        }
        //se a matricula e cpf nao existirem, adiciona o aluno a lista
        boolean cadastrou = alunos.add(a); //cria uma variavel boolean para servir de teste para confirmar o cadastro
        return cadastrou ? alunos.size() : -1; //se cadastrou for true, retorna o tamanho da lista de alunos, se for false, -1
    }

    public Aluno pesquisarAluno(String matriculaAluno) { //metodo que compara uma string matricula dada com uma que pode estar na lista
        for (Aluno a : alunos) { //itera toda a lista de alunos
            if (a.getMatricula().equalsIgnoreCase(matriculaAluno)) { //verifica se a matricula pesquisada e igual a alguma da lista
                return a; //se for igual, retorna o aluno que tem a matricula igual
            }
        }
        return null; //se nao, retorna nada
    }

    public boolean removerAluno(Aluno a) { //metodo para remover um aluno da lista usando a matricula
        return alunos.remove(a); //encontra o aluno a ser removido e remove-o da lista
    }

    public boolean atualizarAluno(String matricula, Aluno a) { //metodo para fazer alteracoes no cadastro de um aluno
        Aluno remover = pesquisarAluno(matricula); //cria uma variavel de referencia de tipo aluno para receber a matricula que o usuario inseriu
        alunos.remove(remover); //remove o aluno cuja matricula e igual ao que o usuario passou
        return alunos.add(a); //adiciona um novo aluno para receber os dados atualizados
    }

    public List<Aluno> listarTodosAlunos() { //metodo que mostra toda a lista de alunos de uma vez
        return new ArrayList<>(alunos);
    }
}
