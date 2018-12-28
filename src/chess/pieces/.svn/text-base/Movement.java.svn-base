/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

/**
 *
 * @author sijar
 */
public class Movement {

    private CoOrdinate fromPosition;
    private CoOrdinate toPosition;
    private movementType typeOfMovement;

    /**
     * Enumeration to check the type of movement
     */
    public enum movementType {

        HORIZONTAL, VERTICAL, DIAGONAL, LSHAPED
    };


    /**
     * 
     */
    public Movement(){
        
    }

    /**
     *
     * @param fromPosition
     * @param toPosition
     */
    public Movement(CoOrdinate fromPosition, CoOrdinate toPosition) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.typeOfMovement = calculateMovementType(fromPosition,toPosition);
    }

    /**
     * 
     * @param moveCommand
     */
    public Movement(String moveCommand) {
        CoOrdinate[] coOrds = parseMoveCommand(moveCommand);
        if (coOrds.length == 2) {
            this.fromPosition = coOrds[0];
            this.toPosition = coOrds[1];
            this.typeOfMovement = calculateMovementType(fromPosition, toPosition);
        } else {
            throw new IllegalArgumentException(" Invalid move command " + moveCommand);
        }
    }

    public CoOrdinate getFromPosition() {
        return fromPosition;
    }

    private void setFromPosition(CoOrdinate fromPosition) {
        this.fromPosition = fromPosition;
    }

    public CoOrdinate getToPosition() {
        return toPosition;
    }

    private void setToPosition(CoOrdinate toPosition) {
        this.toPosition = toPosition;
    }

    public movementType getTypeOfMovement() {
        return typeOfMovement;
    }

    private void setTypeOfMovement(movementType typeOfMovement) {
        this.typeOfMovement = typeOfMovement;
    }

    /**
     * Method to update the coordinates
     *
     * @param fromPosition
     * @param toPosition
     */
    public void updateCordinates(CoOrdinate fromPosition, CoOrdinate toPosition){
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.typeOfMovement = calculateMovementType(fromPosition, toPosition);
    }
    

    @Override
    public String toString() {
        StringBuilder objectString = new StringBuilder();
        if(fromPosition != null){
            objectString.append(" Source:");
            objectString.append(fromPosition.toString());
        }
        if(toPosition != null){
            objectString.append(" Destination:");
            objectString.append(toPosition.toString());
        }
        if(typeOfMovement != null){
            objectString.append(" Movement:");
            objectString.append(typeOfMovement.name());
        }
        return objectString.toString();
    }


    /**
     * Parse move command
     *
     * @param moveCommand
     * @return
     */
    private CoOrdinate[] parseMoveCommand(String moveCommand) {
        CoOrdinate[] coOrdinate = new CoOrdinate[0];
        if (moveCommand.matches("[BKNPQR](\\d\\d)-[BKNPQR](\\d\\d)") && (moveCommand.charAt(0) == moveCommand.charAt(4))) {
            //System.out.println("%% move command format is correct :");
            AbstractChessPiece chessPiece = ChessFactory.ObtainPiece(moveCommand.charAt(0));
            coOrdinate = new CoOrdinate[2];
            coOrdinate[0] = new CoOrdinate(moveCommand.substring(1, 3)); //the numerical
            coOrdinate[1] = new CoOrdinate(moveCommand.substring(5, 7)); //the numerical
        }
        return coOrdinate;
    }

    /**
     * Method to calculate the movement type
     * whether is vertical, horizontal, diagonal or LShaped
     * @param fromPosition
     * @param toPosition
     * @return
     */
    private movementType calculateMovementType(CoOrdinate fromPosition, CoOrdinate toPosition) {
        if (fromPosition != null && toPosition != null) {
            if (isVerticalMovement(fromPosition, toPosition)) {
                return movementType.HORIZONTAL;
            } else if (isHorizontalMovement(fromPosition, toPosition)) {
                return movementType.VERTICAL;
            } else if (isDiagonalMovement(fromPosition, toPosition)) {
                return movementType.DIAGONAL;
            } else if (isLShapedMovement(fromPosition, toPosition)) {
                return movementType.LSHAPED;
            }
        }
        return null;
    }

    /**
     * 
     * @param fromPosition
     * @param toPosition
     * @return
     */
    private boolean isDiagonalMovement(CoOrdinate fromPosition, CoOrdinate toPosition) {
        if (Math.abs(fromPosition.getX() - toPosition.getX()) == Math.abs(fromPosition.getY() - toPosition.getY())) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param fromPosition
     * @param toPosition
     * @return
     */
    private boolean isHorizontalMovement(CoOrdinate fromPosition, CoOrdinate toPosition) {
        if (Math.abs(fromPosition.getX() - toPosition.getX()) > 0 && (fromPosition.getY() == toPosition.getY())) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param fromPosition
     * @param toPosition
     * @return
     */
    private boolean isLShapedMovement(CoOrdinate fromPosition, CoOrdinate toPosition) {
        if ( ((Math.abs(fromPosition.getX() - toPosition.getX()) == 1) && (Math.abs(fromPosition.getY() - toPosition.getY()) == 2)) || ((Math.abs(fromPosition.getY() - toPosition.getY()) == 1) && (Math.abs(fromPosition.getX() - toPosition.getX()) == 2))) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param fromPosition
     * @param toPosition
     * @return
     */
    private boolean isVerticalMovement(CoOrdinate fromPosition, CoOrdinate toPosition) {
        if (Math.abs(fromPosition.getY() - toPosition.getY()) > 0 && (fromPosition.getX() == toPosition.getX())) {
            return true;
        }
        return false;
    }
}
