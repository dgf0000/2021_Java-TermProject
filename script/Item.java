package script;

import java.awt.Image;

public class Item extends Object implements Runnable{
    Player player;				//플레이어

    boolean isCrash = false;	//플레이어와 충돌?
    boolean isStop = false;		//움직임 통제

    int itemScore = 0;			//현재 아이템 스코어

    //코인 이미지 관련 설정
    Image itemimage[] = new Image[4];
    final int imageSpeed = 5;
    int itemimageCount = 0;
    int curitemImage = 0;
    
    //충돌거리를 위한 변수
    double dis1, dis2;
    double deltaX, deltaY;
    //플레이어 값을 받기위한 매개변수
    public Item(Player player) {
        this.player = player;
        this.xpos = 300;	//초기위치 값X
        this.ypos = 150;	//초기위치 값Y
        this.collider = 16f;	//코인의 콜라이더(반지름 길이)
        this.image = imageTool.getImage("Image/Object/Coin_0.png");	//코인 이미지 초기화
        for(int i = 0; i < 4; i++) {
            this.itemimage[i] = imageTool.getImage("Image/Object/Coin_"+i+".png");
        }
    }
    //아이템 위치 변경
    public void Respawn() {
        int spawnPointX = (int)(Math.random() * (550 - 50)) + 50;
        int spawnPointY = (int)(Math.random() * (750 - 80)) + 80;

        this.xpos = spawnPointX;
        this.ypos = spawnPointY;
    }
    //아이템 이미지(움직임)
    public void ItemImage() { //스레드로 카운트가 돌아가면서 이미지스피드와 값이 같아지면 1씩증가해서 이미지 교체
        if(itemimageCount == imageSpeed) {
            if(curitemImage == 4)
            curitemImage = 0;
            this.image = itemimage[curitemImage++];       
            itemimageCount = 0;
        }
        itemimageCount++;
    }
    //충돌함수
    public void Clash(Player player) {
        this.deltaX = (player.xpos - this.xpos);
        this.deltaY = (player.ypos - this.ypos);
        this.dis1 = Math.sqrt( deltaX * deltaX  +  deltaY * deltaY );
        this.dis2 = player.collider + this.collider;
        //충돌
        if(dis1 < dis2) {    
            player.GetItemNum++;
            itemScore += 10;
            Respawn();
            ClashSound();
        }  
    }
    //충돌 사운드
    public void ClashSound() {
        Audio crashSound = new Audio("PlayerClashItemSound");
        Thread ItemTh = new Thread(crashSound);
        ItemTh.start();
    }
    //스레드
    @Override
    public void run() {
        try {
            while(true){
                ItemImage();
                Clash(player);
                Thread.sleep(10);
            }
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
}