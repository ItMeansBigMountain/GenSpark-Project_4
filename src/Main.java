import HUMANOID_FAMILY.*;

import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        //USER INPUT INIT
        Scanner USER_INPUT = new Scanner(System.in);
        String choice = "";
        boolean valid = false;


        //INIT GAME SCREEN
        HashMap<Integer, String> game_key = new HashMap();
        game_key.put(-18, "18");                 // -18  "_Number"
        game_key.put(-17, "17");                 // -17  "_Number"
        game_key.put(-16, "16");                 // -16  "_Number"
        game_key.put(-15, "15");                 // -15  "_Number"
        game_key.put(-14, "14");                 // -14  "_Number"
        game_key.put(-13, "13");                 // -13  "_Number"
        game_key.put(-12, "12");                 // -12  "_Number"
        game_key.put(-11, "11");                 // -11  "_Number"
        game_key.put(-10, "10");                 // -10  "_Number"
        game_key.put(-9, "9");                   //  -1  "_Number"
        game_key.put(-8, "8");                   //  -1  "_Number"
        game_key.put(-7, "7");                   //  -1  "_Number"
        game_key.put(-6, "6");                   //  -1  "_Number"
        game_key.put(-5, "5");                   //  -1  "_Number"
        game_key.put(-4, "4");                   //  -1  "_Number"
        game_key.put(-3, "3");                   //  -1  "_Number"
        game_key.put(-2, "2");                   //  -1  "_Number"
        game_key.put(-1, "1");                   //  -1  "_Number"

        game_key.put(0, "\uD83C\uDF32");         //   0  "SCENERY TREE"
        game_key.put(1, "\uD83E\uDD37\u200D");   //   1  "HUMAN"
        game_key.put(2, "\uD83D\uDC7A");         //   2  "GOBLIN"
        game_key.put(3, "\uD83D\uDC7D");         //   3  "MARTIAN"
        game_key.put(4, "\uD83E\uDD22");         //   4  "ZOMBIE"
        game_key.put(5, "\uD83C\uDF0C");         //   5  "SOLAR POTATO"

        game_key.put(7, "◼");                    //   7  "White Tile"
        game_key.put(8, "⬜");                    //   8  "Black Tile"
        game_key.put(9, "");                     //   9  "OPEN AREA"


        int[][] game_board = {
                {0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 9, 9, 9, 9, 9, 4, 2, 3, 1, 1, 3, 2, 4, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0},
                {0, 9, 9, 9, 9, 9, 4, 2, 3, 1, 1, 3, 2, 4, 9, 9, 9, 9, 9, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -17, -18, 0},
        };


        //DRAW SCREEN INIT
        GameFunctionality g = new GameFunctionality();


        //GAME RULES 2 DIMENSIONAL ARRAY
        System.out.println("\n\n\nGAME RULES");
        System.out.println("-\t Add input of coordinates you want to move ... ");
        System.out.println("use '.' full stop to seperate FROM . TO");
        System.out.println("EXAMPLES: 6,21 . 5,21 \tChess...");


        Random random = new Random();
        List<Object> player_one_mobs = new ArrayList<Object>();
        player_one_mobs.add(new Zombie(random, new int[]{21, 6}));
        player_one_mobs.add(new Goblin(random, new int[]{21, 7}));
        player_one_mobs.add(new Martian(random, new int[]{21, 8}));
        player_one_mobs.add(new Human(random, new int[]{21, 9}));
        player_one_mobs.add(new Human(random, new int[]{21, 10}));
        player_one_mobs.add(new Martian(random, new int[]{21, 11}));
        player_one_mobs.add(new Goblin(random, new int[]{21, 12}));
        player_one_mobs.add(new Zombie(random, new int[]{21, 13}));


        List<Object> player_two_mobs = new ArrayList<Object>();
        player_two_mobs.add(new Zombie(random, new int[]{2, 6}));
        player_two_mobs.add(new Goblin(random, new int[]{2, 7}));
        player_two_mobs.add(new Martian(random, new int[]{2, 8}));
        player_two_mobs.add(new Human(random, new int[]{2, 9}));
        player_two_mobs.add(new Human(random, new int[]{2, 10}));
        player_two_mobs.add(new Martian(random, new int[]{2, 11}));
        player_two_mobs.add(new Goblin(random, new int[]{2, 12}));
        player_two_mobs.add(new Zombie(random, new int[]{2, 13}));


        //CONSOLE APP
        Stack<String> message_box = new Stack<>();
        ArrayList<Object> validity;
        boolean player1_turn = false;
        boolean running = true;
        int round = 1;
        while (running) {

            player1_turn = !player1_turn;
            List<Object> current_mobs = (player1_turn ? player_one_mobs : player_two_mobs);
            List<Object> enemy_mobs = (player1_turn ? player_two_mobs : player_one_mobs);


            // DISPLAY GAME
            for (int i = 0; i < game_board.length; i++) {
                System.out.println();
                System.out.print(String.valueOf(i) + "\t");
                //EACH ROW
                for (int y = 0; y < game_board[i].length; y++) {
                    System.out.print(game_key.get(game_board[i][y]) + "\t");
                }
                // APPEND TO SIDE BAR SCREEN
                if (i == 0) System.out.print("TURN: " + round);
                if (i == 1) System.out.print("PLAYER: " + (player1_turn ? "One" : "Two"));
                if (i == 2) System.out.print("TROOPS: " + current_mobs.size());
                if (i == 4) System.out.print("STATS");
                if (i == 5) {
                    System.out.print("hp".toUpperCase() + "\t\t\t");
                    for (int x = 0; x < current_mobs.size(); x++) {
                        if (current_mobs.get(x) instanceof Human) {
                            Human human = (Human) current_mobs.get(x);
                            System.out.print(human.getHealth() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Goblin) {
                            Goblin goblin = (Goblin) current_mobs.get(x);
                            System.out.print(goblin.getHealth() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Zombie) {
                            Zombie zombie = (Zombie) current_mobs.get(x);
                            System.out.print(zombie.getHealth() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Martian) {
                            Martian martian = (Martian) current_mobs.get(x);
                            System.out.print(martian.getHealth() + "\t|\t");
                        }

                    }

                }
                if (i == 6) {
                    System.out.print("atk".toUpperCase() + "\t\t\t");
                    for (int x = 0; x < current_mobs.size(); x++) {
                        if (current_mobs.get(x) instanceof Human) {
                            Human human = (Human) current_mobs.get(x);
                            System.out.print(human.getAttack() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Goblin) {
                            Goblin goblin = (Goblin) current_mobs.get(x);
                            System.out.print(goblin.getAttack() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Zombie) {
                            Zombie zombie = (Zombie) current_mobs.get(x);
                            System.out.print(zombie.getAttack() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Martian) {
                            Martian martian = (Martian) current_mobs.get(x);
                            System.out.print(martian.getAttack() + "\t|\t");
                        }

                    }

                }
                if (i == 7) {
                    System.out.print("str".toUpperCase() + "\t\t\t");
                    for (int x = 0; x < current_mobs.size(); x++) {
                        if (current_mobs.get(x) instanceof Human) {
                            Human human = (Human) current_mobs.get(x);
                            System.out.print(human.getStrength() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Goblin) {
                            Goblin goblin = (Goblin) current_mobs.get(x);
                            System.out.print(goblin.getStrength() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Zombie) {
                            Zombie zombie = (Zombie) current_mobs.get(x);
                            System.out.print(zombie.getStrength() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Martian) {
                            Martian martian = (Martian) current_mobs.get(x);
                            System.out.print(martian.getStrength() + "\t|\t");
                        }

                    }

                }
                if (i == 8) {
                    System.out.print("def".toUpperCase() + "\t\t\t");
                    for (int x = 0; x < current_mobs.size(); x++) {
                        if (current_mobs.get(x) instanceof Human) {
                            Human human = (Human) current_mobs.get(x);
                            System.out.print(human.getDefence() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Goblin) {
                            Goblin goblin = (Goblin) current_mobs.get(x);
                            System.out.print(goblin.getDefence() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Zombie) {
                            Zombie zombie = (Zombie) current_mobs.get(x);
                            System.out.print(zombie.getDefence() + "\t|\t");
                        } else if (current_mobs.get(x) instanceof Martian) {
                            Martian martian = (Martian) current_mobs.get(x);
                            System.out.print(martian.getDefence() + "\t|\t");
                        }
                    }

                }

                if (i == 9) {
                    System.out.print("vertex".toUpperCase() + "\t  ");
                    for (int x = 0; x < current_mobs.size(); x++) {
                        if (current_mobs.get(x) instanceof Human) {
                            Human human = (Human) current_mobs.get(x);
                            System.out.print("["+human.getCoordinate()[1] + "," + human.getCoordinate()[0]+ "]  "  );
                        } else if (current_mobs.get(x) instanceof Goblin) {
                            Goblin goblin = (Goblin) current_mobs.get(x);
                            System.out.print("["+goblin.getCoordinate()[1] + "," + goblin.getCoordinate()[0]+ "]  "  );
                        } else if (current_mobs.get(x) instanceof Zombie) {
                            Zombie zombie = (Zombie) current_mobs.get(x);
                            System.out.print("["+zombie.getCoordinate()[1] + "," + zombie.getCoordinate()[0]+ "]  "  );
                        } else if (current_mobs.get(x) instanceof Martian) {
                            Martian martian = (Martian) current_mobs.get(x);
                            System.out.print( "["+ martian.getCoordinate()[1] + "," + martian.getCoordinate()[0] + "]  "  );
                        }

                    }
                }

                if (i == 10) {
                    System.out.print("\t\t\t");
                    for (int x = 0; x < current_mobs.size(); x++) {
                        int len = String.valueOf(current_mobs.get(x)).length();
                        String first_letter = String.valueOf(String.valueOf(current_mobs.get(x)).charAt(0)).toLowerCase();
                        String lvl = String.valueOf(current_mobs.get(x)).split(":")[1];
                        System.out.print(first_letter + lvl + "\t\t");
                    }
                }




                //MESSAGE BOX
                if (i == 13) {
                    if (!message_box.isEmpty()) System.out.print(message_box.pop());
                }
                if (i == 14) {
                    if (!message_box.isEmpty()) System.out.print(message_box.pop());
                }
                if (i == 15) {
                    if (!message_box.isEmpty()) System.out.print(message_box.pop());
                }
                if (i == 16) {
                    if (!message_box.isEmpty()) System.out.print(message_box.pop());
                }
                if (i == 17) {
                    if (!message_box.isEmpty()) System.out.print(message_box.pop());
                }
                if (i == 18) {
                    if (!message_box.isEmpty()) System.out.print(message_box.pop());
                }




                if (i == 22) System.out.print("Choose Player & Location");
            }


            // USER INPUTS VALIDATION
            int[][] user_coordinates = null;
            valid = false;
            while (!valid) {
                System.out.print(">\t");
                choice = USER_INPUT.nextLine();
                user_coordinates = g.validateCharacterSelect(choice);

                if (user_coordinates != null) {
                    validity = g.coordinate(player1_turn, message_box, user_coordinates, game_board, current_mobs, enemy_mobs);
                    valid = (boolean) validity.get(0);
                    game_board = (int[][]) validity.get(1);
                    current_mobs = (List) validity.get(2);
                    enemy_mobs = (List) validity.get(3);
                    message_box = (Stack<String>) validity.get(4);
                } else {
                    valid = false;
                }
            }


            // MESSAGE BOX UPDATE
            // message_box.push(String.valueOf(valid));


            //REASSIGN TROOPS STATUS
            if (player1_turn) {
                player_one_mobs = current_mobs;
                player_two_mobs = enemy_mobs;
            } else {
                player_one_mobs = enemy_mobs;
                player_two_mobs = current_mobs;
            }

            round++;
        }


    }
}
