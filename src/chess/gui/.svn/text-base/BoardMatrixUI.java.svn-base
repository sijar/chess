/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.gui;

import chess.board.Board;
import chess.board.ChessSquare;
import chess.constants.IConstants;
import chess.game.GameMaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Sijar.Ahmed
 */
public class BoardMatrixUI extends JFrame implements MouseListener, MouseMotionListener {

    ////// Constants /////
    public static final long serialVersionUID = -21323344;
    private static final int BOARD_UI_SIZE = 400;
    static final int PER_SQUARE_SIZE = BOARD_UI_SIZE / 8; //total size/no_of_square
    ///// Fields /////
    private Dimension boardSize = new Dimension(BOARD_UI_SIZE, BOARD_UI_SIZE);
    private JLayeredPane layeredPane;
    private JPanel jCBoard;
    private JPanel box;
    private JTextArea playersInformation = new JTextArea("DashBoard"); //component to display Player Information
    //Using Chess Engine API
    private Board board = Board.getInstance();
    private ChessSquare[][] squares;
    private JLabel chessPiece;
    private int xAdjustment;
    private int yAdjustment;
    private Container lastParent;
    private Icon chessPieceIcon;
    private JPanel playerDashBoard;
    // Position of Piece in UI
    private int prevX = -1, prevY = -1;
    // Game Master
    private final GameMaster gameMaster = new GameMaster();

    /**
     * Constructor
     *
     */
    BoardMatrixUI() {
        // Use a Layered Pane for this this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        //set size of the LayeredPane
        layeredPane.setPreferredSize(new Dimension((boardSize.width * 2), boardSize.height));
        //Register Listener
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
        //initialize JPanels
        jCBoard = new JPanel();
        playerDashBoard = new JPanel();
        //Add a chess jCBoard to the Layered Pane
        layeredPane.add(jCBoard, new Integer(0));
        layeredPane.add(playerDashBoard, new Integer(10));
        //Chess Board
        jCBoard.setLayout(new GridLayout(8, 8));
        jCBoard.setPreferredSize(boardSize);
        jCBoard.setBounds(0, 0, boardSize.width, boardSize.height);
        //player DashBoard
        playerDashBoard.setVisible(true);
        playerDashBoard.setLayout(new BorderLayout());
        playerDashBoard.setPreferredSize(boardSize);
        playerDashBoard.setBounds(boardSize.width + 5, 0, boardSize.width * 2, boardSize.height);
        playerDashBoard.setToolTipText("Player Information");
        //player information
        playersInformation.setVisible(true);
        playersInformation.setEditable(false);
        playersInformation.setForeground(Color.white);
        playersInformation.setBackground(Color.black);
        //clean the player Dash Board
        playerDashBoard.removeAll();
        //add Player Information to DashBoard
        playerDashBoard.add(playersInformation);
        paintBoardUI();
    }

    /**
     *
     * Method will the Board
     * It fill the alterate squares with black and white colors
     * and will populate the suitable squares with chess pieces
     *
     */
    final void paintBoardUI() {

        try {
            //clean the container
            jCBoard.removeAll();
            //fill the piece
            squares = board.getUnmodifiableChessSquares(); //Obtain an Unmodified Square
            for (ChessSquare[] chessSquares : squares) {
                for (ChessSquare square : chessSquares) {
                    box = new JPanel(new BorderLayout());
                    Color color = Color.white;
                    if (square.getColor() == ChessSquare.BoardColor.BLACK.ordinal()) {
                        color = Color.black;
                    }
                    box.setBackground(color);
                    if (square.getPiece() != null) {
                        String fileName = null;
                        //display blocked/unblocked pieces
                        ////System.out.print("\n%% Image Path informatoin :"  + IConstants.IMAGE_FOLDER);
                        if (square.getPiece().getColor() != gameMaster.getTurnPlayerColor()) {                            
                            fileName = IConstants.IMAGE_FOLDER + square.getPiece().getImageName() + ".png";
                        } else {
                            fileName = IConstants.IMAGE_FOLDER + square.getPiece().getImageName() + "_blur.png";
                        }
                        //System.err.println(square.getPiece().getColor());
                        JLabel piecesImage = new JLabel(new ImageIcon(fileName));
                        ////System.out.print("\n%% Image Icon informatoin :"  + piecesImage.getName());
                        box.add(piecesImage);
                    }
                    jCBoard.add(box, BorderLayout.CENTER);
                }
            }
            //update the information
            playersInformation.setForeground(Color.white);
            playersInformation.setText(gameMaster.getPlayersInformation());            
        } catch (IllegalArgumentException excp) {
            //update the information
            playersInformation.setForeground(Color.yellow);
            playersInformation.setText(excp.getMessage() + "\n" + gameMaster.getPlayersInformation());
        } catch (Exception excp) {
            //update the information
            playersInformation.setForeground(Color.yellow);
            playersInformation.setText(excp.getMessage() + "\n" + gameMaster.getPlayersInformation());
        }
    }

