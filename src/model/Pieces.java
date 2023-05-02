package model;

public interface Pieces {

	int getX();
	int getY();
	Couleur getCouleur();
	boolean isMoveOk(int xFinal, int yFinal);
	boolean move(int xFinal, int yFinal);
	boolean capture();
	String getName();
	
}
