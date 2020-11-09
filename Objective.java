
/* Objective class (abstract)
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
 * Classe qui défini un Objectif d'un algo heuristique et qui affiche son evaluation si elle
 *  a été faite, elle affiche "objective was not evaluated yet" si aucune evaluation n'a ete faite 
 *  (C'est seulement la dernière evalution qui est donner, c'est la solution courante)
 * 
 */
public abstract class Objective
{
	/**
	 * Nom de l'objectif ainsi que la valeur de la derniere evaluation
	 */
   // protected attributes
   protected String name; 
   protected Double lastValue = null;

   /**
    *Donne le nom de l'objectif
    */
   // getter for objective name
   public String getName()
   { 
      return this.name;
   }

   /**
    *Prend des possible solution de manière aléatoire
    */
   // abstract method "solutionSample" (possibly a random sample)
   public abstract Data solutionSample();

   /**
    *Value l'objectif
    */
   // abstract method "value"
   public abstract double value(Data D);

   /**
    *Affichage de la derniere solution si il y en a une
    */
   // toString
   public String toString()
   {
      String print = "[" + this.name + ", ";
      if (this.lastValue != null)
         print = print + "last computed value = " + this.lastValue;
      else
         print = print + "objective was not evaluated yet";
      return print + "]";
   }
}

