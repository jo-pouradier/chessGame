package vue;

import model.*;
import controler.ChessGameControlers;
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
    ChessGameControlers chessGameControler;

    Dimension boardSize;

    Coord lastCoord;

    public ChessGameGUI(String name, ChessGameControlers chessGameControler, java.awt.Dimension boardSize) {
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
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        displayCase();
        // Add a few pieces to the board
//        List<PieceIHM> pieceIMHS = chessGameControler.getPiecesIHM();
//        displayPiecesIHM(pieceIMHS);

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

        Component c;
        System.out.println("initCoord=" + new Coord((lastCoord.x+xAdjustment)/(boardSize.width/8), (lastCoord.y+yAdjustment)/(boardSize.height/8)));
        System.out.println("finalCoord=" + new Coord(e.getX()+xAdjustment, e.getY()+yAdjustment));
        boolean moveOK = chessGameControler.move(new Coord(lastCoord.x+xAdjustment, lastCoord.y+yAdjustment), new Coord(e.getX()+xAdjustment, e.getY()+yAdjustment));
        if (moveOK) {
            System.out.println("move OK");
            c = chessBoard.findComponentAt(e.getX(), e.getY());
            Container parent;
            if (c instanceof JLabel) {
                parent = c.getParent();
                parent.remove(0);
            } else {
                parent = (Container) c;
            }
            parent.add(chessPiece);
            chessPiece.setVisible(true);
        } else {
            System.out.println("move not OK");
            c = chessBoard.findComponentAt(lastCoord.x+xAdjustment, lastCoord.y+yAdjustment);
            Container parent;
            if (c instanceof JLabel) {
                parent = c.getParent();
                parent.remove(0);
            } else {
                parent = (Container) c;
            }
            parent.add(chessPiece);
            chessPiece.setLocation(lastCoord.x+xAdjustment, lastCoord.y+yAdjustment);
            chessPiece.setVisible(true);


        }
        lastCoord = null;
        Container parent;
        if (c instanceof JLabel) {
            parent = c.getParent();
            parent.remove(0);
        } else {
            parent = (Container) c;
        }
        parent.add(chessPiece);

        chessPiece.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
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

