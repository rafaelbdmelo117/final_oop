import app.CampoEmBrancoException;
import cadastros.*;
import javax.swing.JOptionPane;
import view.*;

public class Principal {

	static CadastroAluno cadAluno;
	static CadastroProfessor cadProfessor;
	static CadastroDisciplina cadDisciplina;
	static CadastroTurma cadTurma;
	
	public static void main(String[] args) throws CampoEmBrancoException {
		cadAluno = new CadastroAluno();
		cadProfessor = new CadastroProfessor();
		cadDisciplina = new CadastroDisciplina();
		cadTurma = new CadastroTurma(cadProfessor, cadDisciplina);
		//ate aqui, foram criados os objetos de cada tipo para serem usados de acordo com a opcao selecionada
		
		int escolhido; //vai receber a informacao do MenuPrincipal

		try { //este try esta aqui para capturar uma excecao de campo em branco, caso aconteca
			do { //de acordo com a escolha do usuario (que vai vir la do MenuPrincipal), entra em um menu diferente onde havera interacoes especificas
				escolhido = MenuPrincipal.menuOpcoes(); //puxa a escolha do usuario la do MenuPrincipal
				switch (escolhido) {
					case 1:
						MenuAluno.MenuAluno(cadAluno);
						break;
					case 2:
						MenuProfessor.MenuProfessor(cadProfessor);
						break;
					case 3:
						MenuDisciplina.MenuDisciplina(cadDisciplina);
						break;
					case 4:
						MenuTurmas.MenuTurma(cadTurma, cadAluno, cadProfessor); //a turma so pode ser criada usando informacoes de professor e disciplina,
																				//assim como alunos que serao adicionados, por isso recebe esses parametros.
						break;
					case 0: //no caso de o usuario digitar 0, ele simplesmente fecha a aplicacao
						break;
				}
			} while (escolhido != 0);
		} catch (CampoEmBrancoException cbe) {
			JOptionPane.showMessageDialog(null, "Erro: " + cbe.getMessage()); //puxa a mensagem de excecao la no tratamento do MenuPrincipal
		}
		return;
	}
}