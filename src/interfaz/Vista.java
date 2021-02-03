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

import logica.Logica;
import logica.Nodo;

public class Vista extends JFrame{
	
	JLabel titulo;
	JButton boton;
	
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
		LA.setBounds(190, 270, 120, 100);
		LA.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.getContentPane().add(LA);
		tablero.setLa(LA);
		
		boton= new JButton("prueba");
		boton.setBounds(200, 400, 50, 20);
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object data[][]=LA.obtenerInfo();
				for(int i=0;i<data.length;i++) {
					System.out.println(data[i][0]+"->"+data[i][1]);
				}
				
			}
		});
		this.getContentPane().add(boton);
	}
	
}