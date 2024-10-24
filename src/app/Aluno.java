package app;

public class Aluno extends PessoaFisica {
    private String matricula;
    private String curso;

    public Aluno(String nome, String cpf, String email, String matricula, String curso) {
        super(nome, cpf, email);
        this.matricula = matricula;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }


   

    @Override
    public String toString() {
        return "Nome: " + getNome() + '\n' +
               "CPF: " + getCpf() + '\n' +
               "Email: " + getEmail() + '\n' +
               "Matrícula: " + getMatricula() + '\n' +
               "Curso: " + getCurso() + '\n';
    }
}