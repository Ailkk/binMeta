import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GSA extends binMeta {

	//List qui contient les data de tous les agents
	List<Agent> listeAgents;


	//Meilleur solution
	Double best;
	int nombreAgents;
	


	//CONDITION D'ARRET POSSIBLE : 
	//collision, out of space, nb iterations, temps execution


	public GSA(int start,Data data, Objective obj , long maxTime) {
		try
		{
			String msg = "Impossible de creer un objet GSA : ";
			if(maxTime <= 0) throw new Exception(msg + " Le temps d'execution max est de 0 ou est negatif");	    	  
			this.maxTime = maxTime;
			if(start == 0) throw new Exception(msg + " La population est inferieur ou Egal a 0");
			this.nombreAgents =start;
			if (data == null) throw new Exception(msg + "les data sont Null");
			this.solution = data;
			if(obj == null) throw new Exception(" L'objectif donner est null");
			this.obj=obj;
			this.objValue = objValue;
			this.metaName = "Gravitational Search Algorithm";

			listeAgents  = new ArrayList<Agent>();

			/*
			//Constante gravitationelle
			constanteG = 

					//Vitesse de la lumiere a 10^6
					vitesseLumiere = 1079;

			//Masse Maximal d'un element
			masseMax = (long) 1000000000000000000L;

			//Dimension max de l'espace calculer a partie des donner entrer et de la vitesse de la lumière
			xMax = (vitesseLumiere * xMax);
			yMax = (vitesseLumiere * yMax);
			zMax = (vitesseLumiere * zMax);*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	//Crée une liste d'agent aléatoirement 
	public void remplirListeAgents() {
		for(int i = 0; i < nombreAgents; i++) {
			listeAgents.add(new Agent(8,8,8));
		}
	}

	
	
	public Data fliped (Data a, Data b , int c) {
		
		int hammingCompteur=c;
		//for (int i = 0; i < a.numberOfBytes(); i++)
	     // {
			a.getCurrentBit();
			b.getCurrentBit();
			
			while(hammingCompteur > 0) {
				int aB =a.getNextBit();
				int bB =b.getNextBit();
				if(aB!=bB) {
					aB=bB;
					hammingCompteur --;
				}
			}
	        /* byte byt = (byte) a.getBit(i);
	         for (int l = 7; l >= 0; l--)
	         {
	            //faire un truc
	         }
	      }*/
		return a;
		
	}


	@Override
	public void optimize() {

		Random R = new Random();
		Data D = new Data(this.solution);
		long startime = System.currentTimeMillis();


		//ETAPE 1


		//Génération de la population d'agent en fonction de nombres voulus
		remplirListeAgents();


		List<Double> listeMasse = new ArrayList<Double>();

		//Calcule de leurs masse
		for(int i=0; i< listeAgents.size();i++) {
			double value = obj.value(listeAgents.get(i).getCoord());
			listeMasse.add(value);
		}
		
		//Determiner la meilleurs solution (Le corps avec la masse la plus petite)
		best = listeMasse.get(0);
		for(int i=1; i< listeMasse.size();i++) {
			if(listeMasse.get(i)<best) {
				best =listeMasse.get(i);
			}
		}
		while (System.currentTimeMillis() - startime < this.maxTime){
			
			//Nouvelle solution générer par le flipping
			ArrayList<Data> lD = new ArrayList<Data>(3);

			//ETAPE 2

			for(int i=0; i< listeMasse.size();i++) {
				//System.out.println(best + "   -   "+ listeMasse.get(i));
				if(listeMasse.get(i)<best) {
					best =listeMasse.get(i);
					
					//Mise a jour de Best et de la solution

					this.solution=new Data (listeAgents.get(i).getCoord());
					this.objValue=best;
					
				}
			}
			
			//ETAPE 3
			//Verification du temps (condition d'arret)
			if((System.currentTimeMillis() - startime < this.maxTime)) {

				
				//ETAPE 4
				//Calcule de la distance de Hamming sur les different couple de l'ensemble
				
				
				for(int j=0; j< listeMasse.size()-1;j++) {
					
					for(int k=j+1; k< listeMasse.size();k++) {
						
						//Etape 4A
						//Calcul de la distance de Hamming entre chaque couple
						int distanceH = listeAgents.get(j).getCoord().hammingDistanceTo(listeAgents.get(k).getCoord());
						
						//Cas ou objectif(B)<objectif(A)
						Random r = new Random();

						//Etape 4B
						if(listeMasse.get(k)<listeMasse.get(j)) {
							//ETAPE 4B1
							if(distanceH<1)distanceH=1;
							int hA = r.nextInt(distanceH) + 1;
							//ETAPE 4B2
							
							
							//Data newA = fliped(listeAgents.get(j).getCoord(),listeAgents.get(k).getCoord(),hA);
							
							//System.out.println("hA :" + hA);
							Data newA = listeAgents.get(j).getCoord().randomSelectInNeighbour(1,hA);
							
							
							//ETAPE 4B3
							//La nouvelle valeur de A remplace l'ancienne
							//listeAgents.set(j, newA);
							listeAgents.get(j).setCoord(newA);
							
							
						//ETAPE 4C
						}else {
							
							//ETAPE 4C1
							//Cas ou objectif(A)<objectif(B)
							if(distanceH<1)distanceH=1;
							int hB = r.nextInt(distanceH) + 1;
							
							//ETAPE 4C2
							//Agent newB = null;
							//Data newB = fliped(listeAgents.get(k).getCoord(),listeAgents.get(j).getCoord(),hB);
							//System.out.println("hB :" + hB);
							Data newB = listeAgents.get(k).getCoord().randomSelectInNeighbour(1,hB);
							
							//ETAPE 4C3
							//La nouvelle valeur de B remplace l'ancienne
							//listeAgents.set(k, newB);
							listeAgents.get(k).setCoord(newB);
						}
						
					}
				}
				
				//Afficher l'evolution des agent durant l'execution
				/*for(int p=0; p<listeAgents.size();p++) {
					System.out.println("Agent " + p + " : " + listeAgents.get(p).getCoord());
				}*/
				
			
			}else {
				//Si le temps est dépasser on sort du While
				break;
			}
			
			
			//Mise a jour des masse calculer
			//Calcule de leurs masse
			listeMasse.clear();
			for(int i=0; i< listeAgents.size();i++) {
				double value = obj.value(listeAgents.get(i).getCoord());
				listeMasse.add(value);
			}
			
			//ETAPE 5 
			//Boucle jusqu'a ce que la condition soit arreter
			
		}
		
	}

	// main
	public static void main(String[] args) {

		int timeMax = 10000;  // Timer 

		//Taille de l'espace défini pour l'espace de calcule

		int nbBit=8;
		int NbAgent = 50;

		//BitConter
		//TODO
		//int n = 50;
		int n=nbBit*3;
		Objective obj = new BitCounter(n);
		Data D = obj.solutionSample();
		GSA rw = new GSA(NbAgent,D,obj,timeMax);
		System.out.println(rw);
		System.out.println("starting point : " + rw.getSolution());
		System.out.println("optimizing ...");
		rw.optimize();
		System.out.println(rw);
		System.out.println("solution : " + rw.getSolution());
		System.out.println();

		// Fermat
		//TODO
		int exp = 2;
		//int ndigits = 10;
		int ndigits = nbBit;
		obj = new Fermat(exp,ndigits);
		D = obj.solutionSample();
		rw = new GSA(NbAgent,D,obj,timeMax);
		System.out.println(rw);
		System.out.println("starting point : " + rw.getSolution());
		System.out.println("optimizing ...");
		rw.optimize();
		System.out.println(rw);
		System.out.println("solution : " + rw.getSolution());
		Data x = new Data(rw.solution,0,ndigits-1);
		Data y = new Data(rw.solution,ndigits,2*ndigits-1);
		Data z = new Data(rw.solution,2*ndigits,3*ndigits-1);
		System.out.print("equivalent to the equation : " + x.posLongValue() + "^" + exp + " + " + y.posLongValue() + "^" + exp);
		if (rw.objValue == 0.0)
			System.out.print(" == ");
		else
			System.out.print(" ?= ");
		System.out.println(z.posLongValue() + "^" + exp);
		System.out.println();

		// ColorPartition
		//TODO
		n = 2;  int m = nbBit*3/2;
		ColorPartition cp = new ColorPartition(n,m);
		D = cp.solutionSample();
		rw = new GSA(NbAgent,D,cp,timeMax);
		System.out.println(rw);
		System.out.println("starting point : " + rw.getSolution());
		System.out.println("optimizing ...");
		rw.optimize();
		System.out.println(rw);
		System.out.println("solution : " + rw.getSolution());
		cp.value(rw.solution);
		System.out.println("corresponding to the matrix :\n" + cp.show());
	}

}


