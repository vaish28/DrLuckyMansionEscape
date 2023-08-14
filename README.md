# CS 5010 Semester Project

This repo represents the coursework for CS 5010, the Fall 2022 Edition!

**Name:** Vaishnavi Sunil Madhekar

**Email:** madhekar.v@northeastern.edu

**Preferred Name:** Vaishnavi



### About/Overview

- The problem involves creating a program to simulate the game called "Kill Doctor Lucky".
- This game is loosely inspired by the Doctor Lucky series of games by Cheapass Games.
- This program uses the MVC architecture to develop the game.
- The program solves the problem by implementing a modular and extensible design with an
  interactive UI.
    - The general overview of the solution is as follows:
    - World interface:- defines a template contract for a game world and contains the methods for
      retrieving the information about the world.
    - DrLuckyWorld class:- implements the world interface and represents the game world. It stores
      the information about the world's dimensions, total number of spaces and items, details
      about the target character. It provides methods for accessing this information using the
      getter functions.
    - The Space interface:- defines the contract for a space in the game world. It includes methods
      for retrieving the space's positions, name, neighbors and items in that space.
    - DrLuckySpaces class:- implements the Space interface and represents a space in the game world.
      It stores information about the space's positions,name, neighbors and items in that space.
    - Item interface:- defines the contract for an item in the game world. It includes methods for
      retrieving the item's room index, damage value and name.
    - DrLuckyItems class:- implements the Item interface and represents a weapon in the game
      world. It stores information about the  item's damage value and name.
    - The DrLuckyWorldDriver class:- serves as the entry point of the program. It takes a file path
      as a command-line argument and passes it to the ReadInput class which is a inner class within the DrLuckyWorld 
      class. This helps in parse the file or string input, in short, any readable object. It then creates an instance of
      DrLuckyWorld and  interacts with it to perform various operations on the game world. 
    - A controller class has been added, it provides the following functionality:
      - The GameController class is responsible for coordinating the interactions between the players,
        game world, and user interface.
      - It manages the turn-based gameplay, where each player takes their turn sequentially.
      - The controller initializes the game by setting the UI to visibility true.
      - It listens to all potential key presses and responds accordingly.
      - During each turn, the controller prompts the active player for their action choice and
        executes the corresponding action.
      - The controller updates the game world state, including player positions and item collections,
        based on the actions performed by the players and asks the view to update the UI
   - It separates the concerns of reading the world specification file, parsing its contents, and
     managing the game world into distinct classes and interfaces, promoting code organization
     and maintainability.
   - The view has readonlymodel access and controller has access of all the methods as it has the object of world 
     interface.


### List of Features


1. Reading and parsing the world specification file:
    - The program reads the contents of the file and parses it to extract the necessary
      information.
    - The information is about the game world, such as its dimensions, target character, spaces,
      and items.

2. Creating the game world:
    - The program creates an instance of the DrLuckyWorld.
    - It initialises the properties such as rows, columns, target character, spaces and items in
      the world.

3. Accessing the world information:
    - The program provided information to retrieve the information about the game world, such as
      its dimensions, target character, available spaces, and weapons.




### How to Run

1. Clone or download Git Repository.
2. Go into the res directory.
3. The res directory has a jar file name "cs5010project-vaish28".
4. To run the jar file open the command prompt in the folder where the jar file is located.
5. The res directory also contains sample input files on which you can test the program.
6. To run the jar file use the command below:
   ```java -jar cs5010project-vaish28.jar mansion.txt 10```
   The command specifies the path to the mansion.txt file, which is the sample input file. It is
   followed by a number which indicates the maximum turns for the world. In the project, both the
   jar file and the mansion.txt file are currently in the res folder.

### How to Use the Program


To use the "Dr. Lucky World Game" program, follow these instructions to interact with its functionality:

1. Launching the Game:
    - Run the program, and the game window will appear. 
    - The game's main interface will display the game world, player information, and buttons for various actions.

2. Adding a Human Player:
   - Click the "Enter human player info" button.
   - A dialog will pop up, prompting you to enter the human player's information:
     - Enter the player's name. 
     - Enter the maximum capacity for carrying items (a number). 
     - Enter the name of the room you want the player to start in.
   - Click "OK" to add the human player to the game.

3. Adding a Computer Player
   - Click the "Enter computer player info" button.
   - A computer-controlled player will be added to the game automatically.

4. Viewing Space Information:
   - Click the "View Space Information" button.
   - A dialog will prompt you to enter the name of a room. 
   - Enter the name of a room you want to get information about and click "OK."
   - Information about the selected room will be displayed.

5. Player Movement:
   - Click on a room/space in the grid to move the current player (human) to that room. 
   - The player must be human-controlled to move.

