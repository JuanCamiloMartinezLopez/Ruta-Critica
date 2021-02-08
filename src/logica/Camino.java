package logica;

import java.util.ArrayList;

public class Camino {
	int tiempo;
	int nInicial;
	int nFinal;
	ArrayList<Enlace> enlaces;
	ArrayList<Integer> nodos;
	Boolean llego;
	
	public Camino(int nI, int nF) {
		this.nFinal=nF;
		this.nInicial=nI;
		this.tiempo=0;
		if(nI==nF) {
			this.llego=true;
		}else {
			this.llego=false;
		}
		this.enlaces= new ArrayList<Enlace>();
		this.nodos= new ArrayList<Integer>();
	}
	
	public Camino() {
		
	}
	
	public void añadirEnlace(Enlace en) {
		this.enlaces.add(en);
		if(en.Final.num==nFinal) {
			this.llego=true;
		}
	}
	
	public void añadirnodo(int nodo) {
		this.nodos.add(nodo);
		if(nodo==nFinal) {
			this.llego=true;
		}
	}
	
	public int getTiempo() {
		for(int i= 0; i<this.enlaces.size();i++) {
			tiempo+=this.enlaces.get(i).getDuracion();
		}
		return tiempo;
	}
	
	public boolean LLego() {
		return this.llego;
	}
	
	public void info() {
		if(!nodos.isEmpty()) {
			System.out.print(this.nFinal+"->");
			for(Integer n: nodos) {
				System.out.print(n+"->");
			}
			System.out.print(this.nInicial+"|");
			System.out.println(" tiempoCamino: "+this.getTiempo());
		}
		System.out.println(" No hay camino");
	}
	

}
