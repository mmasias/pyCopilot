package solitaire;

import java.util.*;

/**
 * Represents a deck of playing cards with shuffling and dealing capabilities
 */
public class Deck {
    private List<Card> cards;
    
    public Deck() {
        initializeDeck();
        shuffle();
    }
    
    private void initializeDeck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    public Card deal() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot deal from empty deck");
        }
        return cards.remove(cards.size() - 1);
    }
    
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    public int size() {
        return cards.size();
    }
}