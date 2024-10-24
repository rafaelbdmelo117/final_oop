package app;

public class Professor extends PessoaFisica{
	
	private String areaFormacao;
	private String matriculaFUB;
	
	public Professor(String nome, String cpf, String email, String areaFormacao, String matriculaFUB) {
		super(nome, cpf, email);
		this.areaFormacao = areaFormacao;
		this.matriculaFUB = matriculaFUB;
	}

	public final String getAreaFormacao() {
		return areaFormacao;
	}

	public final String getMatriculaFUB() {
		return matriculaFUB;
	}

	public String toString() {
		String resposta = super.toString();
		resposta += "Matrícula FUB: " + getMatriculaFUB() + '\n';
		resposta += "Área de formação: " + getAreaFormacao() + '\n';
		return resposta;
	}
}