package model;

public class Reine extends AbstractPiece{

	public Reine(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		int dx = Math.abs(xFinal - this.getX());
		int dy = Math.abs(yFinal - this.getY());
		
		if (dx == 0 && dy == 0) {
			return false;
		}
		if ( (dx == dy) || (dx == 0 || dy == 0) ) {
			return true;
		}
		return false;
	}

}
