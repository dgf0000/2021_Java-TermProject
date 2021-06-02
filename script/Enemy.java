package script;

public class Enemy extends Object {
    int enemyName;		//생성되는 총알 이름
    int randomPoint;	//램덤포인트를 받기위한 변수
    int score = 0;		//이 총알의 점수

    public Enemy(int respawnPosition, int enemyName) { //리스폰될 위치, 총알 이름

        this.respawnPosition = respawnPosition;
        this.enemyName = enemyName;
        this.collider = 8f;
        
        switch(enemyName) {
            //빨간색 총알
            case 0:
                this.image = imageTool.getImage("Image/Object/BulletA.png");
                this.speed = 1f;
                break;
            //파란색 총알
            case 1:
                this.image = imageTool.getImage("Image/Object/BulletB.png");
                this.speed = 5f;
                break;
        }
        Respawn(respawnPosition);	//지정된 위치에 총알 생성
    }
    //총알의 위치 리셋을 위한 함수
    public void setRespawnPosition(int respawnPosition) {
        this.respawnPosition = respawnPosition;
    }
    //총알이 생성되는 위치
    public void Respawn(int respawnPosition) { //매개변수의 값에따라 다른 곳에 총알 생성
        switch(respawnPosition) {
            //top 
            case 0:
                this.xpos = 100;
                this.ypos = -10; 
                break;
            case 1:
                this.xpos = 200;
                this.ypos = -10;
                break;
            case 2:
                this.xpos = 300;
                this.ypos = -10;
                break;
            case 3:
                this.xpos = 400;
                this.ypos = -10;
                break;
            case 4:
                this.xpos = 500;
                this.ypos = -10;
                break;

            //bottom
            case 5:
                this.xpos = 100;
                this.ypos = 850;
                break;
            case 6:
                this.xpos = 200;
                this.ypos = 850;
                break;
            case 7:
                this.xpos = 300;
                this.ypos = 850;
                break;
            case 8:
                this.xpos = 400;
                this.ypos = 850;
                break;
            case 9:
                this.xpos = 500;
                this.ypos = 850;
                break;
            //left
            case 10:
                this.xpos = -10;
                this.ypos = 100;
                break;
            case 11:
                this.xpos = -10;
                this.ypos = 200;
                break;
            case 12:
                this.xpos = -10;
                this.ypos = 300;
                break;
            case 13:
                this.xpos = -10;
                this.ypos = 400;
                break;
            case 14:
                this.xpos = -10;
                this.ypos = 500;
                break;
            case 15:
                this.xpos = -10;
                this.ypos = 600;
                break;
            case 16:
                this.xpos = -10;
                this.ypos = 700;
                break;
            //right
            case 17:
                this.xpos = 610;
                this.ypos = 100;
                break;
            case 18:
                this.xpos = 610;
                this.ypos = 200;
                break;
            case 19:
                this.xpos = 610;
                this.ypos = 300;
                break;
            case 20:
                this.xpos = 610;
                this.ypos = 400;
                break;
            case 21:
                this.xpos = 610;
                this.ypos = 500;
                break;
            case 22:
                this.xpos = 610;
                this.ypos = 600;
                break;
            case 23:
                this.xpos = 610;
                this.ypos = 700;
                break;
            //diagonal TL
            case 24:
                this.xpos = 45;
                this.ypos = -10;
                break;
            case 25:
                this.xpos = -10;
                this.ypos = 45;
                break;
            //diagonal TR
            case 26:
                this.xpos = 555;
                this.ypos = -10;
                break;
            case 27:
                this.xpos = 610;
                this.ypos = 45;
                break;
            //diagonal BL
            case 28:
                this.xpos = -10;
                this.ypos = 755;
                break;
            case 29:
                this.xpos = 45;
                this.ypos = 850;
                break;
            //diagonal BT
            case 30:
                this.xpos = 555;
                this.ypos = 850;
                break;
            case 31:
                this.xpos = 610;
                this.ypos = 755;
                break;
        }
    }
    //총알 움직임 구현
    public void Move() { //총알의 시작 위치에 따라 움직임이 다르게 설정 //설정된 위치를 벗어나면 재배치
        //top
        if(this.respawnPosition <= 4) {
            this.ypos += this.speed;
        } 
        //bottom
        else if(this.respawnPosition <= 9) {
            this.ypos -= this.speed;
        }
        //left
        else if(this.respawnPosition <= 16) {
            this.xpos += this.speed;
        }
        //right
        else if(this.respawnPosition <= 23) {
            this.xpos -= this.speed;
        }
        //diagonal
        else if(this.respawnPosition <= 25) {
            this.xpos += this.speed;
            this.ypos += this.speed;
        }
        else if(this.respawnPosition <= 27) {
            this.xpos -= this.speed;
            this.ypos += this.speed;
        }
        else if(this.respawnPosition <= 29) {
            this.xpos += this.speed;
            this.ypos -= this.speed;
        }
        else if(this.respawnPosition <= 31) {
            this.xpos -= this.speed;
            this.ypos -= this.speed;
        }
        //Reset respawn
        if(this.xpos > 900) {
            Relocation();
        }
        //top respawn
        else if(this.ypos > 850) {
            Relocation();
            this.score++;
        }
        //bottom respawn
        else if(this.ypos < -50) {
            Relocation();
            this.score++;
        }
        //left respawn
        else if(this.xpos > 650) {
            Relocation();
            this.score++;
        }
        //right respawn
        else if(this.xpos < -50) {
            Relocation();
            this.score++;
        }  
    }
    //재배치
    public void Relocation() {
        randomPoint = (int)(Math.random() * 31) + 0;
        setRespawnPosition(randomPoint);
        Respawn(respawnPosition);
    }
}