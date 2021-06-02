package script;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    //이미지 설정
    Toolkit imageTool = Toolkit.getDefaultToolkit();
    Image playerImage = imageTool.getImage("Image/Player/DcuFront0.png");

    //이미지 움직임을 위한  배열
    Image playerImgFront[] = new Image[4];
    Image playerImgBack[] = new Image[4];
    Image playerImgLeft[] = new Image[4];
    Image playerImgRight[] = new Image[4];
    Image playerImginvincible = imageTool.getImage("Image/Player/Invincible.png");
    
    //플레이어 설정값
    int Health = 5;             	//체력
    int GetItemNum = 0;         	//모은 아이템 갯수
    float Speed = 3f;         		//현재스피드
    float normalSpeed =2.5f;    	//기본스피드
    float dashSpeed =5f;        	//대쉬스피드
    float collider = 26f;       	//콜라이더 크기
    int xpos = 300;             	//초기위치X
    int ypos = 400;             	//초기위치Y
    boolean isInvincible = false;   //무적?
    int nvincibleCount = 0;         //무적카운트를 위한 변수
    int nvincibleTime = 1500;       //무적시간
    
    //플레이어 이미지 얻기
    public Player() {    
        try {
            GetPlayerImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //플레이어 이미지 세팅
    public void GetPlayerImage() throws IOException{
        File suorceFrontImage;
        File suorceBackImage ;
        File suorceLeftImage;
        File suorceRightImage;

        for(int i = 0; i < 4; i++) {
            suorceFrontImage = new File("Image/Player/DcuFront"+i+".png");
            suorceBackImage = new File("Image/Player/DcuBack"+i+".png");
            suorceLeftImage = new File("Image/Player/DcuLeft"+i+".png");
            suorceRightImage = new File("Image/Player/DcuRight"+i+".png");
    
            playerImgFront[i] = ImageIO.read(suorceFrontImage);
            playerImgBack[i] = ImageIO.read(suorceBackImage);
            playerImgLeft[i] = ImageIO.read(suorceLeftImage);
            playerImgRight[i] = ImageIO.read(suorceRightImage);
        }
    }

    //플레이어 리셋
    public void playerReset() {
        this.Health = 5;
        this.GetItemNum = 0;
        this.xpos = 300;
        this.ypos = 400;
        this.playerImage = playerImgFront[0];
    }
}