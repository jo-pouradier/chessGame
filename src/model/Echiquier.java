package model;

import java.util.LinkedList;
import java.util.List;

public class Echiquier implements BoardGames {
	
	private final Jeu jeuBlanc;
	private final Jeu jeuNoir;
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

	public Pieces getPieceXY(int x, int y) {
		for (Pieces p : jeuCourant.getPieces()) {
			if (p.getX()==x && p.getY()==y) {
				return p;
			}
		}
		return null;
	}

	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		if (!verifyPath(new Coord(xInit, yInit), new Coord(xFinal, yFinal))) {
			setMessage("Le chemin n'est pas libre");
			System.out.println("Le chemin n'est pas libre");
			return false;
		}
		Pieces p = getPieceXY(xInit, yInit);
		if(p == null)
			return false;
		return p.isMoveOk(xFinal, yFinal);
	}
	
	@Override
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean ret = false;
		Pieces movingPiece;

		if (isMoveOk(xInit, yInit, xFinal, yFinal)) {
			movingPiece = getPieceXY(xInit, yInit);
			if (movingPiece == null) {
				setMessage("Pas de pièce à cet endroit");
				return false;
			}
			movingPiece.move(xFinal, yFinal);
			ret = true;
			// on vérifie si le roi est en échec et on rollback si besoin
			if (roiEnEchec()){
				movingPiece.move(xInit, yInit);
				ret = false;
				setMessage("Votre roi est en échec si vous jouez ce coup");
			}
			Pieces capturePiece = getPieceXY(xFinal, yFinal);
			if (capturePiece != null) {
				capturePiece.capture();
				setMessage("Vous avez capturé une pièce");
			}
		}
		return ret;
	}
	
	public List<PieceIHM> getPiecesIHM(){
		List<PieceIHM> listPieceIHM = new LinkedList<PieceIHM>();
		List<Pieces> allPieces = new LinkedList<>();
		allPieces.addAll(jeuCourant.getPieces());
		allPieces.addAll(jeuNonCourant.getPieces());
		for (Pieces p : allPieces){
			PieceIHM pihm = new PieceIHM(p.getClass().getSimpleName(), p.getCouleur());
			pihm.add(new Coord(p.getX(), p.getY()));
			listPieceIHM.add(pihm);
		}
		return listPieceIHM;
	}

	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Couleur getColorCurrentPlayer() {
		return jeuCourant.getCouleur();
	}

	@Override
	public Couleur getPieceColor(int x, int y) {
		Pieces p = getPieceXY(x, y);
		if (p != null) {
			return p.getCouleur();
		}
		return null;
	}

	public boolean roiEnEchec() {
		for (Pieces p: jeuCourant.getPieces()){
			if (p instanceof Roi){
				// on regarde les deplacements de tout les pieces de l'autre joueur
				for (Pieces p2: jeuNonCourant.getPieces()){
					if (p2.isMoveOk(p.getX(), p.getY())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Echiquier e = new Echiquier();
		System.out.println(e);
		System.out.println(e.getPiecesIHM());

	}

	public boolean verifyPath(Coord initCoord, Coord finalCoord) {
		System.out.println("verify path");
		// create the vector between the two points
		int xVector = finalCoord.x - initCoord.x;
		int yVector = finalCoord.y - initCoord.y;
		System.out.println("xVector : " + xVector + " yVector : " + yVector);
		System.out.println("initCoord : " + initCoord + " finalCoord : " + finalCoord);

		List<PieceIHM> piecesIHM = getPiecesIHM();

		// test straight lines
		if (xVector == 0) {
			int startY = Math.min(initCoord.y, finalCoord.y) + 1;
			int endY = Math.max(initCoord.y, finalCoord.y);
			for (PieceIHM p : piecesIHM) {
				Coord pieceCoord = p.getList().get(0);
				if (pieceCoord.x == initCoord.x && pieceCoord.y >= startY && pieceCoord.y < endY) {
					System.out.println(p);
					return false;
				}
			}
		} else if (yVector == 0) {
			int startX = Math.min(initCoord.x, finalCoord.x) + 1;
			int endX = Math.max(initCoord.x, finalCoord.x);
			for (PieceIHM p : piecesIHM) {
				Coord pieceCoord = p.getList().get(0);
				if (pieceCoord.y == initCoord.y && pieceCoord.x >= startX && pieceCoord.x < endX) {
					System.out.println(p);
					return false;
				}
			}
		}
		// test diagonals
		else if (Math.abs(xVector) == Math.abs(yVector)) {
			int startX = initCoord.x + (xVector > 0 ? 1 : -1);
			int startY = initCoord.y + (yVector > 0 ? 1 : -1);

			int numSteps = Math.abs(xVector) - 1;
			for (int i = 0; i < numSteps; i++) {
				int currentX = initCoord.x + (xVector > 0 ? (i + 1) : -(i + 1));
				int currentY = initCoord.y + (yVector > 0 ? (i + 1) : -(i + 1));

				for (PieceIHM p : piecesIHM) {
					Coord pieceCoord = p.getList().get(0);
					if (pieceCoord.x == currentX && pieceCoord.y == currentY) {
						System.out.println(p);
						return false;
					}
				}
			}
		}
		return true;
	}

}
