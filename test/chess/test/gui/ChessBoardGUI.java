package chess.test.gui;

import chess.board.Board;
import chess.board.ChessSquare;
import chess.constants.IConstants;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * 
 * @author sijar
 */
public class ChessBoardGUI extends JFrame implements MouseListener, MouseMotionListener {

    private static final int BOARD_UI_SIZE = 400;
    private Dimension boardSize = new Dimension(BOARD_UI_SIZE, BOARD_UI_SIZE);
    private JLayeredPane layeredPane;
    private JPanel jCBoard;
    private JPanel box;
    private JLabel chessPiece;
    private int xAdjustment;
    private int yAdjustment;
    private Container lastParent;
    //Using Chess Engine API
    private Board board = Board.getInstance();
    private ChessSquare[][] squares = board.getUnmodifiableChessSquares();
    public static final int PER_SQUARE_SIZE = BOARD_UI_SIZE / 8; //total size/no_of_sqaure)
    private Icon chessPieceIcon;
    // Position of Piece UI 
    private int prevX = -1, prevY = -1;

    /**
     * Constructor
     * 
     */
    public ChessBoardGUI() {
        // Use a Layered Pane for this this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
        // Add a chess jCBoard to the Layered Pane
        jCBoard = new JPanel();
        // set "jCBoard" to the lowest layer (default_layer)
        layeredPane.add(jCBoard, JLayeredPane.DEFAULT_LAYER);
        jCBoard.setLayout(new GridLayout(8, 8));
        jCBoard.setPreferredSize(boardSize);
        jCBoard.setBounds(0, 0, boardSize.width, boardSize.height);
        initializeBoardUI();
    }

    /**
     *
     * Method will the Board
     * It fill the alterate squares with black and white colors
     * and will populate the suitable squares with chess pieces
     * 
     */
    public void initializeBoardUI() {
        try {
            for (ChessSquare[] chessSquares : squares) {
                for (ChessSquare square : chessSquares) {
                    box = new JPanel(new BorderLayout());
                    jCBoard.add(box, BorderLayout.CENTER);
                    Color color = Color.white;
                    if (square.getColor() == ChessSquare.BoardColor.BLACK.ordinal()) {
                        color = Color.black;
                    }
                    box.setBackground(color);
                    if (square.getPiece() != null) {
                        String fileName = IConstants.IMAGE_FOLDER + square.getPiece().getImageName() + ".png";
                        System.err.println(fileName);
                        JLabel piecesImage = new JLabel(new ImageIcon(fileName));
                        box.add(piecesImage);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ChessBoardGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


//    /**
//     * Restore the components
//     *
//     */
//    private void restoreSquareColor(){
//        for(int i=0; i < squares.length; ++i){
//            for(int j=0; j < squares[i].length; ++j){
//                //System.out.println("%%%% %%% " + squares[i][j].getColor());
//            }
//        }
//       Component[] components = jCBoard.getComponents();
//        for (Component component : components) {
//            //System.out.println("Components :" + component.getForeground());
//            //System.out.println("Components :" + component.getBackground());
//            //System.out.println("Components :" + component.getX() + "," + component.getY());
//        }
//    }

    /*
     *
     *
     * Add the selected chess piece to the dragging layer so it can be moved
     * 
     */
    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component component = jCBoard.findComponentAt(e.getX(), e.getY());
        if (component instanceof JPanel) {
            return;
        }
        lastParent = component.getParent();
        Point parentLocation = component.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel) component;
        //update the position
        this.prevX = e.getX() + xAdjustment;
        this.prevY = e.getY() + yAdjustment;        
        //System.out.println("Coordinates :" + this.prevX + " " + this.prevY);
        //System.out.println("Chess Piece :" + chessPiece.getIcon());
        this.chessPieceIcon = chessPiece.getIcon();                
        //chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        //chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        //layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);        
    }

    /*
    
     ** Move the chess piece around

     */
    public void mouseDragged(MouseEvent me) {
        if (chessPiece == null) {
            return;
        }        
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    /*
     *
     *
     * Drop the chess piece back onto the chess jCBoard
     */
    public void mouseReleased(MouseEvent e) {
        Component c = jCBoard.findComponentAt(e.getX(), e.getY());
        //System.out.println("Previous Location :"+ e.getX() + " " + e.getY());
        if (c == null) {
            layeredPane.remove(chessPiece);
            lastParent.add(chessPiece);
            Rectangle r = lastParent.getBounds();
            chessPiece.setLocation(r.x + xAdjustment, r.y + yAdjustment);
            lastParent.validate();
            lastParent.repaint();
            return;
        }
        int x = (int) c.getBounds().getX();
        int y = (int) c.getBounds().getY();
        //container and parent information
        //System.out.println(c);
        //System.out.println("%%%%%% Container Parent Information :" + c.getParent());
        //check whether colliding or not
        if( (c.getParent().getX() != 0) && (c.getParent().getY() != 0)){
            x = c.getParent().getX();
            y = c.getParent().getY();
        }
        //System.out.println("Altered Location :"+ x + " " + y);
        /////////////////// VALIDATE THE MOVE ////////////////////        
        if(!board.movePiece(generateMoveCommand(this.prevX,this.prevY,x,y))){
            //System.out.print("\n******Incorrect MOVE :");
            //chessPiece.setVisible(true);
            lastParent.validate();
            lastParent.repaint();
            return;
        }
        //c.setBackground(currentColor);
        if (chessPiece == null) {
            return;
        }
        chessPiece.setVisible(false);
        if (c instanceof JLabel) {
            Container parent = c.getParent();
//remove the piece that is capture
            parent.remove(0);
            parent.add(chessPiece);
        } else {
            Container parent = (Container) c;
            parent.add(chessPiece);
        }
        chessPiece.setVisible(true);
        lastParent.repaint();
    }

    /**
     *
     * @param x
     * @param y
     * @param x0
     * @param y0
     */
     private String generateMoveCommand(int srcX, int srcY, int destX, int destY) {
        //System.out.print("\n********** GENERATING MOVE***********");
        if(this.chessPieceIcon == null){
            return "";
        }
        char chsPc =  parseIcon(this.chessPieceIcon.toString());
        //Refactor the coordinates
        srcX /= PER_SQUARE_SIZE;
        srcY /= PER_SQUARE_SIZE;
        destX /= PER_SQUARE_SIZE;
        destY /= PER_SQUARE_SIZE;
        //System.out.println(srcX +"," + srcY + " -- " + destX + "," + destY);
        return String.valueOf(chsPc) + srcY + srcX +"-" + chsPc + destY + destX;
    }

    /**
     *
     * @param chessPieceIcon
     * @return
     */
    private char parseIcon(String chessPieceIcon) {
        int srtIndex = chessPieceIcon.indexOf("_");
        chessPieceIcon = chessPieceIcon.substring(srtIndex+1);
        //System.out.println("ICON :" + chessPieceIcon);
        return chessPieceIcon.charAt(0);
    }



    public void mouseClicked(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Create & Show the GUI
     *
     */
    private static void createAndShowGUI() {
//Make sure we have nice window decorations
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new ChessBoardGUI();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//Display the window
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
//Schedule a job for the event-dispatching thread:
//creating and showing this application's GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}