package script;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio implements Runnable {
    AudioInputStream stream;	//스트림
    File file;					//생성되는 파일
    Clip clip;					//클립
    String fileName;			//현재 파일 이름
    boolean isPlaying = false;	//실행중?
    
    public Audio(String fileName) {	//매개변수의 이름에 따라 file이 다르게 생성
        this.fileName = fileName;
        switch(fileName) {
            case "BGMSound":
                this.file = new File("audio/KartRiderMusic-Mansion-Dance-Battle.wav");
                break;
            case "clickSound":
                this.file = new File("audio/DM-CGS-32.wav");
                break;
            case "PlayerClashEnemySound":
                this.file = new File("audio/DM-CGS-41.wav");
                break;
            case "PlayerClashItemSound":
                this.file = new File("audio/DM-CGS-28.wav");
                break;
            case "GameClearSound":
                this.file = new File("audio/DM-CGS-18.wav");
                break;
            case "GameOverSound":
                this.file = new File("audio/DM-CGS-43.wav");
                break;
        }
        try {
            stream = AudioSystem.getAudioInputStream(file);
            this.clip = AudioSystem.getClip();
            this.clip.open(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //스레드 호출로 플레이
    @Override
    public void run() {
        try {
            this.clip.start();
        } catch(Exception e) {    
            e.printStackTrace();
        }
        
    }
}