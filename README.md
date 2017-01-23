# Drop

## Launch instructions
To compile:
`$ javac Woo.java`

To run: 
`$ java Woo`


## The Game
This is a remake of Bejeweled. The goal of the game is to swap two gems that are next to each other in order to form chains. A chain is defined by three or more consecutive gems that are the same color. The chains that are formed will be destroyed and replaced by the gems above them. The resulting voids will be filled in by new gems from the top row. 

There are also special gems that can destroy all gems of the same color, all the gems in a row or column, or the gems around the special gem. You get points based on the number of gems destroyed in each move.

## The Instructions
Press w, a, s, or d and then hit enter to move your cursor. Enter e to select/deselect the gem that the cursor is on. Select two gems that are next to each other to swap them.

Swaps are only allowed if they result in a chain. Moves are only counted if they result in a valid swap. There are a maximum of 10 moves before the game ends. 
