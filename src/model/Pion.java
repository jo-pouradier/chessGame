package model;

public class Pion extends AbstractPiece implements Pions {
	
	public Pion(Couleur couleur_de_piece, Coord coord){
		super(couleur_de_piece, coord);
	}

	@Override
	public boolean isMoveDiagOk(int xFinal, int yFinal) {
		int dx = Math.abs(xFinal - this.getX());
		int dy = Math.abs(yFinal - this.getY());
		if (dx == 1 && dy == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		int dx = Math.abs(xFinal - this.getX());
		int dy = Math.abs(yFinal - this.getY());
		
		if (dx == 0 && dy == 0) {
			return false;
		}
		if ((dy == 2) && (this.getY()==1 || this.getY()==6)){
			return true;
		}
		if ((dx == 0 && dy == 1)) {
			return true;
		}
		if (isMoveDiagOk(xFinal, yFinal)) {
			return true;
		}
		return false;
	}

}
