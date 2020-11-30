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
			listeAgents.add(new Agent());
		}
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
			double value = obj.value(listeAgents.get(i));
			listeMasse.add(value);
		}

		while (System.currentTimeMillis() - startime < this.maxTime){

			//Nouvelle solution générer par le flipping
			ArrayList<Data> lD = new ArrayList<Data>(3);

			//ETAPE 2

			//Determiner la meilleurs solution (Le corps avec la masse la plus petite)
			best = listeMasse.get(0);
			for(int i=1; i< listeMasse.size();i++) {
				if(listeMasse.get(i)<best) {
					best =listeMasse.get(i);
				}
			}

			//ETAPE 3
			//Verification du temps (condition d'arret)

			if(!(System.currentTimeMillis() - startime < this.maxTime)) {

				
				//ETAPE 4
				//Calcule de la distance de Hamming sur les different couple de l'ensemble
				
				
				for(int j=0; j< listeMasse.size()-1;j++) {
					for(int k=j+1; k< listeMasse.size();k++) {
						
						//Etape 4A
						//Calcul de la distance de Hamming entre chaque couple
						int distanceH = listeAgents.get(j).hammingDistanceTo(listeAgents.get(k));
						
						//Cas ou objectif(B)<objectif(A)
						Random r = new Random();
						
						//Etape 4B
						if(listeMasse.get(k)<listeMasse.get(j)) {
							//ETAPE 4B1
							int hA = r.nextInt(distanceH);
							//ETAPE 4B2
							
							
							Agent newA = null;
							
							
							//ETAPE 4B3
							//La nouvelle valeur de A remplace l'ancienne
							listeAgents.set(j, newA);
							
							
						//ETAPE 4C
						}else {
							
							//ETAPE 4C1
							//Cas ou objectif(A)<objectif(B)
							int hB = r.nextInt(distanceH);
							
							//ETAPE 4C2
							Agent newB = null;
							
							
							
							//ETAPE 4C3
							//La nouvelle valeur de B remplace l'ancienne
							listeAgents.set(k, newB);
							
						}
						
					}
				}
				
				
				

				/*
				//Nouvelle liste avec les positions et la vitesse mise a jour
				List<Data> AgentResultat = null;

				//Evaluation des Agents avec la fonction obj de GSA donner (Soit BtCounter , Soit ColorPartition ou soit Fermat)

				//Pour chaque agent on applique fitness
				Iterator listAgentForFitness = lesAgents.iterator();
				while(listAgentForFitness.hasNext()) {

					//Recuperation des attributs des agents
					int x = (int) listAgentForFitness.next();
					int y = (int) listAgentForFitness.next();
					int z = (int) listAgentForFitness.next();
					long masse = (long) listAgentForFitness.next();
					int vitesse = (int) listAgentForFitness.next();
					int acceleration = (int) listAgentForFitness.next();


					//Calcule de fitness en fonction des agent//TODO
					double valueFitness = obj.value(D);
					if (this.objValue > valueFitness)
					{
						this.objValue = valueFitness;
						this.solution = new Data(D);
					}

					//Mise a jour de best
					if(objValue > best) {
						best = objValue;
					}
					//Mise a jour de worst
					if(objValue < worst) {
						worst = objValue;
					}
				}

				*/
				/*
				//Mise a jour de la constante G //TODO

				constanteG= constanteG; //TODO



				//Calcule de la masse ? et de l'acceleration pour chaque agent 

				Iterator listAgentForVelocityAndPos = lesAgents.iterator();
				while(listAgentForVelocityAndPos.hasNext()) {

					//Recuperation des attributs des agents
					int x = (int) listAgentForVelocityAndPos.next();
					int y = (int) listAgentForVelocityAndPos.next();
					int z = (int) listAgentForVelocityAndPos.next();
					long masse = (long) listAgentForVelocityAndPos.next();
					int vitesse = (int) listAgentForVelocityAndPos.next();
					int acceleration = (int) listAgentForVelocityAndPos.next();


					//Calcule de M de l'agent ? //TODO


					//Calcule de l'acceleration

					acceleration = vitesse;//TODO



					//Calcule des nouvelle position

					x = x;//TODO
					y = y;//TODO
					z = z;//TODO

					//Verification de l'emplacement de l'agent dans l'espace
					if( (x<0) || (x>xMax)) {
						outOfSpace=true;
					}
					if( (y<0) || (y>yMax)) {
						outOfSpace=true;
					}
					if( (z<0) || (z>zMax)) {
						outOfSpace=true;
					}


					//Ajoute de l'agent dans la nouvelle liste

					AgentResultat.add(new Data(x));
					AgentResultat.add(new Data(y));
					AgentResultat.add(new Data(z));
					AgentResultat.add(new Data(masse));
					AgentResultat.add(new Data(vitesse));
					AgentResultat.add(new Data(acceleration));
				}



				//La liste de base est remplacr par la nouvelle
				lesAgents.clear();
				lesAgents.addAll(AgentResultat);


				*/
				// a new solution is generated by flipping a consecutive subset of its bits

				/*
	    	 ArrayList<Data> lD = new ArrayList<Data>();
	         int n = D.numberOfBits();
	         int i = R.nextInt(n);
	         if (i != 0 && i != n - 1)
	         {
	            int j = i + 1 + R.nextInt(n - i - 1);
	            if (j != n - 1)
	            {
	               lD.add(new Data(D,0,i-1));
	               lD.add(new Data(D,i,j-1,true));
	               lD.add(new Data(D,j,n-1));
	               D = new Data(lD);
	            }
	            else
	            {
	               lD.add(new Data(D,0,i-1,true));
	               lD.add(new Data(D,i,n-1));
	               D = new Data(lD);
	            }
	         }
	         else
	         {
	            int j = 1 + R.nextInt(n - 2);
	            lD.add(new Data(D,0,j-1));
	            lD.add(new Data(D,j,n-1,true));
	            D = new Data(lD);
	         }

			double value = obj.value(D);
			if (this.objValue > value)
			{
				this.objValue = value;
				this.solution = new Data(D);
			}*/
				
				
			}else {
				//Si le temps est dépasser on sort du While
				break;
			}
			
			//ETAPE 5 
			//Boucle jusqu'a ce que la condition soit arreter
		}
		
	}

	// main
	public static void main(String[] args) {

		int timeMax = 10000;  // Timer 

		//Taille de l'espace défini pour l'espace de calcule

		int NbAgent = 50;

		//BitConter
		//TODO
		int n = 50;
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
		int ndigits = 10;
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
		n = 4;  int m = 15;
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


