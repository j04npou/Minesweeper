# Buscaminas
Esta es una implementación del juego de Buscaminas en Java. El juego se juega en la terminal, y el objetivo es descubrir todas las celdas del tablero sin tocar ninguna mina.

## Cómo Jugar
- Si quieres jugar sin tener que compilar el código, puedes descargar el archivo JAR del juego de la última release desde el siguiente enlace: [Descargar Buscaminas JAR](https://github.com/j04npou/Minesweeper/releases/tag/1.1)

- Para jugar, simplemente ejecuta en tu terminal el archivo JAR descargado:
`java -jar minesweeper-1.1.jar`

-  Se mostrará el menú del juego, donde puedes elegir el nivel de dificultad o personalizar tu propio juego.
    
-  Los niveles de dificultad son los siguientes:
    
    -   Fácil: tablero de 10x8 con 10 minas
    -   Medio: tablero de 19x14 con 40 minas
    -   Difícil: tablero de 24x20 con 99 minas
    -   Personalizado
    
-  El juego comenzará, y verás el tablero del juego representado con caracteres y emojis. Las celdas se mostrarán como cubiertas hasta que las descubras.
    
-  Para hacer un movimiento, ingresa una coordenada en el formato `[Letra][Número]` (por ejemplo, `A7`) para descubrir una celda. Para marcar una celda con una bandera, utiliza el formato `.[Letra][Número]` (por ejemplo, `.B3`).
    
- El juego termina cuando descubres todas las celdas que no contienen minas o si tocas una mina.
    
-  Si ganas el juego, se mostrará el tiempo que tardaste en completarlo.
