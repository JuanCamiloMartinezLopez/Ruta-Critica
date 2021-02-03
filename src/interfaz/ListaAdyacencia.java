package interfaz;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import logica.Logica;

public class ListaAdyacencia extends JPanel{
	
	JTable tabla;
	JScrollPane js;
	Logica log;
	DefaultTableModel model;
	
	public ListaAdyacencia() {
		this.setSize(120, 200);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		model=new DefaultTableModel();
		tabla= new JTable(model);
		tabla.setBounds(0, 0, 120, 200);
		System.out.println(model);
		model.addColumn("Nodo");
		model.addColumn("Adyacentes");
		TableColumnModel columnModel = this.tabla.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(30);
		columnModel.getColumn(1).setPreferredWidth(70);
		js=new JScrollPane(tabla);
		js.setVisible(true);
		this.add(js);
		this.add(tabla);
	}
	
	public void actualizarTabla() {
		System.out.println(model);
		this.model.addRow(new Object[]{String.valueOf(log.getNumNodos())+"->",""});
		this.repaint();
	}
	
	public Object[][] obtenerInfo(){
		int numfilas=this.model.getRowCount();
		int numColumnas= this.model.getColumnCount();
		Object data[][] = new Object[numfilas][numColumnas];
		for(int i=0;i<numfilas;i++) {
			data[i][0]=i+1;
			data[i][1]=this.model.getValueAt(i,1);
		}
		return data;
	}

	public void setLog(Logica log) {
		this.log = log;
	}

}
