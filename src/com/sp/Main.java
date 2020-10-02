package com.sp;

import javafx.util.Pair;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    private static int count_states;
    private static int begin_state=1;
    private static Map<Pair<Integer,String>,Integer> moves;
    private static HashSet<String> worlds = new HashSet<>();
    private static int count_final_states;
    private static ArrayList<Integer> finalStates;
    private static int count_letters;
    private static ArrayList<String> alfavit;
    public static void main(String[] args)  {
        try {
            File file = new File("text.txt");

            Scanner scan = new Scanner(file);
            count_states = scan.nextInt();

            count_final_states = scan.nextInt();
            finalStates = new ArrayList<>();
            for(int i=0;i<count_final_states;i++)
            {
                finalStates.add(scan.nextInt());
            }
            count_letters = scan.nextInt();
            alfavit = new ArrayList<>();
            for(int i=0;i<count_letters;i++)
            {
                alfavit.add(scan.next());
            }
            moves= new HashMap<Pair<Integer,String>,Integer>();
            while(scan.hasNext()){
                Integer state = scan.nextInt();
                String letter = scan.next();
                Integer next_state = scan.nextInt();
                if(state!=next_state){
                    Pair<Integer, String> move = new Pair<Integer, String>(state,letter);
                    moves.put(move,next_state);
                }
                else {
                    System.out.println(state + " has petlia");
                }
            }
            generateWorlds();
        }
        catch (Exception e)
        {

        }
    }

    private static void generateWorlds() {
        String word = "";
        Integer cur_state = begin_state;
        int length = 6 ;
        List<Pair<Integer,Integer>> visited_states = new ArrayList<>();
        recGenWord(cur_state,visited_states,word,length);
        for (String c:
             worlds) {
            if(c.length()> length-2)
            System.out.println(c);

        }
    }
    private static void recGenWord(int cur_state, List<Pair<Integer,Integer>> visited_states, String word, int length){
        if(length>0) {
            Map<Pair<Integer,String>,Integer> cur_moves = getAllMovesFromState(cur_state);
            for (var move :cur_moves.entrySet()
                    ) {
              //  String cur_word =word+ move.getKey().getValue();

                if(!visited_states.contains(new Pair<>(cur_state, move.getValue()))) {
                    visited_states.add(new Pair<>(cur_state,move.getValue()));
                    recGenWord(move.getValue(), visited_states, word+ move.getKey().getValue(), length - 1);
                    visited_states.remove(new Pair<>(cur_state, move.getValue()));
                }

                if(finalStates.contains(cur_state))
                {
                    //System.out.println(word);
                    worlds.add(word);
                }
                //
              //  cur_state = move.getKey().getKey();
            }
        }

    }
    private static Map<Pair<Integer,String>,Integer> getAllMovesFromState(int state)
    {
        Map<Pair<Integer,String>,Integer> cur_moves = new HashMap<Pair<Integer,String>,Integer>();
        for(String c:alfavit)
        {
            if(moves.containsKey(new Pair<>(state,c)))
                cur_moves.put(new Pair<>(state,c),moves.get(new Pair<>(state,c)));
        }
        return cur_moves;
    }
}
