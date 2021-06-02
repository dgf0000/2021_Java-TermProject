package script;

import java.util.Vector;

public class EnemySpawn implements Runnable{
    Player player;				//충돌을 위해 필요
    Vector<Enemy> enemys;		//Vector를 이용해 총알 생성
    int spawnPoint;				//총알의 위치
    int randomPoint;			//총알의 위치를 램덤하게 배치시키기 위한 변수
    int spawnName;				//총알이름
    int addEnemyCount = 0;		//총알이 추가 되기위한 변수
    int enemysScore = 0;		//총알들의 총 점수
    int sumScore = 0;			//총 점수에 넣어주기 위한 변수
    boolean isCrash = false;	//플레이어와 충돌?
    boolean isStop = true;		//움직임 통제

    //충돌거리를 위한 변수
    double dis1, dis2;
    double deltaX, deltaY;
    //플레이어 위치 값을 받기위한 매개변수
    public EnemySpawn(Player player) {
        this.player = player;
        this.enemys = new Vector<>();
      
        //적생성
        for(int i = 0; i < 10; i ++) {
            spawnPoint = (int)(Math.random() * 31) + 0;
            if(i < 8)
                enemys.add(new Enemy(spawnPoint , 0));
            else   
                enemys.add(new Enemy(spawnPoint , 1));
        }
    }
    //적생성을 위한 테스트함수 모든 리스폰장소에서 나옴
    public void TestEnemy() {
        for(int i = 0; i < 5; i ++) {enemys.add(new Enemy(i , 0));}
        for(int i = 5; i < 10; i ++) {enemys.add(new Enemy(i , 0));}
        for(int i = 10; i < 17; i ++) {enemys.add(new Enemy(i , 0));}
        for(int i = 17; i < 24; i ++) {enemys.add(new Enemy(i , 0));}
        for(int i = 24; i < 32; i ++) {enemys.add(new Enemy(i , 0));}

    }
    //적 증가
    public void AddEnemys(int num) { //램덤한 값으로 위치와 총알 선택
        for(int i = 0; i < num; i ++) {
            spawnPoint = (int)(Math.random() * 31) + 0;
            spawnName = (int)(Math.random() * 9) + 0;
            if(spawnName < 6)							//6할은 빨간색 4할은 파란색 생성
                enemys.add(new Enemy(spawnPoint , 0));
            else
                enemys.add(new Enemy(spawnPoint , 1));
        }
    }
    //아이템 획들 할 때 마다 적 증가함수 호출
    public void CheckPlayerGetItemNum() {
        if(player.GetItemNum  != addEnemyCount) {
            AddEnemys(5);
            addEnemyCount++;
        }
    }
    //움직임
    public void EnemyMove() {
        for(int i = 0; i < this.enemys.size(); i++) {
            if(!isStop) {
                this.enemys.get(i).Move();
            }
            sumScore = this.enemys.get(i).score;	//움직이는 함수에서 스코어도 같이 처리
            enemysScore += sumScore; //
            this.enemys.get(i).score = 0;
            sumScore = 0;
        }
    }
    //모든 총알 위치 초기화
    public void AllEnemyReset() {
        for(int i = 0; i < this.enemys.size(); i++) {
            Enemy enemy = this.enemys.get(i);
            enemy.xpos = 1000;
            enemy.xpos = 1000;
        }
    }
    //충돌함수
    public void Clash(Player player) {
        for(int i = 0; i < this.enemys.size(); i++) {
            deltaX = (player.xpos - enemys.get(i).xpos);
            deltaY = (player.ypos - enemys.get(i).ypos);
            dis1 = Math.sqrt( deltaX * deltaX  +  deltaY * deltaY );
            dis2 = player.collider + enemys.get(i).collider;
            if(player.isInvincible) {
                player.nvincibleCount++;
                if(player.nvincibleCount == player.nvincibleTime) {
                    player.nvincibleCount = 0;
                    player.isInvincible = false;
                }
            }
            //충돌
            if(dis1 < dis2) { 
                if(!(player.isInvincible)) { //무적이 아닐때
                    player.Health--;
                    ClashSound();
                } 
                player.isInvincible = true;
                randomPoint = (int)(Math.random() * 31) + 0; //충돌한 총알은 다시 리스폰
                enemys.get(i).setRespawnPosition(randomPoint);
                enemys.get(i).Respawn(randomPoint);   
            } 
        }
    }
    //충돌 사운드
    public void ClashSound() {
        Audio crashSound = new Audio("PlayerClashEnemySound");
        Thread enemySpawnTh = new Thread(crashSound);
        enemySpawnTh.start();
    }
    //스레드
    @Override
    public void run() {
        try {
            while(true){
                EnemyMove();
                Clash(player);
                CheckPlayerGetItemNum();
                Thread.sleep(10);
            }
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
}