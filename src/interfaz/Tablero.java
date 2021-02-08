package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.Enlace;
import logica.Logica;
import logica.Nodo;
import logica.Point;

@SuppressWarnings("serial")
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
            	if(log.getNumNodos()<17) {
            		addNodo(e.getX(),e.getY());
                    actualizarLA();
            	}else {
            		JOptionPane.showMessageDialog(null, "Maximo 18 Nodos","Alerta", JOptionPane.INFORMATION_MESSAGE, null);
            	}
            }
        });
	}
	
	public void addNodo(int x, int y) {
    	log.sumNumNodos();
    	System.out.println(this.getGraphics());
		Rectangle2D bounds=this.getGraphics().getFontMetrics().getStringBounds(String.valueOf(log.getNumNodos()), this.getGraphics());
    	int w=(int)bounds.getWidth();
    	int h= (int)bounds.getHeight();
    	log.getGrafoOriginal().add(new Nodo(log.getNumNodos(),x,y,w,h));
    	paintNodo(x,y);
    	log.getGrafoOriginal().get(log.getNumNodos()-1).info();
    }
	
	public void paintNodo(int x, int y) {
		this.getGraphics().drawString(String.valueOf(log.getNumNodos()), x,y);
    	//g.drawRect(x, y-13, 8, 13);
    }
	
	public void paintEnlace( Point A,Point B) {
		//this.getGraphics().drawLine(2,2,10,10);
		System.out.println("Ax:"+A.getX()+",Ay:"+A.getY());
		System.out.println("Bx:"+B.getX()+",By:"+B.getY());
    	this.getGraphics().drawLine(A.getX(), A.getY(), B.getX(), B.getY());
    }
	
	public void paintRutaCritica( Graphics g,Point A,Point B) {
		//this.getGraphics().drawLine(2,2,10,10);
		System.out.println("pintar Ruta critica");
		System.out.println(Color.RED);
		System.out.println(this.getGraphics().getColor());
		g.setColor(Color.RED);
		System.out.println(this.getGraphics().getColor());
		//g.drawOval(10, 10, 5, 5);
		//System.out.println("Ax:"+A.getX()+",Ay:"+A.getY());
		//System.out.println("Bx:"+B.getX()+",By:"+B.getY());
    	g.drawLine(A.getX(), A.getY(), B.getX(), B.getY());
    }
	
	public void paintEnlaces() {
		ArrayList<Nodo> nodos=this.log.getGrafoOriginal();
		ArrayList<Enlace> enlaces;
		Enlace enlace;
		for(int i=0;i<nodos.size();i++) {
			enlaces=nodos.get(i).getEnlaces();
			if(enlaces.size()>0) {
				for(int j=0;j<enlaces.size();j++) {
					enlace=enlaces.get(j);
					paintEnlace(enlace.getPinicial(),enlace.getPFinal());
				}
			}
		}
	}
	
	public void actualizarLA() {
		la.actualizarTabla();
	}
	
	public void paintRutaCritica() {
		ArrayList<Nodo> nodos=this.log.getRutaCritica();
		ArrayList<Enlace> enlaces;
		Enlace enlace;
		for(int i=0;i<nodos.size()-1;i++) {
			enlaces=nodos.get(i).getEnlaces();
			for(int j=0;j<enlaces.size();j++) {
				enlace=enlaces.get(j);
				if(enlace.inicial!=nodos.get(i) || enlace.Final!=nodos.get(i+1))continue;
				paintRutaCritica(this.getGraphics(),enlace.getPinicial(),enlace.getPFinal());
			}
		}
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
