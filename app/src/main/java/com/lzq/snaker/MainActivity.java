package com.lzq.snaker;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements  OnClickListener{

	GameView gameView;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (GameView) findViewById(R.id.gv);
        gameView.setOnSnakerListener(new GameView.OnSnakerListener() {
            @Override
            public void onEnd() {
                Toast.makeText(MainActivity.this,"游戏结束",Toast.LENGTH_SHORT).show();
                gameView.startGame();
            }
        });
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

}
