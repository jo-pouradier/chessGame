package model;

public abstract class AbstractPiece implements Pieces{
	
	private Couleur couleur;
	private Coord coord;
	private String name;
	
	public AbstractPiece(Couleur couleur, Coord coord){
		this.couleur = couleur;
		this.coord = coord;
		this.name = this.getClass().getSimpleName();
	}
	
	public int getX() {
		return coord.x;
	}
	public int getY() {
		return coord.y;
	}
	
	public Couleur getCouleur(){
		return couleur;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean capture() {
		coord = new Coord(-1,-1);
		return true;
	}
	
	@Override
	public String toString(){
		return "Piece: " + name + ", Coord: " + coord + ", Couleur: " + couleur;
		
	}
	
	public boolean move(int x, int y) {
		if (isMoveOk(x, y)) {
			coord = new Coord(x,y);
			return true;
		}
		return false;
	}

	public boolean move (int x, int y, boolean force){
		if (force) {
			coord = new Coord(x,y);
			return true;
		}
		return false;
	}
	public abstract boolean isMoveOk(int xFinal, int yFinal);
	
	public static void main(String[] args) {
		Pieces maTour = new Tour(Couleur.NOIR, new Coord(0, 0));
		System.out.println(maTour);
		maTour.move(5, 0);
		System.out.println(maTour);
		maTour.move(6, 1);
		System.out.println(maTour);
	}

}
