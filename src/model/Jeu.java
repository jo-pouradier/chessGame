package model;

import java.util.List;
import java.util.Objects;

import tools.ChessPiecesFactory;
import tools.ChessSinglePieceFactory;


public class Jeu {

	private final Couleur couleur;
	private final List<Pieces> pieces;
	
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
			if(Objects.equals(piece.getName(), "Roi")) {
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
	
	public boolean isMoveOk(int xInit, int yInit,int xFinal,int yFinal) {
		Pieces p = findPiece(xInit, yInit);
		return p.isMoveOk(xFinal, yFinal);
	}
	
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		Pieces p = findPiece(xInit, yInit);
		return p.move(xFinal, yFinal);
	}

	
	public String getPieceType(int x, int y){
		Pieces p = findPiece(x, y);
		return p.getClass().getSimpleName();
	}
	
	public boolean isPawnPromotion(int xFinal, int yFinal) {
		Pieces p = findPiece(xFinal, yFinal);
		if (Objects.equals(getPieceType(xFinal, yFinal), "Pion")) {
			int pY = p.getY();
			return pY == 0 || pY == 8;
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
		StringBuilder txt = new StringBuilder("Jeu: " + getCouleur() + "\n\t");
		for (Pieces p : pieces) {
			txt.append(p.toString()).append("\n\t");
		}
		return txt.toString();
	}
	
	public boolean undoCapture(int xCatch, int yCatch) {
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
	
	private void afficherJeu() {
        System.out.println("  a b c d e f g h");
        for (int j = 0; j < 8; j++) {
            StringBuilder line = new StringBuilder((j + 1) + " ");
            for (int i = 0; i < 8; i++) {
                if (isPieceHere(i,j) ) {
                    line.append(getPieceType(i, j));
                } else {
                    line.append(".");
                }
                    line.append(" ");
                }
            System.out.println(line);
        }
        System.out.println("\n");
    }
	
	public static void main(String[] args) {
		Jeu jeu = new Jeu(Couleur.NOIR);
		System.out.println(jeu);
		System.out.println("\n");
		
		Pieces p = jeu.findPiece(3, 1);
		System.out.println(p);
		
		Coord c = jeu.getKingCoord();
		System.out.println(c);
		
		jeu.afficherJeu();
        jeu.move(1,1,1,3);
        jeu.move(1, 3, 1, 4);
        jeu.afficherJeu();
		
	}
	
}
