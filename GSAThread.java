import java.util.List;
import java.util.Random;

public class GSAThread extends Thread{

	List<Agent> lesAgents;
	List<Double> lesMasses;
	
	public GSAThread(List<Agent> lA, List<Double>lM) {
		this.lesAgents = lA;
		this.lesMasses = lM;
	}
	
	@Override
	public void run() {
		//ETAPE 4
		//Calcule de la distance de Hamming sur les different couple de l'ensemble
		for(int j=0; j< lesMasses.size()-1;j++) {
			
			for(int k=j+1; k< lesMasses.size();k++) {
				
				//Etape 4A
				//Calcul de la distance de Hamming entre chaque couple
				int distanceH = lesAgents.get(j).getCoord().hammingDistanceTo(lesAgents.get(k).getCoord());
	
				Random r = new Random();

				//Etape 4B
				if(lesMasses.get(k)<lesMasses.get(j)) {
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
