package viewers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controllers.OrcamentoProcess;
import models.Orcamento;


public class OrcamentoForm extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel painel;
	private JLabel id, fornecedor, produto, preco;
	private JComboBox cbproduto;
	private JTextField tfid, tffornecedor, tfpreco;
	private DefaultTableModel tableModel;
	private JButton create, read, update, delete;
	private JTable table;
	private JScrollPane rolagem;
	
	private int autoId = OrcamentoProcess.orcamento.get(OrcamentoProcess.orcamento.size() - 1).getId() + 1;
	private DecimalFormat df = new DecimalFormat("#.00");
	private final Locale BRASIL = new Locale("pt", "BR");
	
	OrcamentoForm(){
		setTitle("Regitro de Orçamento");
		setBounds(100, 100, 610, 390);
		painel = new JPanel();
		setContentPane(painel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		id = new JLabel("ID:");
		id.setBounds(20, 25, 120, 30);
		tfid = new JTextField(String.format("%d", autoId));
		tfid.setEditable(false);
		tfid.setEditable(false);
		tfid.setBounds(40, 25, 40, 30);
		tfid.setBackground(new Color(215, 215, 215));
		
		fornecedor = new JLabel("fornecedor:");
		fornecedor.setBounds(100, 25, 140, 30);
		tffornecedor = new JTextField();
		tffornecedor.setBounds(235, 25, 150, 30);
		tffornecedor.setBackground(new Color(215, 215, 215));
		
		produto = new JLabel("Produto:");
		produto.setBounds(20, 70, 120, 30);
		cbproduto = new JComboBox<String>(new String[] {"SSD", "HD", "Placa mãe" , "Processador", "Placa de video"});
		cbproduto.setBounds(100, 70, 100, 30);
		cbproduto.setBackground(new Color(215, 215, 215));
		
		preco = new JLabel("Preço:");
		preco.setBounds(215, 70, 80, 30);
		tfpreco = new JTextField();
		tfpreco.setBounds(290, 70, 80, 30);
		tfpreco.setBackground(new Color(215, 215, 215));
		
		painel.add(id);
		painel.add(tfid);
		painel.add(fornecedor);
		painel.add(tffornecedor);
		painel.add(produto);
		painel.add(cbproduto);
		painel.add(preco);
		painel.add(tfpreco);
		
		table = new JTable();
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Id");
		tableModel.addColumn("Fornecedor");
		tableModel.addColumn("Produto");
		tableModel.addColumn("Preco");
		tableModel.addColumn("Status");
		
		
		if (OrcamentoProcess.orcamento.size() != 1) {
			preencherTabela();
		}
		table = new JTable(tableModel);
		table.setEnabled(false);
		rolagem = new JScrollPane(table);
		rolagem.setBounds(20, 170, 550, 130);
		painel.add(rolagem);
		
		create = new JButton("Cadastrar");
		create.setBounds(60, 120, 110, 30);
		create.setBackground(new Color(189,236,182));
		painel.add(create);

		read = new JButton("Buscar");
		read.setBounds(180, 120, 110, 30);
		painel.add(read);

		update = new JButton("Alterar");
		update.setBounds(300, 120, 110, 30);
		update.setEnabled(false);
		painel.add(update);

		delete = new JButton("Excluir");
		delete.setBounds(420, 120, 110, 30);

		delete.setEnabled(false);
		painel.add(delete);
		
		create.addActionListener((ActionListener) new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		cadastrar();
        	}
        });
		read.addActionListener((ActionListener) new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		buscar();
        	}
        });
		update.addActionListener((ActionListener) new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		alterar();
        	}
        });
		delete.addActionListener((ActionListener) new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		excluir();
        	}
        });
		
	}
	
	int obterEquipamento(String equipamento) {
		switch (equipamento) {
		case "SSD":
			return 0;
		case "HD":
			return 1;
		case "Placa mãe":
			return 2;
		case "Processador":
			return 3;
		case "Placa de video":
			return 4;
		default:
			return -1;
		}
	}
	

	private void cadastrar() {
	if(tfid.getText().length() != 0 && tffornecedor.getText().length() != 0 && tfpreco.getText().length() != 0) {
		
		df.setCurrency(Currency.getInstance(BRASIL));
		double preco;
		try {
			preco = Double.parseDouble(df.parse(tfpreco.getText()).toString());
		} catch (ParseException e) {
			System.out.println(e);
			preco = 0;
		}

		OrcamentoProcess.orcamento.add(new Orcamento(autoId,  tffornecedor.getText(),
				cbproduto.getSelectedItem().toString(), preco));
		autoId = OrcamentoProcess.orcamento.get(OrcamentoProcess.orcamento.size() - 1).getId() + 1;
		preencherTabela();
		limparCampos();
		OrcamentoProcess.salvar();
		
	}else {
		JOptionPane.showMessageDialog(this, "Favor preencher todos os campos.");
	}
}
	
	private void buscar() {
		String text = JOptionPane.showInputDialog(this, "Digite o id do item");
		try {
			int id = Integer.parseInt(text);
			
			for (Orcamento o : OrcamentoProcess.orcamento) {
				if (o.getId() == id) {
					int indice = OrcamentoProcess.orcamento.indexOf(o);
			tfid.setText(String.format("%d", OrcamentoProcess.orcamento.get(indice).getId()));
			tffornecedor.setText(OrcamentoProcess.orcamento.get(indice).getFornecedor());
			cbproduto.setSelectedIndex(obterEquipamento(OrcamentoProcess.orcamento.get(indice).getProduto()));
			tfpreco.setText((String.format("%.2f" , OrcamentoProcess.orcamento.get(indice).getPreco())));
			
			create.setEnabled(false);
			update.setEnabled(true);
			delete.setEnabled(true);
			OrcamentoProcess.salvar();
					}
				}
			} catch (Exception e) {
		JOptionPane.showMessageDialog(this, "Id inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
	}
	
}
	
	private void alterar() {
		int id = Integer.parseInt(tfid.getText());
		Orcamento o = new Orcamento(id);
		int indice = OrcamentoProcess.orcamento.indexOf(o);
		if (tfid.getText().length() != 0 && tffornecedor.getText().length() != 0 &&  tfpreco.getText().length() != 0)  {
			Orcamento tempOrca = new Orcamento(Integer.parseInt(tfid.getText()), tffornecedor.getText(), cbproduto.getSelectedItem().toString(),(Double.parseDouble(tfpreco.getText().replace(",", "."))));
			for (Orcamento o1 : OrcamentoProcess.orcamento) {
				if (o1.getId() == tempOrca.getId()) {
					o1.setId(tempOrca.getId());
					o1.setFornecedor(tempOrca.getFornecedor());
					o1.setProduto(tempOrca.getProduto());
					o1.setPreco(tempOrca.getPreco());
				
				}
			}
			df.setCurrency(Currency.getInstance(BRASIL));
			double preco;
			try {
				preco = Double.parseDouble(df.parse(tfpreco.getText()).toString());
			} catch (ParseException e) {
				System.out.println(e);
				preco = 0;
			}

				preencherTabela();
				limparCampos();
				OrcamentoProcess.salvar();
				
				create.setEnabled(true);
				update.setEnabled(false);
				delete.setEnabled(false);
				tfid.setText(String.format("%d", autoId));
				OrcamentoProcess.salvar();
			
		} else {
			JOptionPane.showMessageDialog(this, "Favor Preencher todas as informações");
		}
	}
	
	private void limparCampos() {
		tfid.setText(String.format("%d",autoId));
		tffornecedor.setText(null);
		cbproduto.setSelectedIndex(0);
		tfpreco.setText(null);

	}

	private void preencherTabela() {
		int totLinhas = tableModel.getRowCount();
		if (tableModel.getRowCount() > 0) {
			for (int i = 0; i < totLinhas; i++) {
				tableModel.removeRow(0);
			}
		}
		for (Orcamento o : OrcamentoProcess.orcamento) {
			tableModel.addRow(new String[] { String.format("%d", o.getId()), o.getFornecedor(), o.getProduto(),String.format("%.2f", o.getPreco())});
		}
	}

	private void excluir() {
		if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja EXCLUIR esse Produto?") == 0) {
			Orcamento prodTemp = null;
			for (Orcamento o : OrcamentoProcess.orcamento) {
				if (o.getId() == Integer.parseInt(tfid.getText())) {
					prodTemp = o;
				}
			}

			OrcamentoProcess.orcamento.remove(OrcamentoProcess.orcamento.indexOf(prodTemp));

			preencherTabela();
			limparCampos();
			OrcamentoProcess.salvar();

			create.setEnabled(true);
			update.setEnabled(false);
			delete.setEnabled(false);
		}
	}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == create) {
				cadastrar();
			}
			if (e.getSource() == read) {
				buscar();
			}
			if (e.getSource() == update) {
				alterar();
			}
			if (e.getSource() == delete) {
				excluir();
			}
		}
		
		
		public static void main(String[] agrs) throws ParseException {
			OrcamentoProcess.abrir();
			new OrcamentoForm().setVisible(true);
		}
	}

