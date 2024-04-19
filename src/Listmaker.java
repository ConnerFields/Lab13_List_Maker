import javax.swing.JFileChooser;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class ListMaker
{
    private static final ArrayList<String> myArrList = new ArrayList<>(); // ArrayList to store strings
    private static final Scanner scanner = new Scanner(System.in); // Scanner for user input
    private static boolean needsToBeSaved = false; // Flag to track if the list needs to be saved
    private static File currentFile = null; // Currently loaded file

    public static void main(String[] args)
    {
        char choice; // Initialize choice with a default value
        while (true)
        {
            displayMenu(); // Display the menu
            choice = getRegExString(); // Get user choice from the menu
            switch (choice)
            {
                case 'A':
                case 'a':
                    addItemToList(); // Add an item to the list
                    break;
                case 'D':
                case 'd':
                    deleteItemFromList(); // Delete an item from the list
                    break;
                case 'V':
                case 'v':
                    printList(); // View the list
                    break;
                case 'O':
                case 'o':
                    openListFile(); // Open a list file from disk
                    break;
                case 'S':
                case 's':
                    saveListToFile(); // Save the current list file to disk
                    break;
                case 'C':
                case 'c':
                    clearList(); // Clear the list
                    break;
                case 'Q':
                case 'q':
                    if (needsToBeSaved)
                    {
                        if (confirmSave())
                        {
                            saveListToFile();
                        }
                    }
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid input! Please enter a valid menu option.");
            }
        }
    }


    private static void displayMenu()  // Display the menu options
    {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
        System.out.print("Enter your choice: ");
    }


    private static void addItemToList()  // Add an item to the list
    {
        System.out.print("Enter the item to add: ");
        String item = scanner.nextLine();
        myArrList.add(item);
        needsToBeSaved = true; // Mark the list as needing to be saved
        System.out.println("Item added successfully!");
    }


    private static void deleteItemFromList() // Delete an item from the list
    {
        if (myArrList.isEmpty())
        {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }

        System.out.println("Current items in the list:");
        printNumberedList(); // Display the list with numbers

        System.out.print("Enter the number of the item to delete: ");
        int index = getRangedInt(myArrList.size()) - 1;
        String deletedItem = myArrList.remove(index);
        needsToBeSaved = true; // Mark the list as needing to be saved
        System.out.println("Item '" + deletedItem + "' deleted successfully!");
    }


    private static void printList()    // View the list
    {
        if (myArrList.isEmpty())
        {
            System.out.println("The list is empty.");
            return;
        }
        System.out.println("Current items in the list:");
        for (String item : myArrList)
        {
            System.out.println("- " + item);
        }
    }


    private static void openListFile()
    {
        if (needsToBeSaved) // Check if the current list needs to be saved
        {
            if (confirmSave()) // Prompt the user to save the list before loading a new file
            {
                saveListToFile();
            }
            else
            {
                System.out.println("List not loaded. Save your current list or clear it to proceed.");
                return;
            }
        }
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile)))
            {
                myArrList.clear();
                String line;
                while ((line = reader.readLine()) != null)
                {
                    myArrList.add(line);
                }
                currentFile = selectedFile;
                needsToBeSaved = false; // Mark the list as not needing to be saved initially
                System.out.println("List loaded from file successfully.");
            }
            catch (IOException e)
            {
                System.err.println("Error reading the file: " + e.getMessage());
            }
        }
        else
        {
            System.out.println("No file selected.");
        }
    }


    private static void saveListToFile() // Save the current list file to disk
    {
        if (currentFile == null)
        {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                currentFile = fileChooser.getSelectedFile();
            }
            else
            {
                System.out.println("No file selected. List not saved.");
                return;
            }
        }
        try (PrintWriter writer = new PrintWriter(currentFile))
        {
            for (String item : myArrList)
            {
                writer.println(item);
            }
            needsToBeSaved = false; // Mark the list as saved
            System.out.println("List saved to file successfully.");
        }
        catch (IOException e)
        {
            System.err.println("Error saving the file: " + e.getMessage());
        }
    }


    private static void clearList() // Clear the list
    {
        myArrList.clear();
        needsToBeSaved = true; // Mark the list as needing to be saved
        System.out.println("List cleared successfully.");
    }


    private static boolean confirmSave() // Prompt the user to save changes to the list
    {
        System.out.print("Do you want to save changes to the list? (Y/N): ");
        return getYNConfirm();
    }


    private static void printNumberedList() // Print numbered list
    {
        for (int i = 0; i < myArrList.size(); i++)
        {
            System.out.println((i + 1) + ". " + myArrList.get(i));
        }
    }


    private static char getRegExString() // Validate user input
    {
        String input;
        do
        {
            input = scanner.nextLine().trim();
            if (!input.matches("[AaDdVvOoSsCcQq]"))
            {
                System.out.println("Invalid input! Please enter a valid menu option.");
            }
        } while (!input.matches("[AaDdVvOoSsCcQq]"));
        return input.charAt(0);
    }


    private static int getRangedInt(int max) // Validate and return an integer within the specified range
    {
        int num;
        do
        {
            while (!scanner.hasNextInt())
            {
                System.out.print("Invalid input! Enter a number: ");
                scanner.next();
            }
            num = scanner.nextInt();
            if (num < 1 || num > max)
            {
                System.out.print("Invalid input! Enter a number between 1 and " + max + ": ");
            }
        } while (num < 1 || num > max);
        scanner.nextLine(); // Consume newline
        return num;
    }


    private static boolean getYNConfirm() // Validate yes/no confirmation
    {
        String input;
        do
        {
            input = scanner.nextLine().trim().toUpperCase();
        } while (!input.equals("Y") && !input.equals("N"));
        return input.equals("Y");
    }
}
