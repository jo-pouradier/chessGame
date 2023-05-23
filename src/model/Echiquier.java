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
		Pieces p = jeuCourant.isPieceHere(x, y) ? jeuCourant.findPiece(x, y) : null;
		if (p == null) {
			p = jeuNonCourant.isPieceHere(x, y) ? jeuNonCourant.findPiece(x, y) : null;
		}
		return p;
	}

	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		// on verifie s'il n'y a pas de piece qui serait sauté / sur le chemin
		if (!verifyPath(new Coord(xInit, yInit), new Coord(xFinal, yFinal))) {
			setMessage("Le chemin n'est pas libre");
			System.out.println("Le chemin n'est pas libre");
			return false;
		}
		// on vérifie si la piece peut se déplacer à cet endroit
		Pieces p = getPieceXY(xInit, yInit);
		if(p == null) return false;
		return p.isMoveOk(xFinal, yFinal);
	}
	
	@Override
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean ret = false;
		Pieces movingPiece = getPieceXY(xInit, yInit);
		Pieces capturePiece = getPieceXY(xFinal, yFinal);

		if (isMoveOk(xInit, yInit, xFinal, yFinal)) {
			if (movingPiece == null) {
				setMessage("Pas de pièce à cet endroit");
				return false;
			}
			movingPiece.move(xFinal, yFinal);
			ret = true;
			// on bouge la piece et on vérifie si le roi n'est PAS en échec, on rollback si besoin
			if (roiEnEchec()){
				setMessage("Votre roi est en échec si vous jouez ce coup");
				System.out.println("Votre roi est en échec si vous jouez ce coup");
				jeuCourant.undoMove(movingPiece, xInit, yInit);
				return false;
			}
			// on vérifie si on capture une pièce
			System.out.println("capturePiece : " + capturePiece);
			if (capturePiece != null && capturePiece.getCouleur() != movingPiece.getCouleur()) {
				capturePiece.capture();
				setMessage("Vous avez capturé une pièce");
				System.out.println("Vous avez capturé une pièce");
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
				// on regarde les deplacements de toutes les pieces de l'autre joueur
				// et on regarde s'il y a une pièce sur le passage
				for (Pieces p2: jeuNonCourant.getPieces()){
					if (p2.isMoveOk(p.getX(), p.getY()) && verifyPath(new Coord(p2.getX(),p2.getY()),new Coord(p.getX(),p.getY()))) return true;
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
		// create the vector between the two points
		int xVector = finalCoord.x - initCoord.x;
		int yVector = finalCoord.y - initCoord.y;
		List<PieceIHM> piecesIHM = getPiecesIHM();
		// test straight lines
		if (xVector == 0) {
			int startY = Math.min(initCoord.y, finalCoord.y) + 1;
			int endY = Math.max(initCoord.y, finalCoord.y);
			for (PieceIHM p : piecesIHM) {
				Coord pieceCoord = p.getList().get(0);
				if (pieceCoord.x == initCoord.x && pieceCoord.y >= startY && pieceCoord.y < endY) {
					return false;
				}
			}
		} else if (yVector == 0) {
			int startX = Math.min(initCoord.x, finalCoord.x) + 1;
			int endX = Math.max(initCoord.x, finalCoord.x);
			for (PieceIHM p : piecesIHM) {
				Coord pieceCoord = p.getList().get(0);
				if (pieceCoord.y == initCoord.y && pieceCoord.x >= startX && pieceCoord.x < endX) {
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
