package solitaire;

import java.util.*;

/**
 * Main game logic for Klondike Solitaire
 */
public class Game {
    private Deck deck;
    private Tableau tableau;
    private Foundation foundation;
    private StockWaste stockWaste;
    private boolean gameWon;
    
    public Game() {
        initializeGame();
    }
    
    private void initializeGame() {
        deck = new Deck();
        tableau = new Tableau();
        foundation = new Foundation();
        stockWaste = new StockWaste();
        gameWon = false;
        
        // Deal initial tableau
        tableau.dealInitialCards(deck);
        
        // Remaining cards go to stock
        while (!deck.isEmpty()) {
            stockWaste.addToStock(deck.deal());
        }
    }
    
    public void restart() {
        initializeGame();
    }
    
    // Move card from tableau to foundation
    public boolean moveTableauToFoundation(int tableauColumn) {
        Card card = tableau.getTopCard(tableauColumn);
        if (card == null || !card.isFaceUp()) return false;
        
        int foundationPile = foundation.findPileForCard(card);
        if (foundationPile == -1) return false;
        
        tableau.removeTopCard(tableauColumn);
        foundation.addCard(foundationPile, card);
        checkWinCondition();
        return true;
    }
    
    // Move card from waste to foundation
    public boolean moveWasteToFoundation() {
        Card card = stockWaste.getWasteTop();
        if (card == null) return false;
        
        int foundationPile = foundation.findPileForCard(card);
        if (foundationPile == -1) return false;
        
        stockWaste.removeWasteTop();
        foundation.addCard(foundationPile, card);
        checkWinCondition();
        return true;
    }
    
    // Move card from waste to tableau
    public boolean moveWasteToTableau(int tableauColumn) {
        Card card = stockWaste.getWasteTop();
        if (card == null) return false;
        
        if (!tableau.canAddCard(tableauColumn, card)) return false;
        
        stockWaste.removeWasteTop();
        tableau.addCard(tableauColumn, card);
        return true;
    }
    
    // Move cards within tableau
    public boolean moveTableauToTableau(int fromColumn, int cardPosition, int toColumn) {
        if (fromColumn == toColumn) return false;
        
        List<Card> cardsToMove = tableau.getColumn(fromColumn);
        if (cardPosition < 0 || cardPosition >= cardsToMove.size()) return false;
        
        // Check if all cards from position are face up and in sequence
        Card firstCard = cardsToMove.get(cardPosition);
        if (!firstCard.isFaceUp()) return false;
        
        for (int i = cardPosition; i < cardsToMove.size() - 1; i++) {
            Card current = cardsToMove.get(i);
            Card next = cardsToMove.get(i + 1);
            if (!next.canPlaceOnTableau(current)) return false;
        }
        
        // Check if we can place the sequence on target column
        if (!tableau.canAddCard(toColumn, firstCard)) return false;
        
        // Move the cards
        List<Card> removedCards = tableau.removeCardsFromPosition(fromColumn, cardPosition);
        tableau.addCards(toColumn, removedCards);
        return true;
    }
    
    // Move card from foundation to tableau
    public boolean moveFoundationToTableau(int foundationPile, int tableauColumn) {
        Card card = foundation.getTopCard(foundationPile);
        if (card == null) return false;
        
        if (!tableau.canAddCard(tableauColumn, card)) return false;
        
        foundation.removeTopCard(foundationPile);
        tableau.addCard(tableauColumn, card);
        return true;
    }
    
    // Draw card from stock to waste
    public boolean drawCard() {
        if (stockWaste.canDraw()) {
            stockWaste.draw();
            return true;
        } else if (!stockWaste.isWasteEmpty()) {
            stockWaste.recycleWaste();
            return true;
        }
        return false;
    }
    
    private void checkWinCondition() {
        gameWon = foundation.isComplete();
    }
    
    // Auto-move cards to foundation if possible
    public boolean autoMoveToFoundation() {
        boolean moved = false;
        
        // Try to move from tableau
        for (int i = 0; i < tableau.getColumnCount(); i++) {
            if (moveTableauToFoundation(i)) {
                moved = true;
            }
        }
        
        // Try to move from waste
        if (moveWasteToFoundation()) {
            moved = true;
        }
        
        return moved;
    }
    
    public Tableau getTableau() { return tableau; }
    public Foundation getFoundation() { return foundation; }
    public StockWaste getStockWaste() { return stockWaste; }
    public boolean isGameWon() { return gameWon; }
}