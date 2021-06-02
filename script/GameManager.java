package script;

import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*; 

public class GameManager extends JFrame {
    Player player;						//플레이어
    PlayerController playerController;	//플레이어 컨트롤러
    Item item;							//아이템(동전)
    EnemySpawn enemySpawn;				//적(총알)
    Manual manual;						//메뉴얼(다이얼로그)
    Audio backGroundMusic;				//오디오
    
    int Score = 0;						//현재스코어
    int isDie = 1;						//UI교체후 게임오버가 되게 만들어주는 보정값 
    int isClear = 1;					//UI교체후 게임클리어가 되게 만들어주는 보정값 

    
    Toolkit imageTool = Toolkit.getDefaultToolkit();	//이미지킷
    Image lifeImage[] = new Image[2];					//현재 체력UI
    Image coinImage;									//현재 모은 동전UI
    Image coinBackGroundImage;							//코인 배경 이미지(틀)					
    int GetItemnum = 0;									//현재 모은 동전 개수

    //이미지 버퍼를 위한 세팅
    Image buffImg;
    Graphics buffG;
    
    ImageIcon BackGroundImage;	//배경이미지(맵)

    public GameManager(Player player, PlayerController playerController, Item item, EnemySpawn enemySpawn) {
        //설정값
        setTitle("DCU의 총알피하기"); 
        setSize(600,830); 
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //초기화
        this.BackGroundImage = new ImageIcon("Image/Map/BackGround.png");
        this.player = player;
        this.playerController = playerController;
        this.item = item;
        this.enemySpawn = enemySpawn;
        
        //UI세팅
        lifeImage[0] = imageTool.getImage("Image/Ui/DcuIcon.png");
        lifeImage[1] = imageTool.getImage("Image/Ui/DcuIcon_Die.png");
        coinImage = imageTool.getImage("Image/Ui/CoinIcon.png");
        coinBackGroundImage = imageTool.getImage("Image/Ui/CoinIconBackGround.png");
       
        backGroundMusic = new Audio("BGMSound"); //BGM
        manual = new Manual(this, "test", this.getLocation().x, this.getLocation().y); //메뉴얼(다이얼로그)
  
        setVisible(true);
              
        //게임 배경음악
        Thread backMusicTh = new Thread(backGroundMusic);
        backMusicTh.start();

        //게임 시작 전 설명
        manual.setVisible(true);
        SetControllerToManul();

        //플레이어 컨트롤러 쓰레드
        addKeyListener(this.playerController);
        Thread Move = new Thread(this.playerController);
        Move.setDaemon(true);
        Move.start();

        //아이템(동전) 컨트롤러 쓰레드
        Thread ItemTh = new Thread(this.item);
        ItemTh.setDaemon(true);
        ItemTh.start(); 

        //적(총알) 컨트롤러 쓰레드
        Thread EnemyMove = new Thread(this.enemySpawn);
        EnemyMove.setDaemon(true);
        EnemyMove.start();
    }
    //BGM연속재생
    public void continuousPlay() {
        if(!backGroundMusic.clip.isRunning()) {
            try {
                backGroundMusic.stream = AudioSystem.getAudioInputStream(backGroundMusic.file);
                backGroundMusic.clip = AudioSystem.getClip();
                backGroundMusic.clip.open(backGroundMusic.stream);
                backGroundMusic.clip.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }   
        }
    }
    //모든 움직임 통제
    public void AllStop() {
        this.playerController.isStop = true;
        this.enemySpawn.isStop = true;
        this.item.isStop = true;
    }
    //모든 움직임 통제 해제
    public void AllClear() {
        this.playerController.isStop = false;
        this.enemySpawn.isStop = false;
        this.item.isStop = false;
    }
    //게임클리어
    public void GameClear() {
        ClearSound();
        AllStop();
        manual.score = this.Score;
        manual.GameClearUi(); 
        manual.setVisible(true);
        manual.iscloseManual = true;
        GetItemnum = 0;
    }
    //게임클리어 사운드
    public void ClearSound() {
        Audio clearSound = new Audio("GameClearSound"); 
        Thread enemySpawnTh = new Thread(clearSound);
        enemySpawnTh.start();
    }
    //게임오버
    public void GameOver() {
        GameOverSound();
        AllStop(); 
        manual.getItemNum = 0;
        manual.getItemNum = GetItemnum;
        manual.GameOverUi();
        manual.setVisible(true);
        manual.iscloseManual = true;
    }
    //게임오버 사운드
    public void GameOverSound() {
        Audio clearSound = new Audio("GameOverSound"); 
        Thread enemySpawnTh = new Thread(clearSound);
        enemySpawnTh.start();
    }
    //게임스탑(게임중 ESC를 눌렀을 때)
    public void GameStop() {
        AllStop();
        playerController.keyEsc = false;
        SetManualToController();
        manual.GameStopUi();
        manual.setVisible(true);
        manual.iscloseManual = true;
    }
    //게임스탑 중 다이얼로그 호출 할 때 키값 변경을 위한 함수
    public void SetManualToController() {
        manual.keyUp = playerController.keyUp;
        manual.keyDown = playerController.keyDown;
        manual.keyLeft = playerController.keyLeft;
        manual.keyRight = playerController.keyRight;
    }
    //게임스탑 중 다이얼로그 호출 할 때 키값 변경을 위한 함수
    public void SetControllerToManul() {
        playerController.keyUp = manual.keyUp;
        playerController.keyDown = manual.keyDown;
        playerController.keyLeft = manual.keyLeft;
        playerController.keyRight = manual.keyRight;
    }
    //게임 오브젝트 리셋
    public void ReGame() {
        player.playerReset();
        playerController.AllKeyFalse();
        enemySpawn.AllEnemyReset();
        enemySpawn.enemys.setSize(10);
        enemySpawn.addEnemyCount = 0;
        enemySpawn.enemysScore = 0;
        item.itemScore = 0;
        item.Respawn();
        isDie = 1;
        isClear = 1;    
        GetItemnum = 0;
    }
    //게임 중 설정
    public void GameSettting() {
        this.Score = enemySpawn.enemysScore + item.itemScore;
        if(playerController.keyEsc) {
            GameStop();
        }
        if(player.Health == 0) {
            if(isDie == 0) { //
                GameOver();
                isDie = 2;
            }
            else if(isDie == 1)
                isDie = 0 ;
        }
        if(player.GetItemNum == 10) {
            if(isClear == 0) { // 
                GameClear();
                isClear = 2;
            }
            else if(isClear == 1)
                isClear = 0;
        }
        if(manual.iscloseManual && !manual.isVisible()) {
            manual.iscloseManual = false;
            SetControllerToManul();
            AllClear();
            if(player.Health == 0 || player.GetItemNum == 10) {
                ReGame();
            }
        }
        continuousPlay();
    }
    //그래픽 더블 버퍼링
    @Override
    public void paint(Graphics g) {
        buffImg = createImage(getWidth(),getHeight());
        buffG = buffImg.getGraphics(); 
        update(g);
    }
    @Override
    public void update(Graphics g) {
        buffG.clearRect(0, 0, 600, 800);
        buffG.drawImage(BackGroundImage.getImage(), 0, 30, null);
        buffG.drawImage(this.player.playerImage,(this.player.xpos - 27),(this.player.ypos - 32), this);
        //buffG.drawString("●", (this.player.xpos), (this.player.ypos));    //실제 플레이어 포인트
        //buffG.drawImage(this.testImage.Image,this.testImage.xpos,this.testImage.ypos, this);
        GameSettting();		//업데이트에서 설정이 돌아가게 놓았음
        DrawItem(g);		//동전 이미지
        DrawEnemy(g);		//총알 이미지
        DrawUi(g);  		//UI 이미지
        g.drawImage(buffImg,0,0,this); 
        repaint();
        
    }
    //Item 그래픽
    public void DrawItem(Graphics g) {
        buffG.drawImage(item.image, (item.xpos - 4), (item.ypos - 12), this);
    }
    //Enemy 그래픽
    public void DrawEnemy(Graphics g) {
        for(int i = 0; i < this.enemySpawn.enemys.size(); i ++) {
            Enemy enemy = this.enemySpawn.enemys.get(i);
            buffG.drawImage(enemy.image, (enemy.xpos - 4), (enemy.ypos - 12), this);
            //buffG.drawString("●", (enemy.xpos), (enemy.ypos));    //실제 총알 포인트
        }
    }
    //UI 그래픽
    public void DrawUi(Graphics g) {
        for(int i = 0; i < 5; i++) { //플레이어 체력 마다 UI변경
            if(player.Health <= i)
                buffG.drawImage(this.lifeImage[1], 15 + (i*40), 35, this);
            else
                buffG.drawImage(this.lifeImage[0], 15 + (i*40), 35, this);
        }
        buffG.drawString("Score : " + this.Score, 280, 60); //스코어 표시
        buffG.drawImage(this.coinBackGroundImage, 380 , 40, this);	//코인 틀 표시
        this.GetItemnum = player.GetItemNum;	//현재 모은 코인
        
        for(int i = 0; i < this.GetItemnum; i++) {	//모은 코인에 따라 코인 표시
            buffG.drawImage(this.coinImage, 386+ (i * 20), 46, this);
        }
    }
}