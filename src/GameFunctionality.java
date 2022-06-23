import HUMANOID_FAMILY.Humanoid;
import HUMANOID_FAMILY.Zombie;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;


public class GameFunctionality implements KeyListener {


    //KEYBOARD LISTENER OVER-RIDE     FOR THE GUI
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("The key Typed was: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isActionKey())
            System.exit(0);
        System.out.println("The key Pressed was: " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("The key Released was: " + e.getKeyChar());
    }

    public Frame INIT_SCREEN() {
        //Setting the Frame and Labels
        Frame f = new Frame("Humans vs. Goblins");
        f.setLayout(new GridLayout(10, 10));
        f.setSize(500, 500);
        f.setVisible(true);

        //Creating and adding the key listener
        GameFunctionality k = this;
        f.addKeyListener(k);
        return f;
    }


    //CONSOLE APPLICATION
    public int[][] validateCharacterSelect(String choice) {

        // " 21,6 . 16,6 "
        String[] str_arr = choice.split("[.]");

        //INVALID ARGUMENTS
        if (str_arr.length < 2) {
            System.out.println("Please enter a valid option!\n");
            return null;
        }


        //STRIP SPACES
        for (int x = 0; x < str_arr.length; x++) {
            str_arr[x] = str_arr[x].strip();
        }


        try {
            //ASSIGN INPUT DATA
            String[] from_coordinate_string = str_arr[0].split(",");
            String[] to_coordinate_string = str_arr[1].split(",");

            int[] from = {Integer.parseInt(from_coordinate_string[1]), Integer.parseInt(from_coordinate_string[0])};
            int[] to = {Integer.parseInt(to_coordinate_string[1]), Integer.parseInt(to_coordinate_string[0])};

            return new int[][]{from, to};

        } catch (Exception e) {
            System.out.println("Please enter a valid option!\n");
            return null;
        }

    }


    public static List<Object> fight(Stack<String> messages, List<Object> enemy_mobs, Object selected_mob, int[] to, boolean player1) {
        List<Object> output = new ArrayList<>();
        Object winner = null;


        //FIND ENEMY_MOB WHO COLLIDES WITH SELECTED MOB
        //RUN RANDOM PROBABILITIES BASED ON THEIR STATISTICS TO DECIDE WHO WINS
        //WINNER KEEPS THE SPOT


        // FETCH OBJECT METHODS FROM TROOPS (current_mobs) LIST
        Object iteration_mob = null;
        HashMap<String, HashMap<String, Method>> interactive_functions = null;
        HashMap<String, Method> setters = null;
        HashMap<String, Method> getters = null;
        int[] current_coordinates = null;
        boolean found = false;


        //PARSE EVERY ENEMY MOB
        for (int i = 0; i < enemy_mobs.size(); i++) {
            //CALL SETTERS & GETTERS OF INTERACTED OBJECT
            iteration_mob = enemy_mobs.get(i);
            interactive_functions = Humanoid.setters_and_getters(iteration_mob);
            setters = interactive_functions.get("setters");
            getters = interactive_functions.get("getters");

            // FUNCTIONALITY ALGO
            try {
                // METHODS TO INVOKE UPON OTHER INTRACTABLE
                Method get_location = Humanoid.fetchMethod(getters, "getCoordinate()");

                // INVOKE GET PROCESS
                current_coordinates = (int[]) get_location.invoke(iteration_mob);

                //CHECK IF CURRENT COORDINATE == USER INPUT:TO
                if (current_coordinates[0] == to[0] && current_coordinates[1] == to[1]) {
                    found = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println(e);
                //NULL EXCEPTION IS WHEN OBJECT ISN'T INTRACTABLE
            }
        }


        // COORDINATE NOT FOUND
        if (!found) {
            messages.push(player1 ? "PLAYER ONE!!!" : "PLAYER TWO!!!" + "INVALID COORDINATE: Cannot attack mates!");
            output.add(messages);
            output.add(winner);
            return output;
        }


        //FIGHT decide winner
        //BOTH OBJECTS HAVE SAME INTERACTION FUNCTIONS
        System.out.println(iteration_mob);
        System.out.println(interactive_functions);
        //choose a random skill
        //find range from 0 --> random skill
        //check who's bigger

        //attack
        //strength
        //defence
        //combatLVL
        //intelligence
        //health
        //compassion
        //coordinates
        //inventory







        //MESSAGE BOX UPDATE (4 lines MAX ) & RETURN SUCCESSFUL
        messages.push(player1 ? "Player one attacked Player two" : "Player two attacked Player one");
        messages.push("Winner: " + winner);

        output.add(messages);
        output.add(winner);
        return output;
    }


    public ArrayList<Object> coordinate(boolean player1_turn, Stack<String> messages, int[][] coordinates, int[][] gameBoard, List current_mobs, List enemy_mobs) {
        ArrayList<Object> output = new ArrayList<Object>();

        //CHECK FOR OUT OF BOUNDS COORDINATES
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i][0] < 2 || coordinates[i][0] > 21) {
                System.out.println("Coordinates must stay within the map");
                output.add(false);
                output.add(gameBoard);
                output.add(current_mobs);
                output.add(enemy_mobs);
                output.add(messages);
                return output;
            } else if (coordinates[i][0] < 1 || coordinates[i][1] > 18) {
                System.out.println("Coordinates must stay within the map");
                output.add(false);
                output.add(gameBoard);
                output.add(current_mobs);
                output.add(enemy_mobs);
                output.add(messages);
                return output;
            }
        }


