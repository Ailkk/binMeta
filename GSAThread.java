import java.util.List;
import java.util.Random;

public class GSAThread extends Thread{

	List<Agent> lesAgents;
	Objective obj;
	int debut, fin;
	
	public GSAThread(List<Agent> lA, Objective o, int d, int f) {
		this.lesAgents = lA;
		this.obj = o;
		this.debut = d;
		this.fin = f;
	}
	
	@Override
	public void run() {
		
		for(int j=debut; j< fin-1;j++) {
			
			for(int k=j+1; k< fin;k++) {
				Random r = new Random();
				//Etape 4A
				//Calcul de la distance de Hamming entre chaque couple
				int distanceH = lesAgents.get(j).getCoord().hammingDistanceTo(lesAgents.get(k).getCoord());
				//Etape 4B
				if(obj.value(lesAgents.get(k).getCoord())<obj.value(lesAgents.get(j).getCoord())) {
					//ETAPE 4B1
					//Cas ou objectif(B)<objectif(A)
					if(distanceH<1)distanceH=1;
					int hA = r.nextInt(distanceH) + 1;
					
					//ETAPE 4B2
					Data newA = lesAgents.get(j).getCoord().randomSelectInNeighbour(1,hA);
					
					
					//ETAPE 4B3
					//La nouvelle valeur de A remplace l'ancienne
					lesAgents.get(j).setCoord(newA);
					
					
				//ETAPE 4C
				}else {
					//ETAPE 4C1
					//Cas ou objectif(A)<objectif(B)
					if(distanceH<1)distanceH=1;
					int hB = r.nextInt(distanceH) + 1;
					
					//ETAPE 4C2
					Data newB = lesAgents.get(k).getCoord().randomSelectInNeighbour(1,hB);
					
					//ETAPE 4C3
					//La nouvelle valeur de B remplace l'ancienne
					lesAgents.get(k).setCoord(newB);
				}
			}
		}
	}
}
