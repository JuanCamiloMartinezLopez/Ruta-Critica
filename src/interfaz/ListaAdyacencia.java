package interfaz;

import java.awt.Color;
import java.awt.Dimension;

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
		inicializar();
	}
	
	public void inicializar() {
		setBounds(10, 250, 120,280);
		String[] columnNames = {"Nodo", "Adyacencia"};
		Object data[][]=new Object[0][2];
		//this.setSize(120, 200);
		//this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		model=new DefaultTableModel(data, columnNames);
		tabla= new JTable(model);
		tabla.setBounds(0, 0, 120,280);
		//tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		System.out.println(model);
		TableColumnModel columnModel = this.tabla.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(34);
		columnModel.getColumn(1).setPreferredWidth(84);
        add(tabla);
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
