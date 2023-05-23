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
		} else if ((dy == 2) && (this.getY()==1 || this.getY()==6)){
			return true;
		} else if ((dx == 0 && dy == 1)) {
			// on verifie qu'on va dans le bon sens
			if (this.getCouleur() == Couleur.BLANC && yFinal < this.getY()) {
				return true;
			} else if (this.getCouleur() == Couleur.NOIR && yFinal > this.getY()) {
				return true;
			} else {
				System.out.println("Vous bougez dans le mauvais sens");
				return false;
			}
		} else if (isMoveDiagOk(xFinal, yFinal)) {
			return true;
		}
		return false;
	}

}
