package cadastros;

import app.*;
import java.util.ArrayList;
import java.util.List;

public class CadastroTurma {
    private List<Turma> turmas;
    private CadastroProfessor cadastroProfessor;
    private CadastroDisciplina cadastroDisciplina;

    public CadastroTurma(CadastroProfessor cadProf, CadastroDisciplina cadDisc) {
        turmas = new ArrayList<>();
        cadastroProfessor = cadProf;
        cadastroDisciplina = cadDisc;
    }

    public int cadastrarTurma(Turma t) {
        for (Turma teste : turmas) {
            if (teste.getCodigoTurma().equalsIgnoreCase(t.getCodigoTurma())) {
                return -3; // Já existe turma com este código
            }
        }

        Disciplina disciplinaVerificadora = cadastroDisciplina.pesquisarDisciplina(t.getDisciplina());
        if (disciplinaVerificadora == null) {
            return -4; // A disciplina não existe
        }

        if (cadastroProfessor.listarTodosProfessores().isEmpty()) {
            return -5; // Não há professores cadastrados no sistema
        }

        Professor professorVerificador = cadastroProfessor.pesquisarProfessor(t.getProfessorAssociado());
        if (professorVerificador == null) {
            return -6; // Professor não encontrado com a matrícula fornecida
        }

        boolean ok = turmas.add(t);
        return ok ? turmas.size() : -7; // Código de erro genérico
    }

    public Turma procurarTurma(String codigoTurma) {
        for (Turma t : turmas) {
            if (t.getCodigoTurma().equalsIgnoreCase(codigoTurma)) {
                return t;
            }
        }
        return null; // Turma não encontrada
    }

    public boolean excluirTurma(Turma excluir) {
        return turmas.remove(excluir);
    }

    public List<Turma> getTurmas() {
        return new ArrayList<>(turmas);
    }

    public int adicionarAlunoATurma(String codigoTurma, Aluno aluno) {
        Turma turma = procurarTurma(codigoTurma);
        if (turma == null) {
            return -1; // Turma não encontrada
        }
        return turma.adicionarAluno(aluno) ? 1 : -2; // -2 indica que não foi possível adicionar o aluno (turma cheia)
    }

    public int removerAlunoDaTurma(String codigoTurma, Aluno aluno) {
        Turma turma = procurarTurma(codigoTurma);
        if (turma == null) {
            return -1; // Turma não encontrada
        }
        return turma.removeAluno(aluno) ? 1 : -2; // -2 indica que o aluno não estava na turma
    }
}
