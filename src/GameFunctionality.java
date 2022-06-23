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


    public static List<Object> fight(HashMap<String, HashMap<String, Method>> selected_mob_functions, Stack<String> messages, List<Object> enemy_mobs, Object selected_mob, int[] to, boolean player1) {
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
        //SELECTED PLAYER FUNCTIONS
        Method selected_player_get_cb_lvl = Humanoid.fetchMethod(selected_mob_functions.get("getters"), "getCombatLVL()");
        Method selected_player_get_health = Humanoid.fetchMethod(selected_mob_functions.get("getters"), "getHealth()");
        Method selected_player_get_attack = Humanoid.fetchMethod(selected_mob_functions.get("getters"), "getAttack()");
        Method selected_player_get_strength = Humanoid.fetchMethod(selected_mob_functions.get("getters"), "getStrength()");
        Method selected_player_get_defence = Humanoid.fetchMethod(selected_mob_functions.get("getters"), "getDefence()");
        Method selected_player_get_intelligence = Humanoid.fetchMethod(selected_mob_functions.get("getters"), "getIntelligence()");
        Method selected_player_get_compassion = Humanoid.fetchMethod(selected_mob_functions.get("getters"), "getCompassion()");
        Method selected_player_set_health = Humanoid.fetchMethod(selected_mob_functions.get("setters"), "setHealth(int)");


        // ENEMY METHODS
        Method get_cb_lvl = Humanoid.fetchMethod(getters, "getCombatLVL()");
        Method get_health = Humanoid.fetchMethod(getters, "getHealth()");
        Method get_attack = Humanoid.fetchMethod(getters, "getAttack()");
        Method get_strength = Humanoid.fetchMethod(getters, "getStrength()");
        Method get_defence = Humanoid.fetchMethod(getters, "getDefence()");
        Method get_intelligence = Humanoid.fetchMethod(getters, "getIntelligence()");
        Method get_compassion = Humanoid.fetchMethod(getters, "getCompassion()");
        Method set_health = Humanoid.fetchMethod(setters, "setHealth(int)");

        //simulate your attack/defence roll
        //simulate their attack/defence roll
        //do maths and iterations to subtract hp
        //find winner
        //push fight stats to message board
        String fight_round_results = "";
        try {
            Random random = new Random();

            //INIT STAT METHODS
            int selected_cb = (int) selected_player_get_cb_lvl.invoke(selected_mob);
            int selected_hp = (int) selected_player_get_health.invoke(selected_mob);
            int selected_attack = (int) selected_player_get_attack.invoke(selected_mob);
            int selected_strength = (int) selected_player_get_strength.invoke(selected_mob);
            int selected_defence = (int) selected_player_get_defence.invoke(selected_mob);
            int selected_intelligence = (int) selected_player_get_intelligence.invoke(selected_mob);
            int selected_compassion = (int) selected_player_get_compassion.invoke(selected_mob);

            int enemy_cb = (int) get_cb_lvl.invoke(iteration_mob);
            int enemy_hp = (int) get_health.invoke(iteration_mob);
            int enemy_attack = (int) get_attack.invoke(iteration_mob);
            int enemy_strength = (int) get_strength.invoke(iteration_mob);
            int enemy_defence = (int) get_defence.invoke(iteration_mob);
            int enemy_intelligence = (int) get_intelligence.invoke(iteration_mob);
            int enemy_compassion = (int) get_compassion.invoke(iteration_mob);


            //INIT RANDOM RANGES FROM THESE STATS
            int your_random_atk;
            int your_random_def;
            int your_random_intel;
            int your_random_compassion;
            int your_random_str;
            double your_atk;
            double your_def;

            int their_random_atk;
            int their_random_def;
            int their_random_intel;
            int their_random_compassion;
            int their_random_str;
            double their_atk;
            double their_def;



            // RUNESCAPE calculate "e" for both players :
            int round_number = 0;
            while ((int) selected_player_get_health.invoke(selected_mob) > 0 && (int) get_health.invoke(iteration_mob) > 0) {

                //RANDOM PLAYER STATS RNG
                your_random_atk = random.ints(1, selected_attack).findFirst().getAsInt();
                your_random_def = random.ints(1, selected_defence).findFirst().getAsInt();
                your_random_intel = random.ints(1, selected_intelligence).findFirst().getAsInt();
                your_random_compassion = random.ints(1, selected_compassion).findFirst().getAsInt();
                your_random_str = random.ints(1, selected_strength).findFirst().getAsInt();

                your_atk = (Math.floor(Math.ceil(your_random_atk * your_random_intel) - your_random_compassion) * (your_random_str + 64));
                your_def = (Math.floor(Math.ceil(your_random_def * your_random_intel) - your_random_compassion) * (your_random_str + 64));

                //RANDOM ENEMY STATS RNG
                their_random_atk = random.ints(1, enemy_attack).findFirst().getAsInt();
                their_random_def = random.ints(1, enemy_defence).findFirst().getAsInt();
                their_random_intel = random.ints(1, enemy_intelligence).findFirst().getAsInt();
                their_random_compassion = random.ints(1, enemy_compassion).findFirst().getAsInt();
                their_random_str = random.ints(1, enemy_strength).findFirst().getAsInt();

                their_atk = Math.floor(Math.ceil(their_random_atk * their_random_intel) - their_random_compassion) * (their_random_str + 64);
                their_def = Math.floor(Math.ceil(their_random_def * their_random_intel) - their_random_compassion) * (their_random_str + 64);


                //DISPLAY FIGHT HITS
                int hp;
                if (your_atk > their_def) {
                    hp = (int) get_health.invoke(iteration_mob);
                    set_health.invoke(iteration_mob, hp - 1);
                    fight_round_results += "A ";
                } else if (their_atk > your_def) {
                    hp = (int) selected_player_get_health.invoke(selected_mob);
                    selected_player_set_health.invoke(selected_mob, hp - 1);
                    fight_round_results += "D ";
                }


                //ROUND RESULTS
                System.out.println("Attacker: " + your_atk + "\tvs.\t" + "Defenders: " + their_def);
                System.out.println("Attacker: " + your_def + "\tvs.\t" + "Defenders: " + their_atk  );

                round_number++;
            }


            //RIGHT AFTER THE FIGHT
            if ((int) selected_player_get_health.invoke(selected_mob) > (int) get_health.invoke(iteration_mob)) {
                System.out.println(player1 ? "Player one " : "player two " + "wins the fight!");
                winner = selected_mob;
            } else {
                System.out.println(player1 ? "Player two " : "player one " + "wins the fight!");
                winner = iteration_mob;

            }


        } catch (Exception e) {
            System.out.println(e);
            System.out.println("There was a problem calculating a winner");
        }


        //IF ATK ROLL BIGGER -> (1 – [(DEF + 2) / (2 x (ATK + 1))]
        //IF DEF ROLL BIGGER ->  [ATK/ (2 x DEF +1)]

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
        messages.push("Winner: " + winner);
        messages.push( "ROUND RESULTS: " +  fight_round_results );
        messages.push(player1 ? "Player one attacked Player two" : "Player two attacked Player one");

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
                fight_outcome = fight(interactive_functions, messages, enemy_mobs, selected_mob, to, player1_turn);

                messages = (Stack<String>) fight_outcome.get(0);
                String winner = (String) fight_outcome.get(1);

                if (winner != null) {
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
                    System.out.println(messages); // push winner title
                    System.out.println(gameBoard); // update to winner

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
