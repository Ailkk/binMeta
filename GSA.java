import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GSA extends binMeta {

	//List qui contient les data de tous les agents
	//Un agent est dÃ©fini par sa position (x,y,z), sa masse, sa vitesse et son acceleration
	//Donc tous les 6 Ã©lÃ©ments de la liste => un nouvel agent
	List<Data> lesAgents;

	int vitesseLumiere;
	long masseMax;
	static int xMax;

	static int yMax;

	static int zMax;

	float constanteG = (float) (0.000000000066742);


	//Meilleur solution
	Double best;

	//Pire solution
	Double worst;


	public void best() {
		//TODO
	}

	public void worst() {
		//TODO
	}

	//CONDITION D'ARRET POSSIBLE : 
	//collision, out of space, nb iterations, temps execution


	public GSA(Data data, Objective obj , long maxTime) {
		try
		{
			String msg = "Impossible de creer un objet GSA : ";
			if(maxTime <= 0) throw new Exception(msg + " Le temps d'execution max est de 0 ou est negatif");	    	  
			this.maxTime = maxTime;
			if (data == null) throw new Exception(msg + "les data sont Null");
			this.solution = data;
			if(obj == null) throw new Exception(" L'objectif donner est null");
			this.obj=obj;
			this.objValue = objValue;
			this.metaName = "Gravitational Search Algorithm";


			//Constante gravitationelle
			constanteG = 

					//Vitesse de la lumiere a 10^6
					vitesseLumiere = 1079;

			//Masse Maximal d'un element
			masseMax = (long) 1000000000000000000L;

			//Dimension max de l'espace calculer a partie des donner entrer et de la vitesse de la lumière
			xMax = (vitesseLumiere * xMax);
			yMax = (vitesseLumiere * yMax);
			zMax = (vitesseLumiere * zMax);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void remplirListeAgents() {
		//pour i de 2 Ã  random creer un agent

		Random r = new Random();
		//xMax * yMax * zMaz / 3
		int maxAgents = r.nextInt(xMax * yMax * zMax / 3);
		for(int i = 2; i < maxAgents; i++) {
			creerAgentRandom();
		}
	}

	public void creerAgentRandom() {
		Random rand = new Random();

		int x = rand.nextInt(xMax);
		lesAgents.add(new Data(x));

		int y = rand.nextInt(yMax);
		lesAgents.add(new Data(y));

		int z = rand.nextInt(zMax);
		lesAgents.add(new Data(z));

		//10^27 - 10^6 kg
		long masse = Math.abs((new Random().nextLong()));
		lesAgents.add(new Data(masse));

		//entre 0 et la vitesse de la lumiÃ¨re (en km/h)
		int vit = rand.nextInt(vitesseLumiere);
		lesAgents.add(new Data(vit));

		int acc = rand.nextInt(vitesseLumiere-vit);
		lesAgents.add(new Data(acc));
	}


	//Plan de Optimize :

	//algo \/
	//initial population v
	//main loop
	//fitness
	//update G, best, worst
	//calculate M and a
	//update velocity and pos
	//end of the loop?
	//return best solution


	@Override
	public void optimize() {

		Random R = new Random();
		Data D = new Data(this.solution);
		long startime = System.currentTimeMillis();

		//Remplie la site des agent avec un nombre d'agent aléatoire (en fonction de l'espace donner), les donner de chaque agents sont elle aussi générer de façon aléatoire.
		remplirListeAgents();

		//CONDITION D'ARRET POSSIBLE : Collition , out of Space et timeout execution
		boolean collition = false;
		boolean outOfSpace = false;

		while ((System.currentTimeMillis() - startime < this.maxTime) && !collition && !outOfSpace){

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
		}
	}

	// main
	public static void main(String[] args) {

		int timeMax = 10000;  // Timer 

		//Taille de l'espace défini pour l'espace de calcule
		xMax = 400;
		yMax = 200;
		zMax = 800;

		//BitConter
		//TODO
		int n = 50;
		Objective obj = new BitCounter(n);
		Data D = obj.solutionSample();
		GSA rw = new GSA(D,obj,timeMax);
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
		rw = new GSA(D,obj,timeMax);
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
		rw = new GSA(D,cp,timeMax);
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