6. Looking Around:
  - Press the "L" key to trigger the "look around" action.
  - A dialog will display information about the current room, including neighboring rooms.
  
7. Picking Up Items:
   - Press the "P" key to pick up an item in the current room. 
   - A dialog will prompt you to select an item from the available items in the room. 
   - Select an item from the list to pick it up.
   
8. Attacking the Target Character:
   - Press the "A" key to attempt an attack on the target character's life.
   - A confirmation dialog will appear.
   - Choose an item (or action) to use for the attack from the options presented.
   - The result of the attack will be displayed in a dialog.
   
9. Player Description:
   - Click on a human player in the grid to view their description. 
   - A dialog will display information about the selected human player.
   
10. New Game:
    - Click on the "Game" menu in the top menu bar. 
    - Choose "New Game (New World)" to start a new game with a new world. 
    - Follow the prompts to select a world file and specify the maximum number of turns.
    
11. Game Over:
    - The game will end when either the target character's health reaches zero (victory) or the maximum number of turns is reached without defeating the target character (defeat).
    - A dialog will display the game outcome, either indicating victory or defeat.
    
12. Menus and About Dialog:
    - Use the menus in the top menu bar to access options such as starting a new game and quitting the game.
    - The "About" option provides information about the game.

13. Keyboard Shortcuts:
    - The keyboard shortcuts "P," "L," and "A" can be used to perform pick-up, look around, and attack actions, respectively.

### Example Runs


The example run for the Milestone 4 is the video demo which will demo the working of the game
with four players in total. 


There are some important example run files present in the res directory for Milestone 3:
1. EffectOfPet
2. playerwinningthegame
3. TargetCharacterRunningForLife
4. ComputerplayerWinningTheGame

The first example run shows the effect of pet on the spaces. The pet situated in a space makes it invisible.
Therefore, it makes the Lilac Room invisible.

The second example run captures the behaviour of human player making an attempt on the target character's life and human
player winning the game by killing the target character.

The third example captures the behaviour of target character escaping with his life and the game ending.

The example run four captures the behaviour of computer controlled player winning the game by killing the target
character and thus making an attempt on the target character life/health.

Rest of the example runs are sample runs demonstrating the game play.


### Design/Model Changes

- During Milestone 1, only the model was created and was used to find neighboring spaces adding
  spaces, displaying graphical representation and reading input.
- During Milestone 2, a synchronous controller was added and  model changes were made to add a human player and
  computer player. The allowed actions in Milestone 2 were to pick up an item, move a player to space, add a
  computer player and a human player, displaying space and player information, lookaround the world and
  displaying graphical representation.
- During Milestone 3, a class was added for the pet of the target character, classes for the command design pattern were 
  also added. 
  - The important changes made to the design are as follows:
    - The players are not associated with spaces and vice versa.
    - The players do not maintain the current space to track the location in order to maintain the SRP.
    - The mapping between players and spaces are moved to hashmap.
    - The items are not associated with the world but with spaces
    - Each space and player maintains it's own list of items.
    - The DrLuckyWorldReadInput is made an inner class of the DrLuckyWorld class.
- For Milestone 4, the following model changes were done:-
  - The computer action was moved from the model to the controller.
  - The controller is now responsible for controlling the players.
  - A new function in the model class is added for loading the world specification from a file.
  - The view has been added and it supports all the functionalities of previous milestones.

### Assumptions

1. The world is initially created with all the necessary spaces, and all the items are available
   at the beginning in a readable format.

2. The human player is included in the game before the computer player.

### Limitations

1. The generation of rooms and items is not dynamically handled, meaning new rooms and items
   cannot be created during gameplay.

2. The gameplay requires the human player to be added to the gameplay before a computer player.

### Citations

1. Tutorialspoint. (n.d.). Java - Buffered Image.
   Retrieved from https://www.tutorialspoint.com/java_dip/java_buffered_image.htm [02/06/2023]

2. GeeksforGeeks. "Collections.nCopies() in Java."
   Available: https://www.geeksforgeeks.org/collections-ncopies-java/. [30/05/2023]

3. GeeksforGeeks. "Java Program to Remove Nulls from a List/Container."
   Available: https://www.geeksforgeeks.org/java-program-to-remove-nulls-from-a-list-container/.
   [31/05/2023].

4. Javatpoint. "Java static nested classes."
   Available: https://www.javatpoint.com/static-nested-class/ .
   [23/06/2023]

5. Tutorialspoint. "SWING Tutorial."
   Available: https://www.tutorialspoint.com/swing/index.htm .
   [01/08/2023] [02/08/2023] [31/07/2023]


