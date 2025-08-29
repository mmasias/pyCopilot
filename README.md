# pyCopilot - Klondike Solitaire

Un juego de Solitario Klondike implementado en Java consola.

## Descripción

Este proyecto implementa el clásico juego de cartas Solitario Klondike con una interfaz de consola. El juego incluye todas las características estándar del Klondike:

- Tableau con 7 columnas
- 4 pilas de fundación (una por palo)
- Pila de stock y descarte
- Reglas completas de Klondike
- Interfaz en español

## Requisitos

- Java 8 o superior
- Terminal/consola que soporte caracteres Unicode (para los símbolos de las cartas)

## Cómo ejecutar

### Opción 1: Usar el script de ejecución (recomendado)
```bash
./run.sh
```

### Opción 2: Compilación manual
```bash
# Compilar
mkdir -p build
javac -d build src/solitaire/*.java

# Ejecutar
java -cp build solitaire.Main
```

## Cómo jugar

El objetivo del juego es mover todas las cartas a las pilas de fundación, ordenadas por palo desde As hasta Rey.

### Comandos disponibles:

- `d` - Sacar carta del stock
- `tf [columna]` - Mover carta del tableau a fundación (columnas 1-7)
- `wf` - Mover carta del descarte a fundación
- `wt [columna]` - Mover carta del descarte al tableau
- `tt [desde] [posición] [hasta]` - Mover cartas entre columnas del tableau
- `ft [fundación] [columna]` - Mover carta de fundación al tableau (fundaciones 1-4)
- `auto` - Auto-mover cartas a fundaciones cuando sea posible
- `help` - Mostrar ayuda
- `quit` - Salir del juego

### Reglas:

1. **Fundaciones**: Se construyen desde As hasta Rey, por palo
2. **Tableau**: Se construyen en orden descendente, alternando colores
3. **Stock**: Se pueden sacar cartas de una en una
4. **Movimientos válidos**: Solo cartas boca arriba se pueden mover

### Ejemplo de juego:

```
FUNDACIONES:
  F1:[A♦] F2:[  ] F3:[  ] F4:[  ] 

STOCK Y DESCARTE:
  Stock:[23 cartas] Descarte:[5♥]

TABLEAU:
   1     2     3     4     5     6     7
      [??] [??] [??] [??] [??] [??] 
      [Q♦] [??] [??] [??] [??] [??] 
           [3♦] [??] [??] [??] [??] 
                [7♣] [??] [??] [??] 
                     [K♥] [??] [??] 
                          [6♠] [??] 
                               [K♦] 
```

¡Disfruta del juego!