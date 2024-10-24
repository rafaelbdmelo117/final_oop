package app;

public class Disciplina {
    
    private String nomeDisciplina;
    private String codigoDisciplina;
    
    public Disciplina(String nomeDisciplina, String codigoDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    @Override
    public String toString() {
        return "Disciplina: " + getNomeDisciplina() + '\n' +
        "Código da disciplina: " + getCodigoDisciplina() + '\n';
    }
}