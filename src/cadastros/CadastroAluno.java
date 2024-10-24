package cadastros;

import app.Aluno;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CadastroAluno {
    private List<Aluno> alunos;

    public CadastroAluno() {
        alunos = new ArrayList<>();
    }

    public int cadastrarAluno(Aluno a) {
        // Verifica se a matrícula ou CPF já existe na lista
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equalsIgnoreCase(a.getMatricula())) {
                // Retorna -1 se a matrícula já existir
                return -1;
            }
            if (aluno.getCpf().equalsIgnoreCase(a.getCpf())) {
                // Retorna -2 se o CPF já existir
                return -2;
            }
        }
        // Se a matrícula e CPF não existirem, adiciona o aluno à lista
        boolean cadastrou = alunos.add(a);
        return cadastrou ? alunos.size() : -1;
    }

    public Aluno pesquisarAluno(String matriculaAluno) {
        for (Aluno a : alunos) {
            if (a.getMatricula().equalsIgnoreCase(matriculaAluno)) {
                return a;
            }
        }
        return null;
    }

    public boolean removerAluno(Aluno a) {
        return alunos.remove(a);
    }

    public boolean atualizarAluno(String matricula, Aluno a) {
        Aluno remover = pesquisarAluno(matricula);
        if (remover != null) {
            alunos.remove(remover);
            return alunos.add(a);
        }
        return false;
    }

    public List<Aluno> listarTodosAlunos() {
        return new ArrayList<>(alunos);
    }
}
