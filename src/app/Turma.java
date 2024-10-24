package app;

import java.util.HashSet;
import java.util.Set;

public class Turma {
    private String codigoTurma;
    private String sala;
    private String professorAssociado;
    private String disciplina;
    private int qtdMaxAlunos;
    private Set<Aluno> alunos; // Usando HashSet para evitar duplicidade e garantir melhor performance

    public Turma(String codigoTurma, String sala, String professorAssociado, String disciplina, int qtdMaxAlunos) {
        this.codigoTurma = codigoTurma;
        this.sala = sala;
        this.professorAssociado = professorAssociado;
        this.disciplina = disciplina;
        this.qtdMaxAlunos = qtdMaxAlunos;
        this.alunos = new HashSet<>();
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public String getSala() {
        return sala;
    }

    public String getProfessorAssociado() {
        return professorAssociado;
    }


    public String getDisciplina() {
        return disciplina;
    }

    public int getQtdMaxAlunos() {
        return qtdMaxAlunos;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public boolean adicionarAluno(Aluno aluno) {
        if (alunos.size() < qtdMaxAlunos) {
            return alunos.add(aluno); // Adiciona o aluno se não estiver no conjunto e a turma não estiver cheia
        }
        return false;
    }

    public boolean removeAluno(Aluno aluno) {
        return alunos.remove(aluno); // Remove o aluno se estiver no conjunto
    }

    @Override
    public String toString() {
        return "Turma: " + codigoTurma + 
        "\nSala: " + sala + 
        "\nProfessor: " + professorAssociado +
        "\nDisciplina: " + disciplina + 
        "\nMáximo de Alunos: " + qtdMaxAlunos +
        "\nAlunos: " + alunos;
    }
}
