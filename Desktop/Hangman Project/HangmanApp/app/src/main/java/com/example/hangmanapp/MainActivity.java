package com.example.hangmanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

   private  Button btnEng;
   private Button btnFre;
   private  Button btnRest;
   private  TextView edttxt;
   private TextView edtResult;
   private TextView edtwrong;
   private ImageView  imgView;

    private AssetManager assetManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setApplicationLocale("en");
        setContentView(R.layout.activity_main);

        assetManager = getAssets();

        final TextView tView = (TextView) findViewById(R.id.txtWelcome);
        edttxt = (TextView) findViewById(R.id.edtLetter);
        edtResult =  (TextView) findViewById(R.id.txtresult);
        edtwrong =  (TextView) findViewById(R.id.txtwguess);

        btnEng = (Button) findViewById(R.id.btnEnglish);
        btnFre = (Button) findViewById(R.id.btnFrench);
        btnRest = (Button) findViewById(R.id.btnrestart);

        imgView = (ImageView) findViewById(R.id.imageView);

        Game game = new Game(this);
        game.play();

        String wrongGuess = getResources().getString(R.string.txt_nwg) +"0";
        edtwrong.setText(wrongGuess);

        btnEng.setOnClickListener((v)-> {
          //  edttxt.setText(edttxt.getText()+ "0");
            recreate();
            setApplicationLocale("en");
           // setContentView(R.layout.activity_main);
        });

        btnFre.setOnClickListener((v)-> {
          //  edttxt.setText(edttxt.getText()+ "1");
           recreate();
            setApplicationLocale("fr");
          //  setContentView(R.layout.activity_main);
        });

        btnRest.setOnClickListener((v)-> {

            edttxt.setText("");
            edtResult.setText("");
            edtwrong.setText("");
            int resID = getResources().getIdentifier("image0", "drawable", getPackageName());
            Drawable res = getResources().getDrawable(resID);
            imgView.setImageDrawable(res);
           // imgView.setBackgroundResource(R.drawable.image0);
            game.play();
        });



        edttxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    String msg ="";
                    String play_won ="";
                    String comp_won ="";

                    String text = v.getText().toString();
                  int num =  game.checkLetter(text);
                  if((game.checkGameStatus()== GameStatus.PLAYING) && (game.getwrongGuesses()<7))
                  {
                      if (num > 0)
                      {

                          msg = getResources().getString(R.string.exc_msg);
                          Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          setWrongGuessTxt(game.getwrongGuesses());
                          SetWrongGuessImage(game.getwrongGuesses());
                          msg = getResources().getString(R.string.wrong_msg);
                          Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                      }

                      edttxt.setText("");
                  }
                  else
                  {
                      if(game.getStatus() == GameStatus.PLAYERWON)
                      {
                           play_won = getResources().getString(R.string.res_st) +" "
                                     + getResources().getString(R.string.res_playerwon);

                          edtResult.setText(play_won);
                      }
                      else
                      {
                          setWrongGuessTxt(game.getwrongGuesses());
                          SetWrongGuessImage(game.getwrongGuesses());
                          comp_won =  getResources().getString(R.string.res_st) +" "
                                     + getResources().getString(R.string.res_compwon);

                          edtResult.setText(comp_won);
                      }
                  }
                   return true;
                }
                return false;
            }
        });


    }

    private void SetWrongGuessImage(int numofwrongguesses)
    {

      //  String imagePath = "image" + numofwrongguesses + ".jpg";//1st solution
        String imagePath = "image" + numofwrongguesses ;//2nd solution
        int resID = getResources().getIdentifier(imagePath , "drawable", getPackageName());

      //  int resId = getResources().getIdentifier(imagePath,"drawable",getPackageName());
      //  imgView.setBackgroundResource(resID);
        Drawable res = getResources().getDrawable(resID);
        imgView.setImageDrawable(res);

     /*   try
        {
            InputStream ins = assetManager.open(imagePath);
        //    Bitmap bitmap = BitmapFactory.decodeStream(ins);
          //  imgView.setImageBitmap(bitmap);

            Drawable d = Drawable.createFromStream(ins , null);

            // set image to ImageView
           imgView.setImageDrawable(d);

        }
        catch (IOException e)
        {
            String msg = e.getMessage();
        }*/
    }

    private void setWrongGuessTxt( int numofwrongguesses)
    {
       String wrongtxt ="";
        wrongtxt = getResources().getString(R.string.txt_nwg);
        wrongtxt = wrongtxt + " " +numofwrongguesses;
        edtwrong.setText(wrongtxt);
    }


    private void setApplicationLocale(String locale)
    {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);

    }
}