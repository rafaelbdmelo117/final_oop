import cadastros.*;
import javax.swing.JOptionPane;
import view.*;

public class Principal {

	static CadastroAluno cadAluno;
	static CadastroProfessor cadProfessor;
	static CadastroDisciplina cadDisciplina;
	static CadastroTurma cadTurma;
	
	public static void main(String[] args) {
		cadAluno = new CadastroAluno();
		cadProfessor = new CadastroProfessor();
		cadDisciplina = new CadastroDisciplina();
		cadTurma = new CadastroTurma(cadProfessor, cadDisciplina);
		
		int opcao = 0; 
		
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
				default: 
					JOptionPane.showMessageDialog(null, "Opção inválida");
					opcao = -1;
				break;
			}
		} while (opcao != 0);
		return;
	}
}