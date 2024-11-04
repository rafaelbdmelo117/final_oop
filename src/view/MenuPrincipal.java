package view;

import app.CampoEmBrancoException;

import javax.swing.JOptionPane;

public class MenuPrincipal {

	public static int menuOpcoes () throws CampoEmBrancoException {
		String opcoes = "1 - Abrir cadastro de alunos \n"
				      + "2 - Abrir cadastro de professores \n"
				      + "3 - Abrir cadastro de disciplinas \n"
				      + "4 - Abrir cadastro de turmas \n"
				      + "0 - Sair";

		String strOpcao = JOptionPane.showInputDialog(opcoes);
		int opcao;
		if (strOpcao.isEmpty()) {
			throw new CampoEmBrancoException("Você deixou um campo em branco.");
		}
		opcao = Integer.parseInt(strOpcao);
		return opcao;
	}
}