package script;

import java.awt.*;
//Item, Enemy의 부모 오브젝트 5장 상속을 위해 구현
public abstract class Object {
    int respawnPosition;	//생성되는 위치
    int xpos;				//x좌표
    int ypos;				//y좌표
    float speed;			//스피드(이미지 or 움직임)
    float collider;			//콜라이더
    Toolkit imageTool = Toolkit.getDefaultToolkit();	//이미지킷
    Image image;			//이미지
}