package solitaire;

import java.util.*;

/**
 * Represents the tableau (main playing area) with 7 columns
 */
public class Tableau {
    private List<List<Card>> columns;
    
    public Tableau() {
        columns = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            columns.add(new ArrayList<>());
        }
    }
    
    public void dealInitialCards(Deck deck) {
        // Deal cards to tableau: 1 card to first column, 2 to second, etc.
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row <= col; row++) {
                Card card = deck.deal();
                if (row == col) {
                    card.setFaceUp(true); // Top card is face up
                }
                columns.get(col).add(card);
            }
        }
    }
    
    public boolean canAddCard(int column, Card card) {
        if (column < 0 || column >= 7) return false;
        
        List<Card> col = columns.get(column);
        if (col.isEmpty()) {
            return card.getRank() == Card.Rank.KING;
        }
        
        Card topCard = col.get(col.size() - 1);
        return card.canPlaceOnTableau(topCard);
    }
    
    public void addCard(int column, Card card) {
        if (!canAddCard(column, card)) {
            throw new IllegalArgumentException("Cannot add card to column " + column);
        }
        card.setFaceUp(true);
        columns.get(column).add(card);
    }
    
    public void addCards(int column, List<Card> cards) {
        for (Card card : cards) {
            addCard(column, card);
        }
    }
    
    public Card removeTopCard(int column) {
        if (column < 0 || column >= 7) return null;
        
        List<Card> col = columns.get(column);
        if (col.isEmpty()) return null;
        
        Card removedCard = col.remove(col.size() - 1);
        
        // Flip top card if it exists and is face down
        if (!col.isEmpty()) {
            Card newTop = col.get(col.size() - 1);
            if (!newTop.isFaceUp()) {
                newTop.setFaceUp(true);
            }
        }
        
        return removedCard;
    }
    
    public List<Card> removeCardsFromPosition(int column, int position) {
        if (column < 0 || column >= 7) return new ArrayList<>();
        
        List<Card> col = columns.get(column);
        if (position < 0 || position >= col.size()) return new ArrayList<>();
        
        List<Card> removedCards = new ArrayList<>();
        for (int i = col.size() - 1; i >= position; i--) {
            removedCards.add(0, col.remove(i));
        }
        
        // Flip top card if it exists and is face down
        if (!col.isEmpty()) {
            Card newTop = col.get(col.size() - 1);
            if (!newTop.isFaceUp()) {
                newTop.setFaceUp(true);
            }
        }
        
        return removedCards;
    }
    
    public Card getTopCard(int column) {
        if (column < 0 || column >= 7) return null;
        
        List<Card> col = columns.get(column);
        return col.isEmpty() ? null : col.get(col.size() - 1);
    }
    
    public List<Card> getColumn(int column) {
        if (column < 0 || column >= 7) return new ArrayList<>();
        return new ArrayList<>(columns.get(column));
    }
    
    public boolean isEmpty(int column) {
        if (column < 0 || column >= 7) return true;
        return columns.get(column).isEmpty();
    }
    
    public int getColumnCount() {
        return 7;
    }
    
    public int getColumnSize(int column) {
        if (column < 0 || column >= 7) return 0;
        return columns.get(column).size();
    }
}