package com.example.hangmanapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.example.hangmanapp.R.raw.words;

public class Game
{
    private ArrayList<String> wordsList;
    private String guessword;
    private String guessed;
    private GameStatus gameStatus;
    private  int numofwrongGuesses;
    private MainActivity mainActivity;



    Game(MainActivity activity)
    {
        mainActivity = activity;
        wordsList = new ArrayList<String>();
        ReadTextFile();


    }

    public GameStatus getStatus()
    {
        return gameStatus;
    }

    public int getwrongGuesses()
    {
        return numofwrongGuesses;
    }

    public String getGuessed()
    {
        return guessed;
    }




    void play()
    {
        pickRandomWord();
        initializeGuessed();
        numofwrongGuesses = 0;
        gameStatus = GameStatus.PLAYING;
    }

    public void ReadTextFile(){
        String wordstr = "";
        StringBuilder stringBuilder = new StringBuilder();
       InputStream is = mainActivity.getResources().openRawResource(R.raw.words);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((wordstr = reader.readLine()) == null)
                {
                    break;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            wordsList.add(wordstr);

        }
        try
        {
            is.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

   private void pickRandomWord()
   {
       Random rand = new Random();

       // Generate random integers in range 0 to 999
       int num = rand.nextInt(10);
       guessword = wordsList.get(num);

   }
   private void initializeGuessed()
   {
       char[] chars = new char[guessword.length()];
       Arrays.fill(chars, '_');
       guessed = new String(chars);
   }

   private char getcharfromUser()
   {
       return ' ';
   }



    int  checkLetter(String letter)
    {
       char c = letter.charAt(0);
        StringBuilder strguessed = new StringBuilder(guessed);
        int num = 0;
        for(int i=0; i< guessword.length(); i++)
       {
           if(guessword.charAt(i) == c)
           {
               strguessed.setCharAt(i, c);
               num++;
           }
       }

        guessed = strguessed.toString();
        if (num == 0)
        {
            numofwrongGuesses++;
        }

        return num;
    }
   GameStatus checkGameStatus()
    {
       if(guessword.equals(guessed))
       {
          gameStatus = GameStatus.PLAYERWON;
           return gameStatus;
       }
       else if(numofwrongGuesses == 7)
        {
            gameStatus = GameStatus.COMPUTERWON;
            return gameStatus;
        }
       else
       {
           gameStatus = GameStatus.PLAYING;
           return gameStatus;
       }
    }





    void displayHanger(int guesses)
    {

    }
}
