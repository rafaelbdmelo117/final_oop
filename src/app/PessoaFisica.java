package app;

public class PessoaFisica {
	
	private String nome, 
	               cpf, 
	               email;
	
	public PessoaFisica(String nome, String cpf, String email) {
		this.nome = nome; 
		this.cpf = cpf;
		this.email = email;
	}

	public final String getNome() {
		return nome;
	}

	public final String getCpf() {
		return cpf;
	}

	public final String getEmail() {
		return email;
	}
	
	public String toString() {
		String resposta = "";
		resposta += "Nome: " + nome + '\n';
		resposta += "CPF: " + cpf + '\n'; 
		resposta += "Email: " + email + '\n';
		return resposta;
	}
}
