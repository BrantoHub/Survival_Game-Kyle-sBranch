package managers;

import gameObjects.GameObject;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class GameAudio implements Runnable {

    private File music;
    private AudioInputStream audioInput;
    private Clip clip;
    private Boolean loop;

    //Overloaded constructor that takes in the audio path to start the music as well as determining if the music should keep looping
    public GameAudio(String Path, Boolean pLoop)
    {
        music = new File(Path);
        Thread audioloop = new Thread(this);
        audioloop.start();
        loop = pLoop;
    }
    public void EndAudio()
    {
        clip.stop();
    }
    //Audio Thread
    //Keeps running to play the music in the background as you play the game
    @Override
    public void run() {
        //music = new File("Audio/TangoMusic (1).wav");
        try {
            audioInput = AudioSystem.getAudioInputStream(music);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            if(loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);



        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Music messed up " + ex.getMessage());
        }
    }
}
