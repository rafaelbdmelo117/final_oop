package cadastros;

import app.Disciplina;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CadastroDisciplina {
    private List<Disciplina> disciplinas;

    public CadastroDisciplina() {
        disciplinas = new ArrayList<>();
    }

    public int cadastrarDisciplina(Disciplina d) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigoDisciplina().equalsIgnoreCase(d.getCodigoDisciplina())) {
                return -1;
            }
        }
        boolean cadastrou = disciplinas.add(d);
        return cadastrou ? disciplinas.size() : -2;
    }

    public Disciplina pesquisarDisciplina(String codigoDisciplina) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina)) {
                return d;
            }
        }
        JOptionPane.showMessageDialog(null, "Disciplina não cadastrada.");
        return null;
    }

    public boolean removerDisciplina(Disciplina d) {
        return disciplinas.remove(d);
    }

    public boolean atualizarDisciplina(String codigoDisciplina, Disciplina d) {
        Disciplina remover = pesquisarDisciplina(codigoDisciplina);
        if (remover != null) {
            disciplinas.remove(remover);
            return disciplinas.add(d);
        }
        return false;
    }

    public List<Disciplina> listarTodasDisciplinas() {
        return new ArrayList<>(disciplinas);
    }
}
