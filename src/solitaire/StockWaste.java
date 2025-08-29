package solitaire;

import java.util.*;

/**
 * Represents the stock pile (remaining deck) and waste pile (drawn cards)
 */
public class StockWaste {
    private Stack<Card> stock;
    private Stack<Card> waste;
    
    public StockWaste() {
        stock = new Stack<>();
        waste = new Stack<>();
    }
    
    public void addToStock(Card card) {
        card.setFaceUp(false);
        stock.push(card);
    }
    
    public void addAllToStock(List<Card> cards) {
        for (Card card : cards) {
            addToStock(card);
        }
    }
    
    public boolean canDraw() {
        return !stock.isEmpty();
    }
    
    public void draw() {
        if (!canDraw()) {
            throw new IllegalStateException("Cannot draw from empty stock");
        }
        
        Card card = stock.pop();
        card.setFaceUp(true);
        waste.push(card);
    }
    
    public Card getWasteTop() {
        return waste.isEmpty() ? null : waste.peek();
    }
    
    public Card removeWasteTop() {
        return waste.isEmpty() ? null : waste.pop();
    }
    
    public void recycleWaste() {
        while (!waste.isEmpty()) {
            Card card = waste.pop();
            card.setFaceUp(false);
            stock.push(card);
        }
    }
    
    public boolean isStockEmpty() {
        return stock.isEmpty();
    }
    
    public boolean isWasteEmpty() {
        return waste.isEmpty();
    }
    
    public int getStockSize() {
        return stock.size();
    }
    
    public int getWasteSize() {
        return waste.size();
    }
}