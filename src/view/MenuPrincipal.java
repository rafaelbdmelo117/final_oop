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

		String strEscolhido = JOptionPane.showInputDialog(opcoes); //recebe a escolha do usuario em uma string
		int escolhido; //variavel int para receber a escolha do usuario
		if (strEscolhido.isEmpty()) { //tratamento da excecao de campo em branco
			throw new CampoEmBrancoException("Você deixou um campo em branco.");
		}
		escolhido = Integer.parseInt(strEscolhido); //converte a string para int caso nao dispare a excecao
		return escolhido; //retorna a escolha para a Principal
	}
}