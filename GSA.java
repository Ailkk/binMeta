import java.util.ArrayList;
import java.util.List;

public class GSA extends binMeta {

	//List qui contient les data de tous les agents
	List<Agent> listeAgents;

	static int nbBits;

	//Meilleure solution
	Double best;

	int nombreAgents;

	public GSA(int start,Data data, Objective obj , long maxTime) {
		try
		{
			String msg = "Impossible de creer un objet GSA : ";
			if(maxTime <= 0) throw new Exception(msg + "; le temps d'execution max est de 0 ou est negatif");	    	  
			this.maxTime = maxTime;
			if(start == 0) throw new Exception(msg + "; la population est inferieure ou egale a 0");
			this.nombreAgents =start;
			if (data == null) throw new Exception(msg + "; les data sont NULL");
			this.solution = data;
			if(obj == null) throw new Exception(msg +"; l objectif donne est NULL");
			this.obj=obj;
			this.objValue = objValue;
			this.metaName = "Gravitational Search Algorithm";

			listeAgents  = new ArrayList<Agent>();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	//Creer une liste d'agents aleatoirement 
	public void remplirListeAgents() {
		for(int i = 0; i < nombreAgents; i++) {
			listeAgents.add(new Agent(nbBits,nbBits,nbBits));
		}
	}


	@Override
	public void optimize() {

		long startime = System.currentTimeMillis();

		//ETAPE 1
		//generation de la population d'agents en fonction du nombre voulu
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
		int compteur = 0;
		while (System.currentTimeMillis() - startime < this.maxTime){
			compteur+=1;	
			//ETAPE 3
			//Verification du temps (condition d'arret)
			if((System.currentTimeMillis() - startime < this.maxTime)) {

				GSAThread t = new GSAThread(listeAgents, listeMasse);
				t.run();

			}else {
				//Si le temps est depasse on sort du While
				break;
			}


			//Mise a jour des masse calculees
			listeMasse.clear();
			for(int i=0; i< listeAgents.size();i++) {
				double value = obj.value(listeAgents.get(i).getCoord());
				listeMasse.add(value);
			}

			//ETAPE 2
			for(int i=0; i< listeMasse.size();i++) {
				if(listeMasse.get(i)<best) {
					best =listeMasse.get(i);
					//Mise a jour de Best et de la solution
					this.solution=new Data (listeAgents.get(i).getCoord());
					this.objValue=best;

				}
			}

			//ETAPE 5 
			//Boucle jusqu'a ce que la condition soit arreter => GOTO debut du while
		}

		//pour voir le nombre de fois ou l on passe dans la boucle while
		//le nombre a double depuis le passage au multithreading
		//environ 8000 tours dans la version sequentielle, et 18000 tous dans la version thread
		System.out.println("\nNombre de boucle : " + compteur+"\n");
	}

	// main
	public static void main(String[] args) {

		int timeMax = 10000;  // temps d'exec en ms 

		//nbBits doit etre modulo 4
		nbBits=8;

		//Nombre d'agent
		int NbAgent = 50;

		if(nbBits%4 == 0) {


			//BitConter
			//TODO
			int n=nbBits*3;
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
			int ndigits = nbBits;
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
			int m = 3*nbBits/4;  n = 3*nbBits/m;
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
}


