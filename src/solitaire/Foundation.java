package solitaire;

import java.util.*;

/**
 * Represents the foundation piles where completed suits are built
 */
public class Foundation {
    private List<Stack<Card>> piles;
    
    public Foundation() {
        piles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            piles.add(new Stack<>());
        }
    }
    
    public boolean canAddCard(int pile, Card card) {
        if (pile < 0 || pile >= 4) return false;
        
        Stack<Card> foundationPile = piles.get(pile);
        if (foundationPile.isEmpty()) {
            return card.getRank() == Card.Rank.ACE;
        }
        
        Card topCard = foundationPile.peek();
        return card.canPlaceOnFoundation(topCard);
    }
    
    public void addCard(int pile, Card card) {
        if (!canAddCard(pile, card)) {
            throw new IllegalArgumentException("Cannot add card to foundation pile " + pile);
        }
        piles.get(pile).push(card);
    }
    
    public Card removeTopCard(int pile) {
        if (pile < 0 || pile >= 4) return null;
        
        Stack<Card> foundationPile = piles.get(pile);
        return foundationPile.isEmpty() ? null : foundationPile.pop();
    }
    
    public Card getTopCard(int pile) {
        if (pile < 0 || pile >= 4) return null;
        
        Stack<Card> foundationPile = piles.get(pile);
        return foundationPile.isEmpty() ? null : foundationPile.peek();
    }
    
    public boolean isEmpty(int pile) {
        if (pile < 0 || pile >= 4) return true;
        return piles.get(pile).isEmpty();
    }
    
    public int getPileCount() {
        return 4;
    }
    
    public int getPileSize(int pile) {
        if (pile < 0 || pile >= 4) return 0;
        return piles.get(pile).size();
    }
    
    public boolean isComplete() {
        for (Stack<Card> pile : piles) {
            if (pile.size() != 13) return false;
        }
        return true;
    }
    
    // Find the best foundation pile for a card
    public int findPileForCard(Card card) {
        for (int i = 0; i < 4; i++) {
            if (canAddCard(i, card)) {
                return i;
            }
        }
        return -1;
    }
}