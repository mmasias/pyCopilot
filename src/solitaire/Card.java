package solitaire;

/**
 * Represents a playing card with suit, rank, and visibility state
 */
public class Card {
    public enum Suit {
        HEARTS("♥", "red"), DIAMONDS("♦", "red"), 
        CLUBS("♣", "black"), SPADES("♠", "black");
        
        private final String symbol;
        private final String color;
        
        Suit(String symbol, String color) {
            this.symbol = symbol;
            this.color = color;
        }
        
        public String getSymbol() { return symbol; }
        public String getColor() { return color; }
    }
    
    public enum Rank {
        ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"),
        SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"),
        JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");
        
        private final int value;
        private final String symbol;
        
        Rank(int value, String symbol) {
            this.value = value;
            this.symbol = symbol;
        }
        
        public int getValue() { return value; }
        public String getSymbol() { return symbol; }
    }
    
    private final Suit suit;
    private final Rank rank;
    private boolean faceUp;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.faceUp = false;
    }
    
    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }
    public boolean isFaceUp() { return faceUp; }
    public void setFaceUp(boolean faceUp) { this.faceUp = faceUp; }
    public void flip() { this.faceUp = !this.faceUp; }
    
    public boolean isRed() { return suit.getColor().equals("red"); }
    public boolean isBlack() { return suit.getColor().equals("black"); }
    
    public boolean canPlaceOnTableau(Card other) {
        if (other == null) return rank == Rank.KING;
        return this.rank.getValue() == other.rank.getValue() - 1 && 
               this.isRed() != other.isRed();
    }
    
    public boolean canPlaceOnFoundation(Card other) {
        if (other == null) return rank == Rank.ACE;
        return this.suit == other.suit && 
               this.rank.getValue() == other.rank.getValue() + 1;
    }
    
    @Override
    public String toString() {
        if (!faceUp) return "[??]";
        return "[" + rank.getSymbol() + suit.getSymbol() + "]";
    }
}