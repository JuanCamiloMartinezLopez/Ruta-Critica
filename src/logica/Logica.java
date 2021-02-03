package logica;

import java.util.ArrayList;

public class Logica {
	
	int numNodos;
	ArrayList<Nodo> lista= new ArrayList<Nodo>();
	
	public Logica() {
		this.numNodos=0;
	}

	public Point[] lineaOptima(Nodo a,Nodo b) {
		Point[] points= new Point[2];
    	Point A= new Point();
    	Point B=new Point();
    	double dMenor=0;
    	for(int i=0; i<4;i++) {
    		for(int j=0;j<4;j++) {
    			double d=distancia(a.puntos[i],b.puntos[j]);
    			System.out.println((i+1)+","+(j+1)+": "+d);
    			if(dMenor==0 || dMenor>d) {
    					dMenor=d;
    					B=a.puntos[i];
    					A=b.puntos[j];
    			}
    		}
    	}
    	System.out.println("dMenor: "+dMenor);
    	points[0]=A;
    	points[1]=B;
    	return points;
    }
	
	public double distancia(Point A, Point B) {
    	double d;
    	double x=A.x-B.x;
    	double y=A.y-B.y;
    	d=(int)Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
    	return d;
    }
	
	public void agregarEnlaces(Object data[][]) {
		String infoEnlaces[];
		String infoEnlace[];
		Enlace enlace;
		for(int i=0;i<lista.size();i++) {
			if(data[i][1]=="") {
				continue;
			}
			Nodo inicial=lista.get(i);
			infoEnlaces=String.valueOf(data[i][1]).split("|");
			for(int j=0;j<infoEnlaces.length;j++) {
				infoEnlace=infoEnlaces[j].split(",");
				Nodo fin=lista.get(Integer.parseInt(infoEnlace[0])-1);
				int a=Integer.parseInt(infoEnlace[1]);
				int d=Integer.parseInt(infoEnlace[2]);		
				enlace=inicial.agregarEnlace(fin, a, d);
				Point puntos[]=this.lineaOptima(inicial, fin);
				enlace.setPuntosConexion(puntos);
			}
		}
	}
	
	public int getNumNodos() {
		return numNodos;
	}
	
	public void sumNumNodos() {
		this.numNodos++;
	}

	public ArrayList<Nodo> getLista() {
		return lista;
	}
}
