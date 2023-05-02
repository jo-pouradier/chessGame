package model;

public class Tour extends AbstractPiece{


	public Tour(Couleur couleur_de_piece, Coord coord) {
		super(couleur_de_piece, coord);
	}


	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		int dx = Math.abs(xFinal - this.getX());
		int dy = Math.abs(yFinal - this.getY());
		
		if (dx == 0 && dy == 0) {
			return false;
		}
		if (dx == 0 || dy == 0) {
			return true;
		}
		return false;
	}

}
