package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logica.Logica;
import logica.Nodo;

public class Vista extends JFrame{
	
	JLabel titulo;
	JTextArea resultado;
	JButton boton;
	JScrollPane resultadoScroll;
	
	Tablero tablero;
	ListaAdyacencia LA;
	Logica log;
	
	public Vista() {
		this.setLayout(null);
		this.setBounds(200, 50, 500,600);
		this.setTitle("Ruta Critica");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		log= new Logica();
		
		titulo= new JLabel("RUTA CRITICA");
		titulo.setFont(new Font("Arial", Font.BOLD, 25));
		titulo.setBounds(150, 5, 200, 40);
		this.getContentPane().add(titulo);
		
		tablero= new Tablero();
		tablero.setLog(log);
		this.getContentPane().add(tablero);
		
		LA= new ListaAdyacencia();
		LA.setLog(log);
		//LA.setBounds(190, 270, 120, 100);
		LA.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		LA.repaint();
		this.getContentPane().add(LA);
		tablero.setLa(LA);
		
		resultado= new JTextArea();
		resultado.setEditable(false);
		resultado.setLineWrap(true);
		resultadoScroll=new JScrollPane(resultado);
		resultadoScroll.setBounds(140, 250, 350, 280);
		resultado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.getContentPane().add(resultadoScroll);
		
		boton= new JButton("Calcular");
		boton.setBounds(10, 535, 120, 20);
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object data[][]=LA.obtenerInfo();
				//Object data[][]=LA.obtenerInfoPrueba();
				/*for(int i=0;i<data.length;i++) {
					System.out.println(data[i][0]+"->"+data[i][1]);
				}*/
				log.agregarEnlaces(data);
				tablero.paintEnlaces();
				log.generarGrafoInverso();
				log.MostrarGrafos();
				log.NodoInicial();
				log.NodoFinal();
				//log.recorrerOriginal();
				log.calcularTiempos();
				//log.distanciasNodoInicial();
				tablero.paintRutaCritica();
				resultado.append(log.infoGrafos());
				resultado.append(log.infoEventos());
				resultado.append(log.infoRutaCritica());
				resultado.append(log.infoDuracionRutaCritica());
			}
		});
		this.getContentPane().add(boton);
		
		this.repaint();
	}
	
}