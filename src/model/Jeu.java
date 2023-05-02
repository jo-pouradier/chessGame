package model;

import java.util.List;
import java.util.LinkedList;
import tools.ChessPiecesFactory;
import tools.ChessSinglePieceFactory;


public class Jeu {

	private Couleur couleur;
	private List<Pieces> pieces;
	
	public Jeu(Couleur couleur) {
		this.couleur = couleur;
		this.pieces = ChessPiecesFactory.newPieces(couleur);
	}
	
	
	public Couleur getCouleur() {
		return couleur;
	}


	public List<Pieces> getPieces() {
		return pieces;
	}

	public Couleur getPieceColor(int x, int y) {
		Pieces p = findPiece(x, y);
		return p.getCouleur();
	}

	public boolean isPieceHere(int x, int y) {
		for (Pieces p : pieces) {
			if(p.getX() == x && p.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	public Pieces findPiece(int x, int y) {
		Pieces pieceR = null;
		if (isPieceHere(x, y)) {
			for (Pieces piece : pieces) {
				if( piece.getX()==x && piece.getY() == y) {
					pieceR = piece;
					break;
				}
			}
		}
		return pieceR;
	}
	
	public Coord getKingCoord() {
		Coord coordR = null;
		for (Pieces piece : pieces) {
			if( piece.getName() == "Roi") {
				coordR = new Coord(piece.getX(), piece.getY());
				break;
			}
		}
		return coordR;
	}
	
	/**
	* @return une vue de la liste des pièces en cours
	* ne donnant que des accès en lecture sur des PieceIHM * (type piece + couleur + liste de coordonnées)
	*/
	public List<PieceIHM> getPiecesIHM(){ 
		PieceIHM newPieceIHM = null;	
		List<PieceIHM> list = new LinkedList<PieceIHM>();
		for (Pieces piece : pieces){ 
			boolean existe = false;
			// si le type de piece existe déjà dans la liste de PieceIHM
			// ajout des coordonnées de la pièce dans la liste de Coord de ce type 
			// si elle est toujours en jeu (x et y != -1)
			for ( PieceIHM pieceIHM : list){
				if ((pieceIHM.getTypePiece()).equals(piece.getClass().getSimpleName())){ 
					existe = true;
					if (piece.getX() != -1){
						pieceIHM.add(new Coord(piece.getX(), piece.getY()));
					}
				}
			}
	       // sinon, création d'une nouvelle PieceIHM si la pièce est toujours en jeu
			if (! existe) {
				if (piece.getX() != -1){
					newPieceIHM = new PieceIHM(piece.getClass().getSimpleName(), piece.getCouleur());
					newPieceIHM.add(new Coord(piece.getX(), piece.getY())); 
					list.add(newPieceIHM);
					
				}
			}
		}
		return list;
	}
	
	public boolean isMoveOk(int xInit, int yInit,int xFinal,int yFinal) {
		Pieces p = findPiece(xInit, yInit);
		boolean b = p.isMoveOk(xFinal, yFinal);
		return b;
	}
	
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		Pieces p = findPiece(xInit, yInit);
		return p.move(xFinal, yFinal);
	}

	
	public String getPieceType(int x, int y){
		Pieces p = findPiece(x, y);
		return p.getClass().getSimpleName();
	}
	
	public boolean isPawnPromotion(int xFinal, int yfinal) {
		Pieces p = findPiece(xFinal, yfinal);
		if (getPieceType(xFinal, yfinal)=="Pion") {
			int pY = p.getY();
			if(pY == 0 || pY == 8){
				return true;
			}
		}
		return false;
	}
	
	public boolean pawnPromotion(int xFinal, int yFinal, String type) {
		if (isPawnPromotion(xFinal, yFinal)) {
			Pieces p = findPiece(xFinal, yFinal);
			pieces.remove(p);
			Pieces newP = ChessSinglePieceFactory.newPiece(couleur, type, xFinal, yFinal);
			pieces.add(newP);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String txt = "Jeu: "+ getCouleur() + "\n\t";
		for (Pieces p : pieces) {
			txt += p.toString() + "\n\t";
		}
		return txt;
	}
	
	public boolean capture(int xCatch, int yCatch) {
		// TODO
		return false;
	}

	public void setPossibleCapture() {
		// TODO
	}
	
	public void setCastling() {
		// TODO
	}
	
	public void undoMove() {
		// TODO
	}
	
	public void undoCapture() {
		// TODO
	}
	
	public static void main(String[] args) {
		Jeu jeu = new Jeu(Couleur.NOIR);
		System.out.println(jeu);
		System.out.println("\n");
		
		Pieces p = jeu.findPiece(3, 1);
		System.out.println(p);
		
		Coord c = jeu.getKingCoord();
		System.out.println(c);
		System.out.println(jeu);
		
	}
	
}
