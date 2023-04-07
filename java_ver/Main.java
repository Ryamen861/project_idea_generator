import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Random rand = new Random();

    private static ArrayList<String> languages = new ArrayList<>(Arrays.asList(
        "Python", "Java", "Rust", "C", "C++", "C#", "JavaScript",
                        "Go", "Swift", "TypeScript", "Kotlin", "Ruby"
    ));

    private static ArrayList<String> items = new ArrayList<>(Arrays.asList(
        "banana", "lamp", "Costco Pizza", "pencil sharpener", "whiteboard", "SSD"
    ));

    private static HashMap<String, ArrayList<String>> name_to_list_link = new HashMap<>();

    private static int index;
    private static int index2;
    private static boolean got_valid_input = false;
    private static boolean found_list_to_edit;
    private static boolean want_to_quit = false;
    private static int order_num;

    private static String[] idea_chunk = new String[3];
    private static String output_str;

    private static String desired_list_to_edit;
    private static String new_addition;
    private static boolean addition_decided;

    public static void main(String[] args) {

        name_to_list_link.put("item", items);
        name_to_list_link.put("language", languages);
        System.out.println("Welcome to the Project Idea Generator!");

        while ((!got_valid_input) || (!want_to_quit)) {
            System.out.println(
                "   Press 1 for a random suggestion\n   Press 2 to add to a category\n   Press 101 to quit"
            );

            try {
                order_num = Integer.parseInt(sc.nextLine());
            } catch(Exception e) {
                continue;
            } 
            got_valid_input = true; 
            
            // calling the orders
            if (order_num == 1) {
                get_random_inspiration();
            } else if (order_num == 2) {
                select_category();
            } else if (order_num == 101) {
                want_to_quit = true;
            }
        }
        sc.close();
    }

    private static void get_random_inspiration() {
        // language
        // note: rand.nextInt excludes the upperbound
        index = rand.nextInt(languages.size());
        idea_chunk[0] = languages.get(index);

        // get two random objects
        for (int i = 1; i < 3; i++) {
            index = rand.nextInt(items.size());

            // if on the second loop, we pick the same index,
            if ((i == 2) && index2 == index) {
                // pick a new index until we get one that is not the same as the previous
                while (index == index2) {
                    index = rand.nextInt(items.size());
                }
            }

            idea_chunk[i] = items.get(index);

            // use this to check later if the indeces are the same
            index2 = index;
        }
        
                        // String.format returns the formatted string
        output_str = String.format("Write a program about a %s and/or a %s using %s\n\n", idea_chunk[2], idea_chunk[1], idea_chunk[0]);
        System.out.println(output_str);
    }

    private static void select_category() {
        found_list_to_edit = false;
        addition_decided = false;

        while (!found_list_to_edit) {
            System.out.println("Type 'item' or 'language' ('quit' to exit process)");
            desired_list_to_edit = sc.nextLine();

            if (desired_list_to_edit.equals("quit")) {
                break;
            }

            if (desired_list_to_edit.equals("item")) {
                add_to_category("items");
                found_list_to_edit = true;
            } else if (desired_list_to_edit.equals("language")) {
                add_to_category("languages");
                found_list_to_edit = true;
            } else {
                continue;
            }
        }
    }

    private static void add_to_category(String list_to_change) {
        while (!addition_decided) {
            // once there is a valid input, read what they want to add
            System.out.println(
                String.format("Please type in what you want added to %s ('quit' to exit process)", desired_list_to_edit)
            );
            new_addition = sc.nextLine();
            // if the user typed in quit, quit this process
            if (new_addition.equals("quit")) {
                break;
            }

            // get confirmation
            output_str = String.format("Are you sure you want %s added? Type 'y' or 'n': ", new_addition);
            System.out.println(output_str);

                // if yes, then add
            if (sc.nextLine().equals("y")) {
                addition_decided = true;
                // change the desired list
                if (list_to_change.equals("items")) {
                    items.add(new_addition);
                } else if (list_to_change.equals("languages")) {
                    languages.add(new_addition);
                }

            } else {
                // if 'no' or invalid, restart process
                continue;
            }
        }
    }
}