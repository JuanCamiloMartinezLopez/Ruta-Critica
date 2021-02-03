package logica;

import java.util.ArrayList;

public class Nodo {
	int num;
	int x;
	int y;
	int w;
	int h;
	int numEnlaces;
	Point[] puntos= new Point[4];
	ArrayList<Enlace> enlaces;

	public Nodo(int num, int x, int y, int w,int h) {
		this.num=num;
		this.x=x;
		this.y=y-h;
		this.w=w;
		this.h=h;
		this.enlaces= new ArrayList<Enlace>();
		this.numEnlaces=0;
		this.llenarPuntosConexion();
	}
	
	public Nodo(int num) {
		this.num=num;
		this.enlaces= new ArrayList<Enlace>();
		
	}
	
	public void llenarPuntosConexion() {
		this.puntos[0]=new Point((x+(w/2)),(y));
		this.puntos[1]=new Point((x+w),(y+(h/2)));
		this.puntos[2]=new Point((x+(w/2)),(y+h));
		this.puntos[3]=new Point((x),(y+(h/2)));
	}
	
	public Enlace agregarEnlace(Nodo f, int a, int d) {
		this.numEnlaces++;
		this.enlaces.add(new Enlace(this, f, a, d));
		return this.enlaces.get(this.enlaces.size()-1);
	}
	
	public void info() {
		System.out.println("numero:"+num);
		System.out.println("x:"+x);
		System.out.println("y:"+y);
		System.out.println("w:"+w);
		System.out.println("h:"+h);
		System.out.println("Puntos:");
		System.out.print("1:");
		this.puntos[0].info();
		System.out.print("2:");
		this.puntos[1].info();
		System.out.print("3:");
		this.puntos[2].info();
		System.out.print("4:");
		this.puntos[3].info();
		System.out.println("-------------");
	}

	public int getNumEnlaces() {
		return numEnlaces;
	}
	
}
