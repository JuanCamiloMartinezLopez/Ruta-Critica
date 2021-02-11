package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Logica {

	int numNodos;
	int numEnlaces;
	ArrayList<Nodo> GrafoOriginal = new ArrayList<Nodo>();
	ArrayList<Nodo> GrafoInvertido = new ArrayList<Nodo>();
	ArrayList<Integer> tiemposTem = new ArrayList<Integer>();
	ArrayList<Integer> ActividadesTem = new ArrayList<Integer>();
	ArrayList<Integer> tiemposTar = new ArrayList<Integer>();
	ArrayList<Integer> ActividadesTar = new ArrayList<Integer>();
	ArrayList<Camino> CaminoOriginal = new ArrayList<Camino>();
	ArrayList<Camino> CaminoInverso = new ArrayList<Camino>();
	ArrayList<Nodo> rutaCritica = new ArrayList<Nodo>();
	int[] tiemposTemprano;
	int[] tiemposTarde;
	int MatrizAdyacencia[][];
	Nodo nInicial;
	Nodo nFinal;

	List conj_S = new ArrayList();
	List conjComp_S = new ArrayList();
	List caminos = new ArrayList();
	String tmp;

	public Logica() {
		this.numNodos = 0;
		this.numEnlaces = 0;
	}

	public Point[] lineaOptima(Nodo a, Nodo b) {
		Point[] points = new Point[2];
		Point A = new Point();
		Point B = new Point();
		double dMenor = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				double d = distancia(a.puntos[i], b.puntos[j]);
				System.out.println((i + 1) + "," + (j + 1) + ": " + d);
				if (dMenor == 0 || dMenor > d) {
					dMenor = d;
					A = a.puntos[i];
					B = b.puntos[j];
				}
			}
		}
		System.out.println("dMenor: " + dMenor);
		points[0] = A;
		points[1] = B;
		System.out.println("Ax:" + A.getX() + ",Ay:" + A.getY());
		System.out.println("Bx:" + B.getX() + ",By:" + B.getY());
		return points;
	}

	public double distancia(Point A, Point B) {
		double d;
		double x = A.x - B.x;
		double y = A.y - B.y;
		d = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return d;
	}

	public void agregarEnlaces(Object data[][]) {
		String infoEnlaces[];
		String infoEnlace[];
		String info;
		Enlace enlace;
		for (int i = 0; i < GrafoOriginal.size(); i++) {
			if (data[i][1] == "") {
				continue;
			}
			Nodo inicial = GrafoOriginal.get(i);
			System.out.println(data[i][1]);
			info = String.valueOf(data[i][1]);
			if (info.contains("-")) {
				infoEnlaces = info.split("-");
				for (int j = 0; j < infoEnlaces.length; j++) {
					infoEnlace = infoEnlaces[j].split(",");
					Nodo fin = GrafoOriginal.get(Integer.parseInt(infoEnlace[0]) - 1);
					int a = Integer.parseInt(infoEnlace[1]);
					int d = Integer.parseInt(infoEnlace[2]);
					enlace = inicial.agregarEnlace(fin, a, d);
					this.numEnlaces++;
					Point puntos[] = this.lineaOptima(inicial, fin);
					enlace.setPuntosConexion(puntos);
				}
			} else {
				infoEnlace = info.split(",");
				System.out.println(infoEnlace.length);
				Nodo fin = GrafoOriginal.get(Integer.parseInt(infoEnlace[0]) - 1);
				int a = Integer.parseInt(infoEnlace[1]);
				int d = Integer.parseInt(infoEnlace[2]);
				enlace = inicial.agregarEnlace(fin, a, d);
				this.numEnlaces++;
				Point puntos[] = this.lineaOptima(inicial, fin);
				enlace.setPuntosConexion(puntos);
			}

		}
		llenarMatrizAdyacencia();
	}
	
	public void inicializartiemposTemprano() {
		this.tiemposTemprano= new int[numNodos];
		for(int i=0;i<numNodos;i++) {
			this.tiemposTemprano[i]=0;
		}
	}
	
	public void inicializartiemposTarde() {
		this.tiemposTarde=new int[numNodos];
		for(int i=0;i<numNodos;i++) {
			this.tiemposTarde[i]=0;
		}
		this.tiemposTarde[this.nFinal.num-1]=this.tiemposTemprano[this.nFinal.num-1];
	}
	
	public void recorrerOriginal() {
		this.inicializartiemposTemprano();
		llenarTiemposTemprano(this.nInicial);
		System.out.print("arreglo tiempos temprano \n");
		for(int i=0;i<numNodos;i++) {
			System.out.print(this.tiemposTemprano[i]+ " ");
		}
	}
	
	public void recorrerinversa() {
		this.inicializartiemposTarde();
		llenarTiemposTarde(this.nFinal);
		System.out.print("arreglo tiempos tarde \n");
		for(int i=0;i<numNodos;i++) {
			System.out.print(this.tiemposTarde[i]+ " ");
		}
		this.tiemposTarde[this.nInicial.num-1]=0;
	}
	
	public void llenarTiemposTemprano(Nodo nod) {
		int numF;
		int numI;
		for(Enlace enl: nod.enlaces) {
			numF = enl.Final.num-1;
			numI= enl.inicial.num-1;
			if(this.tiemposTemprano[numF]==0) {
				this.tiemposTemprano[numF]=enl.duracion+tiemposTemprano[numI];
			}else if(this.tiemposTemprano[numF]<enl.duracion+tiemposTemprano[numI]){
				this.tiemposTemprano[numF]=enl.duracion+tiemposTemprano[numI];
			}
			llenarTiemposTemprano(enl.Final);
		}
	}
	
	public void llenarTiemposTarde(Nodo nod) {
		int numF;
		int numI;
		for(Enlace enl: nod.enlaces) {
			numF = enl.Final.num-1;
			numI= enl.inicial.num-1;
			if(this.tiemposTarde[numF]==0) {
				this.tiemposTarde[numF]=tiemposTarde[numI]-enl.duracion;
			}else if(this.tiemposTarde[numF]>tiemposTarde[numI]-enl.duracion){
				this.tiemposTarde[numF]=tiemposTarde[numI]-enl.duracion;
			}
			llenarTiemposTarde(enl.Final);
		}
	}

	public void llenarMatrizAdyacencia() {
		System.out.println("-------Matriz adyacencia-------");
		this.MatrizAdyacencia = new int[numNodos][numNodos];
		Nodo a;
		for (int i = 0; i < numNodos; i++) {
			for (int j = 0; j < numNodos; j++) {
				MatrizAdyacencia[i][j] = -1;
			}
		}
		for (int i = 0; i < numNodos; i++) {
			//MatrizAdyacencia[i][i] = 0;
			a = this.GrafoOriginal.get(i);
			for (Enlace enlace : a.getEnlaces()) {
				MatrizAdyacencia[i][enlace.Final.num - 1] = enlace.duracion;
			}
		}
		for (int i = 0; i < numNodos; i++) {
			for (int j = 0; j < numNodos; j++) {
				System.out.print(MatrizAdyacencia[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public void generarGrafoInverso() {
		Nodo A;
		Nodo B;
		int num;
		ArrayList<Enlace> enlaces;
		Enlace enlace;

		IniciarGrafoInvertido();
		for (int i = 0; i < this.GrafoOriginal.size(); i++) {
			A = GrafoOriginal.get(i);
			enlaces = A.getEnlaces();
			if (enlaces.isEmpty()) {
				continue;
			} else {
				for (int j = 0; j < enlaces.size(); j++) {
					enlace = enlaces.get(j);
					num = enlace.getFinal().num;
					B = GrafoInvertido.get(num - 1);
					B.agregarEnlace(GrafoInvertido.get(i), enlace.getActividad(), enlace.getDuracion());
				}
			}

		}
	}

	public void IniciarGrafoInvertido() {
		for (int i = 0; i < this.GrafoOriginal.size(); i++) {
			GrafoInvertido.add(new Nodo(i + 1));
		}
	}

	public void NodoInicial() {
		Nodo nodo;
		Boolean[] Visitado = new Boolean[numNodos];
		ArrayList<Enlace> enlaces;
		Enlace enlace;
		for (int i = 0; i < numNodos; i++) {
			Visitado[i] = false;
		}
		nInicial = this.GrafoOriginal.get(0);
		int numNodo = 0;
		for (int i = 0; i < numNodos; i++) {
			nodo = this.GrafoOriginal.get(i);
			enlaces = nodo.getEnlaces();
			if (enlaces.isEmpty()) {
				continue;
			} else {
				for (int j = 0; j < enlaces.size(); j++) {
					enlace = enlaces.get(j);
					if (nInicial.num == enlace.getFinal().num) {
						Visitado[numNodo] = true;
						for (int k = 0; k < numNodos; k++) {
							if (!Visitado[k]) {
								numNodo = k;
								nInicial = this.GrafoOriginal.get(k);
							}
						}
					} else {
						Visitado[enlace.getFinal().num - 1] = true;
					}
				}
			}
		}
		System.out.println("Nodo inicial " + nInicial.num);
	}

	public void NodoFinal() {
		Nodo nodo;
		Boolean[] Visitado = new Boolean[numNodos];
		ArrayList<Enlace> enlaces;
		Enlace enlace;
		for (int i = 0; i < numNodos; i++) {
			Visitado[i] = false;
		}
		nFinal = this.GrafoInvertido.get(0);
		int numNodo = 0;
		for (int i = 0; i < numNodos; i++) {
			nodo = this.GrafoInvertido.get(i);
			enlaces = nodo.getEnlaces();
			if (enlaces.isEmpty()) {
				continue;
			} else {
				for (int j = 0; j < enlaces.size(); j++) {
					enlace = enlaces.get(j);
					if (nFinal.num == enlace.getFinal().num) {
						Visitado[numNodo] = true;
						for (int k = 0; k < numNodos; k++) {
							if (!Visitado[k]) {
								numNodo = k;
								nFinal = this.GrafoInvertido.get(k);
							}
						}
					} else {
						Visitado[enlace.getFinal().num - 1] = true;
					}
				}
			}
		}
		System.out.println("Nodo Final " + nFinal.num);
		System.out.println("------------------------");
	}

	public void calcularTiempos() {
		this.recorrerOriginal();
		this.recorrerinversa();
		this.construirRutaCritica();
	}
	
	public ArrayList<Camino> caminos() {
		return null;
	}

	public void distanciasNodoInicial() {
		int dis[] = new int[this.numNodos];
		for (int i = 0; i < this.numNodos; i++) {
			if (i == this.nInicial.num - 1) {
				dis[i] = 0;
			} else {
				dis[i] = -1;
			}
		}
		System.out.println("Arreglo de distancias");
		for (int i = 0; i < this.numNodos; i++) {
			System.out.println(dis[i]);
		}
	}

	public void calcularRutas() {
		Stack<Nodo> nodos;

	}

	public void caminoTar(Nodo n, int objetivo, int tiempoFinal, Camino cam) {
		ArrayList<Enlace> enlaces;
		Enlace enlace = null;

		if (n.num == objetivo) {
			// return tiempoFinal;
		} else if (n.getNumEnlaces() == 0) {
			return;
		} else {
			enlaces = n.getEnlaces();
			for (int j = 0; j < n.numEnlaces; j++) {
				enlace = enlaces.get(j);
				caminoTar(enlace.Final, objetivo, tiempoFinal);
			}
		}

	}

	public int caminoTar(Nodo n, int objetivo, int tiempoFinal) {
		int tiempo = 0;
		ArrayList<Enlace> enlaces;
		Enlace enlace = null;
		Enlace enlaceFinal = null;
		System.out.println("Nodo objetivo: " + objetivo);
		if (n.num == objetivo) {
			return tiempoFinal;
		}
		enlaces = n.getEnlaces();
		for (int j = 0; j < n.numEnlaces; j++) {
			enlace = enlaces.get(j);
			System.out.println("enlace: " + enlace.info());
			if (enlace.Final.num == objetivo) {
				tiempo = enlace.getDuracion();
				enlaceFinal = enlace;
				break;
			} else {
				if (tiempo == 0) {
					tiempo = enlace.getDuracion();
					enlaceFinal = enlace;
				} else {
					if (tiempo < enlace.getDuracion()) {
						tiempo = enlace.getDuracion();
						enlaceFinal = enlace;

					} else {
						continue;
					}
				}
			}
		}
		System.out.println("enlace: " + enlaceFinal.info());
		System.out.println("siguiente nodo: " + enlaceFinal.Final.num);
		System.out.println("Tiempo: " + (tiempoFinal - tiempo));
		return caminoTar(enlaceFinal.Final, objetivo, tiempoFinal - tiempo);
	}

	public void construirRutaCritica() {
		ArrayList<Nodo> aux= new ArrayList<Nodo>();
		ArrayList<Integer> NodoRC=new ArrayList<Integer>();
		int iNod;
		for (int i = 0; i < this.numNodos; i++) {
			if (this.tiemposTemprano[i]==this.tiemposTarde[i]) {
				if(i!=this.nInicial.num-1 && i!=this.nFinal.num-1) {
					aux.add(this.GrafoOriginal.get(i));
					NodoRC.add(i);
				}
			}
		}
		this.rutaCritica.add(this.nInicial);
		iNod=this.nInicial.num-1;
		for( int j= 0;j<NodoRC.size();j++) {
			for(int k=0; k<this.numNodos;k++) {
				if(this.MatrizAdyacencia[iNod][k]!=-1 && NodoRC.contains(k)) {
					this.rutaCritica.add(aux.get(NodoRC.indexOf(k)));
					iNod=k;
				}
			}
		}
		this.rutaCritica.add(this.nFinal);
		System.out.println("Ruta critica");
		for (int i = 0; i < this.rutaCritica.size(); i++) {
			System.out.println(this.rutaCritica.get(i).num);
		}
	}

	public int getNumNodos() {
		return numNodos;
	}

	public void sumNumNodos() {
		this.numNodos++;
	}

	public ArrayList<Nodo> getGrafoOriginal() {
		return GrafoOriginal;
	}

	public ArrayList<Nodo> getRutaCritica() {
		return rutaCritica;
	}

	public void MostrarGrafos() {
		System.out.println("Original");
		for (int i = 0; i < this.numNodos; i++) {
			this.GrafoOriginal.get(i).infoEnlaces();
		}
		System.out.println("invertido");
		for (int i = 0; i < this.numNodos; i++) {
			this.GrafoInvertido.get(i).infoEnlaces();
		}

	}
	
	public String infoGrafos() {
		String info="";
		info+="Original\n";
		for (int i = 0; i < this.numNodos; i++) {
			info+=this.GrafoOriginal.get(i).infoEnlacesString();
		}
		info+="Inverso\n";
		for (int i = 0; i < this.numNodos; i++) {
			info+=this.GrafoInvertido.get(i).infoEnlacesString();
		}
		return info;
	}
	
	public String infoEventos() {
		String info="";
		info+="\n";
		info+="Temprano\n";
		for (int i = 0; i < this.numNodos; i++) {
			info+=String.valueOf(i+1)+" ";
		}
		info+="\n";
		for (int i = 0; i < this.numNodos; i++) {
			info+=this.tiemposTemprano[i]+" ";
		}
		info+="\n";
		info+="\n";
		info+="Tarde\n";
		for (int i = 0; i < this.numNodos; i++) {
			info+=String.valueOf(i+1)+" ";
		}
		info+="\n";
		for (int i = 0; i < this.numNodos; i++) {
			info+=this.tiemposTarde[i]+" ";
		}
		info+="\n";
		info+="\n";
		return info;
	}
	
	public String infoRutaCritica() {
		String info="";
		info+="Ruta Critica\n";
		info+="Eventos\n";
		for(Nodo nod: this.rutaCritica) {
			info+=nod.num+"->";
		}
		info=info.substring(0, info.length()-2);
		info+="\n";
		info+="\n";
		info+="Actividades\n";
		for(int i=0; i<this.rutaCritica.size()-1;i++) {
			for(Enlace enl:this.rutaCritica.get(i).getEnlaces()) {
				if(enl.inicial.num==this.rutaCritica.get(i).num && enl.Final.num==this.rutaCritica.get(i+1).num) {
					info+="a"+enl.actividad+" ";
				}
			}
		}
		return info;
	}
	
	public String infoDuracionRutaCritica() {
		String info="";
		info+="\n\nSiendo la duracion total de la ruta critica:\n";
		info+=this.tiemposTemprano[this.nFinal.num-1];
		return info;
	}
	
}
