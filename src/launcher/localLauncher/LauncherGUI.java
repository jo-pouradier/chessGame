package launcher.localLauncher;

//import java.awt.Dimension;
//import java.util.Observer;
//
//import javax.swing.JFrame;
//
//import controler.ChessGameControlers;
//import controler.controlerLocal.ChessGameControler;
//import model.observable.ChessGame;
//import vue.ChessGameGUI;


import controler.ChessGameControlers;
import controler.controlerLocal.ChessGameControler;
import model.Couleur;
import model.Jeu;
import model.Pieces;
import model.observable.ChessGame;
import vue.ChessGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observer;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * @author francoise.perrin
 * Lance l'exécution d'un jeu d'échec en mode graphique.
 * La vue (ChessGameGUI) observe le modèle (ChessGame)
 * les échanges passent par le contrôleur (ChessGameControlers)
 * 
 */
public class LauncherGUI {
	public static void main(String[] args) {

		Dimension dim;
		dim = new Dimension(700, 700);

		ChessGame chessGame;
		ChessGameControlers chessGameControler;
		chessGame = new ChessGame();
		chessGameControler = new ChessGameControler(chessGame);

		JFrame frame = new ChessGameGUI("Chess", chessGameControler, dim);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		chessGame.addObserver((Observer) frame);

	}
}
