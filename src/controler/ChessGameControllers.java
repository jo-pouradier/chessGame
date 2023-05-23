package controler;

import model.Coord;

public interface ChessGameControllers {

	
	/**
	 * @param initCoord coordonnées initiales de la pièce
	 * @param finalCoord coordonnées finales de la pièce
	 * @return true si le déplacement s'est bien passé
	 */
	boolean move(Coord initCoord, Coord finalCoord);

	/**
	 * @return message relatif aux déplacements, capture, etc.
	 */
	String getMessage();
	
	/**
	 * @return true si fin de partie OK (echec et mat, pat, etc.)
	 */
	boolean isEnd();

	/**
	 * @param initCoord coordonnées initiales de la pièce
	 * @return une info dont la vue se servira 
	 * pour empêcher tout déplacement sur le damier
	 */
	boolean isPlayerOK(Coord initCoord);

}
