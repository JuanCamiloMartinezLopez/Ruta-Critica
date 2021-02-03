package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import logica.Logica;
import logica.Nodo;
import logica.Point;

public class Tablero extends JPanel{
	
	
	Logica log;
	ListaAdyacencia la;

	public Tablero() {
		//this.graphics= this.getGraphics();
		setBounds(10, 50, 480, 200);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.WHITE);
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                addNodo(e.getX(),e.getY());
                actualizarLA();
            }
        });
	}
	
	public void addNodo(int x, int y) {
    	log.sumNumNodos();
    	System.out.println(this.getGraphics());
		Rectangle2D bounds=this.getGraphics().getFontMetrics().getStringBounds(String.valueOf(log.getNumNodos()), this.getGraphics());
    	int w=(int)bounds.getWidth();
    	int h= (int)bounds.getHeight();
    	log.getLista().add(new Nodo(log.getNumNodos(),x,y,w,h));
    	paintNodo(x,y);
    	log.getLista().get(log.getNumNodos()-1).info();
    }
	
	public void paintNodo(int x, int y) {
		this.getGraphics().drawString(String.valueOf(log.getNumNodos()), x,y);
    	//g.drawRect(x, y-13, 8, 13);
    }
	
	public void paintEnlace( Point A,Point B) {
    	this.getGraphics().drawLine(A.getX(), A.getY(), B.getX(), B.getY());
    }
	
	public void actualizarLA() {
		la.actualizarTabla();
	}

	public Logica getLog() {
		return log;
	}

	public void setLog(Logica log) {
		this.log = log;
	}
	
	public void setLa(ListaAdyacencia la) {
		this.la = la;
	}

}
