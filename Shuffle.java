/**
 * Created by Sarah on 1/18/18.
 * Simulates a human shuffle of a deck
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Shuffle {
    //initialize deck
    static int deckSize = 52;
    static int[] deck = new int[deckSize];
    static int numShuffles = 0;

    //build split mechanics
    static int splitError = 4;
    static int halfSplit = deckSize/2;

    //build labels for UI
    static JLabel infoLabel = new JLabel("Welcome to the riffle shuffle simulator! Here is the starting deck:");
    static JLabel deckLabel = new JLabel();

    public static void main(String[] args) {
        //build deck
        for (int i = 0; i < deck.length; i++) {
            deck[i] = i;
        }

        //initialize UI
        shuffleUI();
    }

    /**
     *
     * @param split where the deck is split
     * @param deckSize how many cards in the deck
     * @param deck the deck itself
     * @return the deck post shuffle
     */
    private static int[] ruffle(int split, int deckSize, int[] deck) {
        int[] shuffled = new int[deckSize];
        int[] top = new int[split];
        int[] bottom = new int[deckSize - split];

        //fill halves
        for (int i = 0; i < top.length; i++) {
            top[i] = deck[i];
        }
        for (int i = 0; i < bottom.length; i++) {
            bottom[i] = deck[i + split];
        }

        //ruffle
        Random r = new Random();
        int topPos = top.length - 1;
        int bottomPos = bottom.length - 1;
        for (int i = deckSize - 1; i >= 0; i--) {
            //pick which side card falls from at random
            if (r.nextBoolean()) {
                //if there's a card left on the top half, it falls
                if (topPos >= 0) {
                    shuffled[i] = top[topPos];
                    topPos--;
                } else i++;
            } else {
                //if there's a card left on the bottom half, it falls
                if (bottomPos >= 0) {
                    shuffled[i] = bottom[bottomPos];
                    bottomPos--;
                } else i++;
            }
        }

        return shuffled;
    }

    /**
     *
     * @return either a euchre deck or a regular deck. If null, user screwed up selection
     */
    private static String[] SelectDeck() {
        String[] suits = {" of Hearts", " of Diamonds", " of Spades", " of Clubs"};
        String[] values = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
        String[] deck;
        int deckSize;

        Scanner s = new Scanner(System.in);

        System.out.printf("Select deck:\nStandard (S)\nEuchre (E)\nCustom (C)\n");
        String selection = s.nextLine();
        if (selection.equalsIgnoreCase("standard") || selection.equalsIgnoreCase("s")) {
            System.out.printf("Standard deck selected.\n");
            deckSize = 52;
            deck = new String[deckSize];

        } else if (selection.equalsIgnoreCase("euchre") || selection.equalsIgnoreCase("e")) {
            System.out.printf("Euchre deck selected.\n");

        } else {
            System.out.printf("Whoops! Try again!\n");
        }
        return suits;
    }

    /**
     * converts a deck in array form to a deck in a string form
     * @param deck deck in int array form to be turned into a string
     * @return deck in string form
     */
    private static String deckToString(int[] deck) {
        String str = "";
        for (int i = 0; i < deck.length; i++)
            str += deck[i] + " ";
        return str;
    }
    
    /**
     * initializes UI
     */
    private static void shuffleUI() {
        //Construct UI
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Shuffle");
        Dimension d = new Dimension(1200, 300);
        frame.setPreferredSize(d);

        //Fill Content Pane
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //Display initial deck
        contentPane.add(infoLabel);
        deckLabel.setText(deckToString(deck));
        contentPane.add(deckLabel);

        //add button & listener
        JButton shuf = new JButton("Shuffle Cards");
        shuf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleAndUpdateUI();
            }
        });
        contentPane.add(shuf);


        //display window
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * updates deck and UI after a shuffle
     */
    private static void shuffleAndUpdateUI() {
        Random r = new Random();
        int split = (r.nextBoolean()) ? halfSplit + r.nextInt(splitError) : halfSplit - r.nextInt(splitError);
        deck = ruffle(split, deckSize, deck);
        numShuffles++;
        infoLabel.setText("Shuffle " + numShuffles);
        deckLabel.setText(deckToString(deck));
    }
}