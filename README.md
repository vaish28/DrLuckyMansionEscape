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

List any example runs that you have in res/ directory and provide a description of what each example represents or does. Make sure that your example runs are provided as *plain text files*.



### Design/Model Changes

Document what changes you have made from earlier designs. Why did you make those changes? Keep an on-going list using some form of versioning so it is clear when these changes occurred.



### Assumptions

List any assumptions that you made during program development and implementations. Be sure that these do not conflict with the requirements of the project.



### Limitations

What limitations exist in your program. This should include any requirements that were *not* implemented or were not working correctly (including something that might work some of the time).



### Citations

Be sure to cite your sources. A good guideline is if you take more than three lines of code from some source, you must include the information on where it came from. Citations should use proper [IEEE citation guidelines](https://ieee-dataport.org/sites/default/files/analysis/27/IEEE Citation Guidelines.pdf) and should include references (websites, papers, books, or other) for ***any site that you used to research a solution***. For websites, this includes name of website, title of the article, the url, and the date of retrieval**.** Citations should also include a qualitative description of what you used, and what you changed/contributed.



