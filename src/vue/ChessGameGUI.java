package vue;

import model.*;
import controler.ChessGameControllers;
import tools.ChessImageProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Observable;

public class ChessGameGUI extends JFrame implements MouseListener, MouseMotionListener, java.util.Observer {

    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
    ChessGameControllers chessGameControler;
    Dimension boardSize;
    Coord lastCoord;
    private Component lastCaseEntered = null;

    public ChessGameGUI(String name, ChessGameControllers chessGameControler, java.awt.Dimension boardSize) {
        this.chessGameControler = chessGameControler;
        this.boardSize = boardSize;
        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        chessBoard = new JPanel();
        chessBoard.setName(name);
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        displayCase();
    }

    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        lastCoord = new Coord(e.getX(), e.getY());
        if (c instanceof JPanel)
            return;
        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel) c;
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }


    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) return;
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    public void mouseReleased(MouseEvent e) {
        if (chessPiece == null) return;
        chessPiece.setVisible(false);
        chessGameControler.move(new Coord((lastCoord.x)/(boardSize.width/8), (lastCoord.y)/(boardSize.height/8)), new Coord((e.getX())/(boardSize.width/8), (e.getY())/(boardSize.width/8)));
    }

    public void mouseClicked(MouseEvent e) {
        // will display green square where a move is possible
        List<Coord> possibleMovement = chessGameControler.getPieceListMoveOK(new Coord((e.getX())/(boardSize.width/8), (e.getY())/(boardSize.width/8)));
        for (Coord coord : possibleMovement) {
            JPanel square = (JPanel) chessBoard.getComponent(coord.x + coord.y * 8);
            square.setBackground(Color.green);
        }

    }

    public void mouseMoved(MouseEvent e) {
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        if (c == lastCaseEntered) return; // on est sur la meme case
        else {
            if (lastCaseEntered != null && lastCaseEntered instanceof JPanel) {
                lastCaseEntered.setBackground(getPreviousColor(lastCaseEntered));
            }
        }
        lastCaseEntered = c;
        if (c instanceof JPanel) {
            c.setBackground(Color.green);
        }
    }

    private Color getPreviousColor(Component c) {
        int i = boardSize.width / 8;
        int col = c.getX() / i;
        int row = c.getY() / i;
        return (row + col - 1) % 2 == 0 ? Color.white : Color.black;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private void displayCase(){
        for (int i = 0; i < 8*8; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.black : Color.white);
            else
                square.setBackground(i % 2 == 0 ? Color.white : Color.black);
        }
    }
    private void displayPiecesIHM (List<PieceIHM> pieceIHMS) {
        chessBoard.removeAll();
        displayCase();
        if (pieceIHMS == null) return;
        //Add a few pieces to the board
        JLabel piece;
        JPanel panel;
        for (PieceIHM p : pieceIHMS) {
            if (p.getList().get(0).x == -1 && p.getList().get(0).y == -1) continue;
            String image = ChessImageProvider.getImageFile(p.getTypePiece(), p.getCouleur());
            piece = new JLabel(new ImageIcon(image));
            int place = p.getList().get(0).x + p.getList().get(0).y * 8;
            panel = (JPanel) chessBoard.getComponent(place);
            panel.add(piece);
        }
        chessBoard.revalidate();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update()");
        displayPiecesIHM((List<PieceIHM>) arg);
    }
}

