#!/bin/bash
# Script para compilar y ejecutar el juego de Solitario Klondike

echo "Compilando Klondike Solitaire..."
mkdir -p build
javac -d build src/solitaire/*.java

if [ $? -eq 0 ]; then
    echo "Compilación exitosa. Iniciando juego..."
    echo
    java -cp build solitaire.Main
else
    echo "Error de compilación."
    exit 1
fi