package com.example.spotifyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Home_screen extends AppCompatActivity {
    private Button play_btn;
    private Button prev_melody_btn;
    private Button next_melody_btn;
    private Button repeat_btn;
    private SeekBar seek_music;
    private TextView txt_sname,txt_sstart,txt_sstop;
    String sname;
    public static final String EXTRA_NAME="song_name";
    boolean is_play=true;
    static MediaPlayer mediaPlayer;
    ArrayList<File> mySongs;
    int position;
    Thread update_seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        play_btn = (Button) findViewById(R.id.play_btn);
        prev_melody_btn = (Button) findViewById(R.id.prev_melody_btn);
        next_melody_btn = (Button) findViewById(R.id.next_melody_btn);
        repeat_btn = (Button) findViewById(R.id.repeat_btn);
        txt_sname = findViewById(R.id.text_song);
        txt_sstart = findViewById(R.id.textstart);
        txt_sstop = findViewById(R.id.textstop);
        seek_music = findViewById(R.id.seekbar);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        if (mediaPlayer !=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String song_Name = i.getStringExtra("songname");
        position = bundle.getInt("pos",0);
        txt_sname.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname = mySongs.get(position).getName();

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        update_seekbar = new Thread(){
            @Override
            public void run() {
                int duration = mediaPlayer.getDuration();
                int current_position = 0;

                while(current_position<duration) {
                    try{
                        sleep(500);
                        current_position = mediaPlayer.getCurrentPosition();
                        seek_music.setProgress(current_position);
                    }catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        seek_music.setMax(mediaPlayer.getDuration());
        update_seekbar.start();
        seek_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String end_time = time(mediaPlayer.getDuration());
        txt_sstop.setText(end_time);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String current_time = time(mediaPlayer.getCurrentPosition());
                txt_sstart.setText(current_time);
                handler.postDelayed(this,delay);
            }
        },delay);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_play){
                    view.setBackgroundResource(R.drawable.pause);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                    mediaPlayer.start();
                }else{
                    if(mediaPlayer != null)
                    {
                        mediaPlayer.pause();
                        view.setBackgroundResource(R.drawable.play);
                    }

                }
                is_play = !is_play;

            }
        });

        next_melody_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%mySongs.size());
                Uri uri = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                sname = mySongs.get(position).getName();
                txt_sname.setText(sname);
                mediaPlayer.start();
                play_btn.setBackgroundResource(R.drawable.pause);

            }
        });

        prev_melody_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0)?(mySongs.size()-1):(position-1);
                Uri uri = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                sname = mySongs.get(position).getName();
                txt_sname.setText(sname);
                mediaPlayer.start();
                play_btn.setBackgroundResource(R.drawable.pause);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next_melody_btn.performClick();
            }
        });
    }

    public String time(int dduration) {
        String tim = "";
        int min = dduration/1000/60;
        int sec = dduration/1000%60;

        tim +=min+":";

        if(sec<10){
            tim+="0";
        }
        tim+=sec;
        return tim;
    }

}