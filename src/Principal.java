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
		
		int opcao;

		try {
			do {
				opcao = MenuPrincipal.menuOpcoes();
				switch (opcao) {
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
						MenuTurmas.MenuTurma(cadTurma, cadAluno, cadProfessor);
						break;
					case 0:
						break;
				}
			} while (opcao != 0);
		} catch (CampoEmBrancoException cbe) {
			JOptionPane.showMessageDialog(null, "Erro: " + cbe.getMessage());
		}
		return;
	}
}