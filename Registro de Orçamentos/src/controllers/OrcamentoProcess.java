package controllers;

import java.text.ParseException;
import java.util.ArrayList;

import models.Orcamento;
import models.dao.OrcamentoDAO;

public class OrcamentoProcess {
	
	public static ArrayList<Orcamento> orcamento = new ArrayList<>();
	private static OrcamentoDAO od = new OrcamentoDAO();
	
	public static void abrir() {
		try {
			orcamento = od.ler();
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
	
	public static boolean salvar() {
		od.escrever(orcamento);
		abrir();
		return false;
		
	}
	
}

