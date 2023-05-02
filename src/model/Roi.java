package model;

public class Roi extends AbstractPiece{

	public Roi(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		int dx = Math.abs(xFinal - this.getX());
		int dy = Math.abs(yFinal - this.getY());
		
		if (dx == 0 && dy == 0) {
			return false;
		}
		if (dx < 2 && dy < 2) {
			return true;
		}
		
		return false;
	}

}
