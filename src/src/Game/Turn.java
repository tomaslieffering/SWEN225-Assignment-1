package Game;

public class Turn {

    /**
     * generates two random numbers between 1 and 6
     * @return the sum of the two numbers generated
     */
    public static int rollDice() {
        int diceOne = (int)(Math.random()*6) + 1;
        int diceTwo = (int)(Math.random()*6) + 1;
        printDice(diceOne);
        printDice(diceTwo);
        System.out.println("= " + (diceOne + diceTwo));
        return diceOne + diceTwo;
    }

    /**
     * Prints a unicode representation of the dice rolled
     * @param dice the dice to print
     */
    private static void printDice(int dice) {
        switch (Integer.toString(dice)){
            case "1":
                System.out.println("\u2680" + "1");
                break;
            case "2":
                System.out.println("\u2681" + "2");
                break;
            case "3":
                System.out.println("\u2682" + "3");
                break;
            case "4":
                System.out.println("\u2683" + "4");
                break;
            case "5":
                System.out.println("\u2684" + "5");
                break;
            case "6":
                System.out.println("\u2685" + "6");
                break;
        }
    }
}
