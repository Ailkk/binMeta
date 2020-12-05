import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GSA extends binMeta {

	//List qui contient les data de tous les agents
	List<Agent> listeAgents;


	//Meilleur solution
	Double best;

	
	//Meilleur solution pour l'algo
	double bestSolution;
	
	//Le nombre d'agent donner
	int nombreAgents;


	static //Nombre de bit pour les possition des agents
	int nbBits;



	public GSA(int start,Data data, Objective obj , long maxTime, double bestSol) {
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
			this.bestSolution= bestSol;

			listeAgents  = new ArrayList<Agent>();

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
			listeAgents.add(new Agent(nbBits,nbBits,nbBits));
		}
	}




	/**
	 * Trouve la meilleur solution possible en fonction de la fonction objective
	 */
	@Override
	public void optimize() {

		long startime = System.currentTimeMillis();

		//ETAPE 1

		//Génération de la population d'agent en fonction de nombres voulus
		remplirListeAgents();


		//Determiner la meilleurs solution (Le corps avec la masse la plus petite)
		best = obj.value(listeAgents.get(0).getCoord());
		for(int i=1; i< listeAgents.size();i++) {
			if(obj.value(listeAgents.get(i).getCoord())<best) {
				best =obj.value(listeAgents.get(i).getCoord());
			}
		}

		int compteur= 0;
		while (System.currentTimeMillis() - startime < this.maxTime && best != bestSolution){
			compteur +=1;
			//Nouvelle solution générer par le flipping
			ArrayList<Data> lD = new ArrayList<Data>(3);

			//ETAPE 3
			//Verification du temps (condition d'arret)
			if((System.currentTimeMillis() - startime < this.maxTime)) {

				//ETAPE 4
				//Calcule de la distance de Hamming sur les different couple de l'ensemble

				for(int j=0; j< listeAgents.size()-1;j++) {

					for(int k=j+1; k< listeAgents.size();k++) {

						//Etape 4A
						//Calcul de la distance de Hamming entre chaque couple
						int distanceH = listeAgents.get(j).getCoord().hammingDistanceTo(listeAgents.get(k).getCoord());

						//Cas ou objectif(B)<objectif(A)
						Random r = new Random();

						//Etape 4B
						if(obj.value(listeAgents.get(k).getCoord())<obj.value(listeAgents.get(j).getCoord())) {

							//ETAPE 4B1
							if(distanceH<1)distanceH=1;
							int hA = r.nextInt(distanceH) + 1;

							//ETAPE 4B2
							Data newA = listeAgents.get(j).getCoord().randomSelectInNeighbour(1,hA);


							//ETAPE 4B3
							//La nouvelle valeur de A remplace l'ancienne
							listeAgents.get(j).setCoord(newA);


							//ETAPE 4C
						}else {

							//ETAPE 4C1
							//Cas ou objectif(A)<objectif(B)
							if(distanceH<1)distanceH=1;
							int hB = r.nextInt(distanceH) + 1;

							//ETAPE 4C2
							Data newB = listeAgents.get(k).getCoord().randomSelectInNeighbour(1,hB);

							//ETAPE 4C3
							//La nouvelle valeur de B remplace l'ancienne
							listeAgents.get(k).setCoord(newB);
						}

					}
				}

			}else {
				//Si le temps est dépasser on sort du While
				break;
			}


			//ETAPE 2

			for(int i=0; i< listeAgents.size();i++) {
				if(obj.value(listeAgents.get(i).getCoord())<best) {
					best =obj.value(listeAgents.get(i).getCoord());
					//Mise a jour de Best et de la solution
					this.solution=new Data (listeAgents.get(i).getCoord());
					this.objValue=best;

				}
			}

			//ETAPE 5 
			//Boucle jusqu'a ce que la condition soit arreter

		}
		//Nombre d'uteration de boucle faite avant l'application du multithreading
		System.out.println("Nombre d'itération :" + compteur);

	}

	/**
	 * Main de GSA, fait les calcule de GSA en utilisant les 3 fonction objective Bitecounter , fermat et colorPartition
	 * @param args
	 */
	public static void main(String[] args) {

		int timeMax = 10000;  // Temps d'exectution

		//nbBits doit etre modulo 4
		nbBits=8;
		
		//Nombre d'agent
		int NbAgent = 50;
		
		
		if(nbBits%4 == 0) {
			

			//BitConter
			long startime1 = System.currentTimeMillis();

			int n=nbBits*3;
			Objective obj = new BitCounter(n);
			Data D = obj.solutionSample();
			GSA rw = new GSA(NbAgent,D,obj,timeMax,0.0);
			System.out.println(rw);
			System.out.println("starting point : " + rw.getSolution());
			System.out.println("optimizing ...");
			rw.optimize();
			System.out.println(rw);
			System.out.println("solution : " + rw.getSolution());
			System.out.println();
			
			System.out.println(System.currentTimeMillis() - startime1);
			System.out.println();

			// Fermat
			startime1 = System.currentTimeMillis();
			
			int ndigits = nbBits;
			int exp = 2;
			obj = new Fermat(exp,ndigits);
			D = obj.solutionSample();
			rw = new GSA(NbAgent,D,obj,timeMax,0.0);
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
			
			System.out.println(System.currentTimeMillis() - startime1);
			System.out.println();

			// ColorPartition
			startime1 = System.currentTimeMillis();
			
			int m = 3*nbBits/4;  n = 3*nbBits/m;
			ColorPartition cp = new ColorPartition(n,m);
			D = cp.solutionSample();
			rw = new GSA(NbAgent,D,cp,timeMax,0.0);
			System.out.println(rw);
			System.out.println("starting point : " + rw.getSolution());
			System.out.println("optimizing ...");
			rw.optimize();
			System.out.println(rw);
			System.out.println("solution : " + rw.getSolution());
			cp.value(rw.solution);
			System.out.println("corresponding to the matrix :\n" + cp.show());
			
			System.out.println(System.currentTimeMillis() - startime1);
			System.out.println();
		}
	}

}


