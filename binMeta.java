
/* binMeta class
 *
 * binMeta project
 *
 * last update: Nov 1, 2020
 *
 * AM
 */
/**
 * 
 * @author Guibert Thomas
 *
 * Classe qui correspond a notre Metode Heuristique:
 * Elle contient le nom de le l'algorithme heuristique, l'objectif de celle si , 
 * valeur de la fonction objective dans la solution, les Data qui containe la solution et enfin le temps d'exectution max
 * 
 */
public abstract class binMeta
{
   protected String metaName;  // meta-heuristic name
   protected Objective obj;    // objective function
   protected Double objValue;  // objective function value in solution
   protected Data solution;    // Data object containing solution
   protected long maxTime;     // maximum execution time (ms)

   
   /**
    *Retourne ne nom de la metode heuristique
    */
   // getName
   public String getName()
   {
      return this.metaName;
   }

   /**
    *Donne l'objectif de la metode heuristique
    */
   // getObj
   public Objective getObj()
   {
      return this.obj;
   }

   /**
    *retourne la solution courante
    */
   // getObjVal (in the current solution)
   public Double getObjVal()
   {
      return this.objValue;
   }

   /**
    *Donne la solution parmi celle qui se trouve dans les Data
    */
   // getSolution
   public Data getSolution()
   {
      return this.solution;
   }

   /**
    *	Execute la methode heuristique
    */
   // abstract method "optimize" (it runs the meta-heuristic method)
   public abstract void optimize();

   /**
    *	Affiche la solution si il y en a une et le nom de l'objectif lier
    */
   // toString
   public String toString()
   {
      String print = "[" + this.metaName;
      if (this.solution != null)
      {
         if (this.objValue == null)  this.objValue = this.obj.value(this.solution);
         print = print + ": objective " + this.obj.getName() + " has value " + this.objValue + " in current solution";
      }
      else
      {
         print = print + ": with objective " + this.obj.getName() + ", no known solutions up to now";
      }
      return print + "]";
   }
}

