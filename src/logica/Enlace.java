package logica;

public class Enlace {
	public Nodo inicial;
	public Nodo Final;
	public int actividad;
	public int duracion;
	public Point Pinicial;
	public Point PFinal;
	
	public Enlace(Nodo i, Nodo f, int a, int d) {
		this.inicial=i;
		this.Final=f;
		this.actividad=a;
		this.duracion=d;
	}
	
	public void setPuntosConexion(Point p[]) {
		p[0].info();
		p[1].info();
		this.setPinicial(p[0]);
		this.setPFinal(p[1]);
	}

	public Nodo getInicial() {
		return inicial;
	}

	public void setInicial(Nodo inicial) {
		this.inicial = inicial;
	}

	public Nodo getFinal() {
		return Final;
	}

	public void setFinal(Nodo final1) {
		Final = final1;
	}

	public int getActividad() {
		return actividad;
	}

	public void setActividad(int actividad) {
		this.actividad = actividad;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Point getPinicial() {
		return Pinicial;
	}

	public void setPinicial(Point pinicial) {
		Pinicial = pinicial;
	}

	public Point getPFinal() {
		return PFinal;
	}

	public void setPFinal(Point pFinal) {
		PFinal = pFinal;
	}
	
	public String info() {
		return String.valueOf(this.Final.num)+","+this.actividad+","+this.duracion;
	}
}
