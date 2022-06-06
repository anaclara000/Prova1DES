package controllers;

import java.text.ParseException;
import java.util.ArrayList;

import models.Orcamento;
import models.dao.OrcamentoDAO;

public class OrcamentoProcess {
	
	public static ArrayList<Orcamento> orcamentos = new ArrayList<>();
	private static OrcamentoDAO od = new OrcamentoDAO();
	
	public static void abrir() {
		try {
			orcamentos = od.ler();
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
	}
	
	public static boolean salvar() {
		od.escrever(orcamentos);
		abrir();
		return false;
		
	}
	
	public static void calculo(String produto) {
		int index = 0;
		double maisBarato = 999999999;
		for (Orcamento orcamento : orcamentos) {
			if (orcamento.getProduto().equals(produto) && orcamento.getPreco() < maisBarato) {
				index = orcamentos.indexOf(orcamento);
				maisBarato = orcamento.getPreco();
			}
		}
		
		for (Orcamento orcamento : orcamentos) {
			if (orcamentos.indexOf(orcamento) == index) {
				orcamento.setMaisBarato(true);
			} else if(orcamento.getProduto() == produto){
				orcamento.setMaisBarato(false);
			}
		}
	}
	
}

