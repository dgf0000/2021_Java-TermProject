package script;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerController extends KeyAdapter implements Runnable{
    //움직일 플레이어
    Player player;

    //이미지 움직임을 위한 설정
    int playerImgSpeed = 10;
    int playerImageCount = 0;
    int platerInvincibleCount = 0;
    int curPlayerImage = 1;
    int lastKey = 1;    //플레이어가 바라보고있는 곳, 마지막으로 받은 키, 메뉴얼을 위한 값

    //이미지 설정값
    boolean isFirstKeyUp = true;
    boolean isFirstKeyDown = true;
    boolean isFirstKeyLeft = true;
    boolean isFirstKeyRight = true;

    //키처리
    boolean isStop = true;
    boolean keyUp = false;
    boolean keyDown = false;
    boolean keyLeft = false;
    boolean keyRight = false;
    boolean keyShift = false;
    boolean keyEsc = false;

    //플레이어 초기화 생성자
    public PlayerController(Player player) {
        this.player = player;
    }
    
    //키 true
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP :
                this.keyUp = true;		
                this.keyDown = false;	//하나의 키를 제외한 모든 키  false, 즉 위 아래 왼쪽 오른쪽한 방향으로만 이동 가능
                this.keyLeft = false;
                this.keyRight = false;
                break;
            case KeyEvent.VK_DOWN :
                this.keyDown = true;
                this.keyUp = false;
                this.keyLeft = false;
                this.keyRight = false;
                break;
            case KeyEvent.VK_LEFT :
                this.keyLeft = true;
                this.keyUp = false;
                this.keyDown = false;
                this.keyRight = false;
                break;
            case KeyEvent.VK_RIGHT :
                this.keyRight = true;
                this.keyUp = false;
                this.keyDown = false;
                this.keyLeft = false;
                break;
            case KeyEvent.VK_SHIFT :
                this.keyShift = true;
                break;
            case KeyEvent.VK_ESCAPE :
                this.keyEsc = true;
                break;
        }
    }
    //키 false
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP :
                this.keyUp = false;
                this.isFirstKeyUp = true;
                this.lastKey = 0;
                break;
            case KeyEvent.VK_DOWN :
                this.keyDown = false;
                this.isFirstKeyDown = true;
                this.lastKey = 1;
                break;
            case KeyEvent.VK_LEFT :
                this.keyLeft = false;
                this.isFirstKeyLeft = true;
                this.lastKey = 2;
                break;
            case KeyEvent.VK_RIGHT :
                this.keyRight = false;
                this.isFirstKeyRight = true;
                this.lastKey = 3;
                break;
            case KeyEvent.VK_SHIFT :
                this.keyShift = false;
                break;
        }
    }
    //이동 
    public void keyProcess() {
        if(this.keyUp) {
            if(player.ypos >= 80)
                this.player.ypos -= player.Speed;      
        }
        if(this.keyDown){
            if(player.ypos < 780)
                this.player.ypos += player.Speed;
        }
        if(this.keyLeft){
            if(player.xpos > 30)
                this.player.xpos -= player.Speed;  
        }
        if(this.keyRight){
            if(player.xpos < 560)
                this.player.xpos += player.Speed;   
        }
        //삼항연산자
        this.player.Speed = (this.keyShift) ? player.dashSpeed : player.normalSpeed;
        this.playerImgSpeed = (this.keyShift) ? 6 : 10;
    }
    //든 방향키 통제
    public void AllKeyFalse() {
        this.keyUp = false;
        this.keyDown = false;
        this.keyLeft = false;
        this.keyRight = false;
        this.keyShift = false;
    }
    // Player 이미지(움직임) 처리 
    public void PlayerImage() {
        //무적 중일때 깜빡임
        if(player.isInvincible) {
            if(keyUp) {
                if(playerImageCount >= (playerImgSpeed / 2)) {	//플레이어 이미지 카운터가 증가하면서 이미지스피드의 1/2일때 투명, 1일 때 걷는 이미지
                    player.playerImage = player.playerImginvincible; //이미지 사이사이에 투명 이미지 추가
                }   
                if(playerImageCount >= playerImgSpeed) {
                    if(curPlayerImage == 4)
                        curPlayerImage = 0;
                    player.playerImage = player.playerImgBack[curPlayerImage++];       
                    playerImageCount = 0;
                }
                playerImageCount++;
            }
            else if(keyDown) {
                if(playerImageCount >= (playerImgSpeed / 2)) {
                    player.playerImage = player.playerImginvincible;
                }   
                if(playerImageCount >= playerImgSpeed) {
                    if(curPlayerImage == 4)
                        curPlayerImage = 0;
                    player.playerImage = player.playerImgFront[curPlayerImage++];       
                    playerImageCount = 0;
                }
                playerImageCount++;
            }
            else if(keyLeft) {
                if(playerImageCount >= (playerImgSpeed / 2)) {
                    player.playerImage = player.playerImginvincible;
                }   
                if(playerImageCount >= playerImgSpeed) {
                    if(curPlayerImage == 4)
                        curPlayerImage = 0;
                    player.playerImage = player.playerImgLeft[curPlayerImage++];       
                    playerImageCount = 0;
                }
                playerImageCount++;
            }
            else if(keyRight) {
                if(playerImageCount >= (playerImgSpeed / 2)) {
                    player.playerImage = player.playerImginvincible;
                }   
                if(playerImageCount >= playerImgSpeed) {
                    if(curPlayerImage == 4)
                        curPlayerImage = 0;
                    player.playerImage = player.playerImgRight[curPlayerImage++];       
                    playerImageCount = 0;
                }
                playerImageCount++;
            }
            else {	//가만히 있을 때 무적 이미지
                if(playerImageCount >= (playerImgSpeed / 2)) {
                    player.playerImage = player.playerImginvincible;
                }   
                if(playerImageCount >= playerImgSpeed) { //기본 걷기 이미지
                    switch(lastKey) {
                        case 0:
                            player.playerImage = player.playerImgBack[0];
                            break;
                        case 1:
                            player.playerImage = player.playerImgFront[0];
                            break;
                        case 2:
                            player.playerImage = player.playerImgLeft[0];
                            break;
                        case 3:
                            player.playerImage = player.playerImgRight[0];
                            break;
                    }  
                    playerImageCount = 0;
                }
                playerImageCount++;
            }
        }
        //기본 이미지(움직임)
        else if(keyUp) {
            if(isFirstKeyUp) {
                player.playerImage = player.playerImgBack[0];
                isFirstKeyUp = false;
            }
            if(playerImageCount >= playerImgSpeed) {
                if(curPlayerImage == 4)
                    curPlayerImage = 0;
                player.playerImage = player.playerImgBack[curPlayerImage++];       
                playerImageCount = 0;
            }
            playerImageCount++;
        }
        else if(keyDown) {
            if(isFirstKeyDown) {
                player.playerImage = player.playerImgFront[0];
                isFirstKeyDown = false;
            }
            if(playerImageCount >= playerImgSpeed) {
                if(curPlayerImage == 4)
                    curPlayerImage = 0;
                player.playerImage = player.playerImgFront[curPlayerImage++];       
                playerImageCount = 0;
            }
            playerImageCount++;
        } 
        else if(keyLeft) {
            if(isFirstKeyLeft) {
                player.playerImage = player.playerImgLeft[0];
                isFirstKeyLeft = false;
            }
            if(playerImageCount >= playerImgSpeed) {
                if(curPlayerImage == 4)
                    curPlayerImage = 0;
                player.playerImage = player.playerImgLeft[curPlayerImage++];       
                playerImageCount = 0;
            }
            playerImageCount++;
        } 
        else if(keyRight) {
            if(isFirstKeyRight) {
                player.playerImage = player.playerImgRight[0];
                isFirstKeyRight = false;
            }
            if(playerImageCount >= playerImgSpeed) {
                if(curPlayerImage == 4)
                    curPlayerImage = 0;
                player.playerImage = player.playerImgRight[curPlayerImage++];       
                playerImageCount = 0;
            }
            playerImageCount++;
        }
        //무적 이미지 풀어주기(플레이어 사라짐 방지)
        else if(!player.isInvincible) {       
            if(keyUp) {
                player.playerImage = player.playerImgBack[0];
            }
            else if(keyDown) {
                player.playerImage = player.playerImgFront[0];
            }
            else if(keyLeft) {
                player.playerImage = player.playerImgLeft[0]; 
            }
            else if(keyRight) {
                player.playerImage = player.playerImgRight[0];
            }
            else {
                switch(lastKey) {
                    case 0:
                        player.playerImage = player.playerImgBack[0];
                        break;
                    case 1:
                        player.playerImage = player.playerImgFront[0];
                        break;
                    case 2:
                        player.playerImage = player.playerImgLeft[0];
                        break;
                    case 3:
                        player.playerImage = player.playerImgRight[0];
                        break;
                } 
            }
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                if(!isStop) {
                    keyProcess();
                    PlayerImage();
                }    
                Thread.sleep(10);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}