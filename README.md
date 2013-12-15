AMaze
=====

Maze Android Application converted from Java Applet from Paul Falstad (with permission)
http://www.falstad.com/maze/

Project that I worked on with Benjamin Weaver (github.com/bweavgolfanatic)  during the Fall of 2012 for Software Development class at the College of William and Mary.

We were given existing code for a Maze Java Applet and throughout the semester added on to it.

Things that we added:

-Adding a new maze algorithm to Falstad's maze program in a test-driven design manner

-Added user interface using JOptionPane with Buttons allowing user to manipulate maze before creating and move throughout the maze.

-Establish a robot platform to operate on the maze

-Add different algorithms to drive a robot out of the maze, with battery life and moves made

    -Gambler: the gambler is a simple random search algorithm with no memory. Once started it looks moves a step, checks its options by asking its available distance sensors, randomly picks a direction, moves a stop and so on. 
  
    -CuriousGambler: the curious gambler has a bias to select an adjacent cell that was not visited as often as other (curious to explore new territories). So it needs a memory to count how often it has visited particular cells. Hint: a hashmap is a great data structure for this, but there are lots of choices that all work (check for Collections). 
  
    -WallFollower: the wall follower is a classic solution technique. The robot needs a distance sensor at the front and at one side (here: pick left) to perform. It follows the wall on its left hand side.  Warning: Think how the robot's limitations in recognizing its environment may have an impact on properties of this classic solution algorithm (termination, correctness).
  
    -Wizard: as there is little magic in algorithms, the wizard is in fact a cheater who knows the distance matrix and uses it to find the exit. The wizard is intended to work as a baseline algorithm to see who the most efficient algorithm can perform in terms of energy consumption and path length. It is also a good candidate for testing a maze, a robot implementation...

-Added an Android user interface

-Ported the code over to Android including manual graphics and robot graphics