        //CREATE FROM & TO COORDINATES
        int[] from = coordinates[0];
        int[] to = coordinates[1];


        // FETCH OBJECT METHODS FROM TROOPS (current_mobs) LIST
        HashMap<String, HashMap<String, Method>> interactive_functions = null;
        HashMap<String, Method> setters = null;
        HashMap<String, Method> getters = null;


        //CHECK IF COORDINATES ARE IN PLAYERS TROOPS
        Boolean found = false;
        int[] current_coordinates = new int[2];
        Object selected_mob = null;
        for (int i = 0; i < current_mobs.size(); i++) {
            //CALL SETTERS & GETTERS OF INTERACTED OBJECT
            selected_mob = current_mobs.get(i);
            interactive_functions = Humanoid.setters_and_getters(selected_mob);
            setters = interactive_functions.get("setters");
            getters = interactive_functions.get("getters");

            // FUNCTIONALITY ALGO
            try {
                // METHODS TO INVOKE UPON OTHER INTRACTABLE
                Method get_location = Humanoid.fetchMethod(getters, "getCoordinate()");

                // INVOKE GET PROCESS
                current_coordinates = (int[]) get_location.invoke(selected_mob);

                //CHECK IF CURRENT COORDINATE == USER INPUT:FROM
                if (current_coordinates[0] == from[0] && current_coordinates[1] == from[1]) {
                    found = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println(e);
                //NULL EXCEPTION IS WHEN OBJECT ISN'T INTRACTABLE
            }
        }


        // COORDINATE NOT FOUND
        if (!found) {
            System.out.println("INVALID PLAYER COORDINATE");
            output.add(false);
            output.add(gameBoard);
            output.add(current_mobs);
            output.add(enemy_mobs);
            output.add(messages);
            return output;
        }


        //PLACE OR FIGHT PLAYERS
        List<Object> fight_outcome;
        Method set_location = Humanoid.fetchMethod(setters, "setCoordinate(int[])");
        Method get_location = Humanoid.fetchMethod(getters, "getCoordinate()");
        try {

            //COLLIDING WITH OTHER PLAYER WHEN MOVING
            if (!(gameBoard[to[0]][to[1]] == 9)) {

                //FIGHT!
                fight_outcome = fight(messages, enemy_mobs, selected_mob, to, player1_turn);

                messages = (Stack<String>) fight_outcome.get(0);
                String winner = (String) fight_outcome.get(1);

                if (winner == null) {
                    //INVALID  RETURN
                    System.out.println("YOU CANNOT ATTACK MATES.");
                    output.add(false);
                    output.add(gameBoard);
                    output.add(current_mobs);
                    output.add(enemy_mobs);
                    output.add(messages);
                    return output;

                } else {
                    //VALID
                    //REMOVE LOSER FROM EITHER current_mobs OR enemy_mobs
                    System.out.println(current_mobs); // update if enemy wins
                    System.out.println(enemy_mobs); // update if you win
                    System.out.println(gameBoard); // update to winner
                    System.out.println(messages); // push winner title

                }


            } else {
                //SWAPPING EMPTY-SPACE AND PLAYER
                set_location.invoke(selected_mob, to);
                int temp = gameBoard[from[0]][from[1]];
                gameBoard[from[0]][from[1]] = gameBoard[to[0]][to[1]];
                gameBoard[to[0]][to[1]] = temp;
            }

        } catch (Exception e) {

        }


        //SUCCESSFUL RETURN
        output.add(true);
        output.add(gameBoard);
        output.add(current_mobs);
        output.add(enemy_mobs);
        output.add(messages);
        return output;
    }


}
