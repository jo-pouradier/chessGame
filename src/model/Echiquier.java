package model;

import java.util.List;

public class Echiquier implements BoardGames {
	
	private Jeu jeuBlanc;
	private Jeu jeuNoir;
	private Jeu jeuCourant;
	private Jeu jeuNonCourant;
	private String message = "";
	
	public Echiquier() {
		this.jeuBlanc = new Jeu(Couleur.BLANC);
		this.jeuNoir = new Jeu(Couleur.NOIR);
		this.jeuCourant = jeuBlanc;
		this.jeuNonCourant = jeuNoir;
	}
	
	private void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return "Echiquier \n[jeuBlanc=" + jeuBlanc + ", \njeuNoir=" + jeuNoir + ",\n message=" + message + "]";
	}

	public void switchJoueur() {
		Jeu jeuTemp = jeuCourant;
		jeuCourant = jeuNonCourant;
		jeuNonCourant = jeuTemp;
	}

	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		for (Pieces p : jeuCourant.getPieces()) {
			if (p.getX()==xInit && p.getY()==yInit) {
				if (p.isMoveOk(xFinal, yFinal)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		if (isMoveOk(xInit, yInit, xFinal, yFinal)) {
			
		}
		return false;
	}
	
	public List<PieceIHM> getPiecesIHM(){
		// TODO
		
		return null;
	}

	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Couleur getColorCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Couleur getPieceColor(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		Echiquier e = new Echiquier();
		System.out.println(e);
		
	}

}