    /**
     *
     * @param currX
     * @param currY
     * @param x0
     * @param y0
     */
    private String generateMoveCommand(int srcX, int srcY, int destX, int destY) {
        //System.out.print("\n********** GENERATING MOVE***********");
        if (this.chessPieceIcon == null) {
            return "";
        }
        char chsPc = parseIcon(this.chessPieceIcon.toString());
        //Refactor the coordinates
        srcX /= PER_SQUARE_SIZE;
        srcY /= PER_SQUARE_SIZE;
        destX /= PER_SQUARE_SIZE;
        destY /= PER_SQUARE_SIZE;
        //System.out.println(srcX +"," + srcY + " -- " + destX + "," + destY);
        return String.valueOf(chsPc) + srcY + srcX + "-" + chsPc + destY + destX;
    }

    /**
     *
     * @param chessPieceIcon
     * @return
     */
    private char parseIcon(String chessPieceIcon) {
        int srtIndex = chessPieceIcon.lastIndexOf("_");
        chessPieceIcon = chessPieceIcon.substring(srtIndex + 1);
        //System.out.println("ICON :" + chessPieceIcon);
        return chessPieceIcon.charAt(0);
    }

    /**
     * Create & Show the GUI
     *
     */
    public static void createAndShowGUI() {
        //Make sure we have nice window decorations
        JFrame.setDefaultLookAndFeelDecorated(true);
        BoardMatrixUI boardUI = new BoardMatrixUI();
        boardUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Display the window
        boardUI.pack();
        boardUI.setResizable(true);
        boardUI.setLocationRelativeTo(null);
        boardUI.setVisible(true);
    }


    /*
     *
     *
     * Add the selected chess piece to the dragging layer so it can be moved
     *
     */
    //@Override
    public void mousePressed(MouseEvent e) {
        try {
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
            //System.out.println("Clicked Piece's Coordinates :" + this.prevX + " " + this.prevY);
            //System.out.println("Clicked Chess Piece :" + chessPiece.getIcon());
            this.chessPieceIcon = chessPiece.getIcon();
        } catch (Exception excp) {            
            //update the information
            playersInformation.setForeground(Color.yellow);
            playersInformation.setText(excp.getMessage() + "\n" + gameMaster.getPlayersInformation());
        }

    }

    /*
     *
     * Move the chess piece around
     */
    //@Override
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
    //@Override
    public void mouseReleased(MouseEvent e) {

        int currX,currY;

        try {
            Component c = jCBoard.findComponentAt(e.getX(), e.getY());
            //container and parent information
            //System.out.println("%% Container Parent Coordinates :" + c.getParent().getX() + ", " + c.getParent().getY());            
            //System.out.println("%% Previous Location :" + e.getX() + " " + e.getY());
            if (c == null) {
                layeredPane.remove(chessPiece);
                lastParent.add(chessPiece);
                Rectangle r = lastParent.getBounds();
                chessPiece.setLocation(r.x + xAdjustment, r.y + yAdjustment);
                lastParent.validate();
                lastParent.repaint();
                return;
            }            
            //check whether colliding or not
            if ((c.getParent().getX() == 0) && (c.getParent().getY() == 0)) {
                currX = (int) c.getBounds().getX();
                currY = (int) c.getBounds().getY();
            } else{
                currX = c.getParent().getX();
                currY = c.getParent().getY();
            }
            System.out.println("%% Final Altered Location :" + currX + " " + currY);
            /////////////////// VALIDATE THE MOVE ////////////////////
            if (!gameMaster.move(generateMoveCommand(this.prevX, this.prevY, currX, currY))) {
                System.out.print("\n******Incorrect MOVE :");
            } else {
                paintBoardUI();
            }
            /** <!-- DO NOT DELETE THIS LINE --> **/
            jCBoard.revalidate();
            return;
        } catch (Exception excp) {
            //update the information
            playersInformation.setForeground(Color.yellow);
            playersInformation.setText(excp.getMessage() + "\n" + gameMaster.getPlayersInformation());
            /** <!-- DO NOT DELETE THIS LINE --> **/
            jCBoard.revalidate();
            return;
        }
    }

    //@Override
    public void mouseClicked(MouseEvent e) {
        //do nothing
    }

    //@Override
    public void mouseEntered(MouseEvent e) {
        //do nothing
    }

    //@Override
    public void mouseExited(MouseEvent e) {
        //do nothing
    }

    //@Override
    public void mouseMoved(MouseEvent e) {
        //do nothing
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}//Close

