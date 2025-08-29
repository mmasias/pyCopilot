package solitaire;

import java.util.*;

/**
 * Console interface for Klondike Solitaire
 */
public class ConsoleInterface {
    private Game game;
    private Scanner scanner;
    
    public ConsoleInterface() {
        game = new Game();
        scanner = new Scanner(System.in);
    }
    
    public void startGame() {
        System.out.println("=== KLONDIKE SOLITAIRE ===");
        System.out.println("Bienvenido al Solitario Klondike!");
        System.out.println();
        
        while (!game.isGameWon()) {
            displayGame();
            showHelp();
            processUserInput();
        }
        
        System.out.println("¡FELICITACIONES! ¡Has ganado el juego!");
        System.out.println("¿Quieres jugar otra vez? (s/n): ");
        
        String response = scanner.nextLine().toLowerCase();
        if (response.startsWith("s")) {
            game.restart();
            startGame();
        }
    }
    
    private void displayGame() {
        System.out.println("\n" + "=".repeat(60));
        
        // Display foundation piles
        System.out.println("FUNDACIONES:");
        System.out.print("  ");
        for (int i = 0; i < 4; i++) {
            Card card = game.getFoundation().getTopCard(i);
            if (card == null) {
                System.out.print(String.format("F%d:[  ] ", i + 1));
            } else {
                System.out.print(String.format("F%d:%s ", i + 1, card));
            }
        }
        System.out.println();
        System.out.println();
        
        // Display stock and waste
        System.out.println("STOCK Y DESCARTE:");
        Card wasteCard = game.getStockWaste().getWasteTop();
        System.out.printf("  Stock:[%d cartas] Descarte:%s\\n", 
                         game.getStockWaste().getStockSize(),
                         wasteCard == null ? "[  ]" : wasteCard.toString());
        System.out.println();
        
        // Display tableau
        System.out.println("TABLEAU:");
        System.out.println("   1     2     3     4     5     6     7");
        
        int maxHeight = 0;
        for (int i = 0; i < 7; i++) {
            maxHeight = Math.max(maxHeight, game.getTableau().getColumnSize(i));
        }
        
        for (int row = 0; row < maxHeight; row++) {
            System.out.print(" ");
            for (int col = 0; col < 7; col++) {
                List<Card> column = game.getTableau().getColumn(col);
                if (row < column.size()) {
                    System.out.print(column.get(row) + " ");
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    private void showHelp() {
        System.out.println("COMANDOS:");
        System.out.println("  d - Sacar carta del stock");
        System.out.println("  tf [columna] - Mover carta del tableau a fundación");
        System.out.println("  wf - Mover carta del descarte a fundación");
        System.out.println("  wt [columna] - Mover carta del descarte al tableau");
        System.out.println("  tt [desde] [posición] [hasta] - Mover cartas entre columnas del tableau");
        System.out.println("  ft [fundación] [columna] - Mover carta de fundación al tableau");
        System.out.println("  auto - Auto-mover cartas a fundaciones");
        System.out.println("  help - Mostrar esta ayuda");
        System.out.println("  quit - Salir del juego");
        System.out.print("Comando: ");
    }
    
    private void processUserInput() {
        String input = scanner.nextLine().trim().toLowerCase();
        String[] parts = input.split("\\s+");
        
        try {
            switch (parts[0]) {
                case "d":
                    if (game.drawCard()) {
                        System.out.println("Carta sacada del stock.");
                    } else {
                        System.out.println("No se puede sacar más cartas.");
                    }
                    break;
                    
                case "tf":
                    if (parts.length < 2) {
                        System.out.println("Uso: tf [columna]");
                        break;
                    }
                    int col = Integer.parseInt(parts[1]) - 1;
                    if (game.moveTableauToFoundation(col)) {
                        System.out.println("Carta movida a fundación.");
                    } else {
                        System.out.println("No se puede mover la carta a fundación.");
                    }
                    break;
                    
                case "wf":
                    if (game.moveWasteToFoundation()) {
                        System.out.println("Carta del descarte movida a fundación.");
                    } else {
                        System.out.println("No se puede mover la carta del descarte a fundación.");
                    }
                    break;
                    
                case "wt":
                    if (parts.length < 2) {
                        System.out.println("Uso: wt [columna]");
                        break;
                    }
                    int toCol = Integer.parseInt(parts[1]) - 1;
                    if (game.moveWasteToTableau(toCol)) {
                        System.out.println("Carta del descarte movida al tableau.");
                    } else {
                        System.out.println("No se puede mover la carta del descarte al tableau.");
                    }
                    break;
                    
                case "tt":
                    if (parts.length < 4) {
                        System.out.println("Uso: tt [desde] [posición] [hasta]");
                        break;
                    }
                    int fromCol = Integer.parseInt(parts[1]) - 1;
                    int pos = Integer.parseInt(parts[2]) - 1;
                    int toColTT = Integer.parseInt(parts[3]) - 1;
                    if (game.moveTableauToTableau(fromCol, pos, toColTT)) {
                        System.out.println("Cartas movidas entre columnas.");
                    } else {
                        System.out.println("No se pueden mover las cartas.");
                    }
                    break;
                    
                case "ft":
                    if (parts.length < 3) {
                        System.out.println("Uso: ft [fundación] [columna]");
                        break;
                    }
                    int foundPile = Integer.parseInt(parts[1]) - 1;
                    int toColFT = Integer.parseInt(parts[2]) - 1;
                    if (game.moveFoundationToTableau(foundPile, toColFT)) {
                        System.out.println("Carta movida de fundación al tableau.");
                    } else {
                        System.out.println("No se puede mover la carta de fundación al tableau.");
                    }
                    break;
                    
                case "auto":
                    if (game.autoMoveToFoundation()) {
                        System.out.println("Auto-movimiento completado.");
                    } else {
                        System.out.println("No hay movimientos automáticos disponibles.");
                    }
                    break;
                    
                case "help":
                    showHelp();
                    break;
                    
                case "quit":
                    System.out.println("¡Gracias por jugar!");
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Comando no reconocido. Escribe 'help' para ver los comandos.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Número inválido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}