import java.util.Scanner;
public class SafeInput
{
    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Forces loop until something is entered
        do
        {
            System.out.print("\n" + prompt + ": "); // shows the prompt
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;
    }

    public static int getInt(Scanner pipe, String prompt)
    {
        int value = 0; //create value
        boolean validInput = false;
        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) //test for valid input
            {
                value = pipe.nextInt();
                validInput = true;
            } else
            {
                System.out.println("Invalid input. Please enter a valid Number.");
                pipe.next(); // Consumes the invalid input
            }
        } while (!validInput);

        pipe.nextLine(); // Clear the newline character from the buffer
        return value;
    }

    public static double getDouble(Scanner pipe, String prompt) //created for numbers that use a "."
    {
        double value = 0;
        boolean validInput = false;
        do
        {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble())  //test for valid input
            {
                value = pipe.nextDouble();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a valid Number.");
                pipe.next(); // Consume the invalid input
            }
        } while (!validInput);

        pipe.nextLine(); // Clear the newline character from the buffer
        return value;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        int value = 0;
        do
        {
            value = getInt(pipe, prompt + " [" + low + " - " + high + "]");
        } while (value < low || value > high); //creating range to test for

        return value;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        double value = 0;
        do
        {
            value = getDouble(pipe, prompt + " [" + low + " - " + high + "]");
        } while (value < low || value > high);

        return value;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        boolean validInput;
        char response;
        do
        {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.next().toUpperCase().charAt(0); //Standardizes the responses
            validInput = response == 'Y' || response == 'N'; //sets parameters for valid inputs
            if (!validInput) //starts loop for invalid inputs
            {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!validInput);

        pipe.nextLine(); // Clear the newline character from the buffer
        return response == 'Y';
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx)
    {
        String value;
        boolean validInput;
        do
        {
            value = getNonZeroLenString(pipe, prompt); //doesn't let user enter nothing
            validInput = value.matches(regEx);
            if (!validInput) //starts loop for invalid inputs
            {
                System.out.println("Invalid input. Please follow the specified pattern.");
            }
        } while (!validInput);

        return value;
    }

    public static void prettyHeader(String msg) {
        int totalWidth = 60; //set totalWidth
        int msgWidth = msg.length();
        int sideStars = (totalWidth - msgWidth - 4) / 2; // Two stars on each side

        for (int i = 0; i < totalWidth; i++) // Print the top row of stars
        {
            System.out.print("*");
        }
        System.out.println();

        System.out.print("***");
        for (int i = 0; i < sideStars; i++)  // Print the second row with centered message
        {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < sideStars; i++)
        {
            System.out.print(" ");
        }
        System.out.println("***");


        for (int i = 0; i < totalWidth; i++)  // Print the bottom row of stars
        {
            System.out.print("*");
        }
        System.out.println();
    }
}

