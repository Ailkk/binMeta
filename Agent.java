import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//normalement on a plus besoin de cette classe
public class Agent  {



	// les 3 coordonnées dans un espace 3D
	private Data coord;
	
	/**
	 * Crée un agent de maniere aléatoire et recupère sa coordonnée
	 * @param x nombres de bits pour x
	 * @param y nombres de bits pour y
	 * @param z nombres de bits pour z
	 */
	public Agent(int x, int y, int z) {
		List<Data> c = new ArrayList<Data>();
		c.add(new Data(x,0.5));
		c.add(new Data(y,0.5));
		c.add(new Data(z,0.5));
		coord = new Data(c);
	}
	

	public Data getCoord() {
		return coord;
	}

	public void setCoord(Data coord) {
		this.coord = coord;
	}
}