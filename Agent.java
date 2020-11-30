import java.util.Random;

//normalement on a plus besoin de cette classe
public class Agent extends Data {

	// les 3 coordonnées dans un espace 3D
	private int x, y, z;
	
	public Agent(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
public Agent() {
		Random r = new Random();
		//TODO
		int rand = r.nextInt(4000);
		this.x = rand;
		rand = r.nextInt(4000);
		this.y = rand;
		rand = r.nextInt(4000);
		this.z = rand;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}