package models;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import viewers.OrcamentoForm;

public class Orcamento {
	
	private int id;
	private String fornecedor;
	private String produto;
	private double preco;
	private float maisBarato;
	
	private final Locale BRASIL = new Locale("pt", "BR");
	private DecimalFormat df = new DecimalFormat("#.00");
	
	public Orcamento(int id, String fornecedor, String produto, double preco) {
		this.id = id;
		this.fornecedor = fornecedor;
		this.produto = produto;
		this.preco = preco;
	}
	
	public Orcamento(int id) {
		this.id = id;
	}
	
	public Orcamento(String linha) {
		df.setCurrency(Currency.getInstance(BRASIL));
		
		String[] temp = linha.split(";");		
			
		this.id = Integer.parseInt(temp[0]);
		this.fornecedor = temp[1];
		this.produto = temp[2];
		this.preco = Double.parseDouble(temp[3].replace(",", "."));

	}

	@Override
	public int hashCode() {
		return Objects.hash(fornecedor, id, maisBarato, preco, produto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orcamento other = (Orcamento) obj;
		return Objects.equals(fornecedor, other.fornecedor) && id == other.id && maisBarato == other.maisBarato
				&& Double.doubleToLongBits(preco) == Double.doubleToLongBits(other.preco)
				&& Objects.equals(produto, other.produto);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

//	public double getMaisBarato() {
//		if (getProduto() == "HD") {
//			double barato = 0;
//			if (preco >barato) {
//				barato = preco;
//		}
//	}
//		return preco;
//	}
	public void setMaisBarato(float maisBarato) {
		this.maisBarato = maisBarato;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return  + id + "\t" + fornecedor + "\t" + produto + "\t" + preco + "\t"  + "\n";
	}
	
	public String toCSV() {
		return  + id + ";" + fornecedor + ";" + produto + ";" + preco + ";" +  "\r\n";
	}

	public Object getMaisBarato() {
		if (getProduto() == "HD") {
			double barato = 0;
			if (preco >barato) {
				preco = barato;
		}
	}
		return maisBarato;
	
	}
}
