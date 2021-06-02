package script;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Manual extends JDialog implements WindowListener{
    //메뉴얼 이미지 초기화
    ImageIcon ManualImage[] = new ImageIcon[6];

    //버튼 이미지
    ImageIcon StartBtn[] = new ImageIcon[3];
    ImageIcon ReturnBtn[] = new ImageIcon[3];

    //메뉴얼 구성목록
    JLabel imageLabel;
	JButton okButton;
    int getItemNum = 0;	//엔딩 변경을 위한 현재 코인값
    boolean isfirst = true;	//처음 클리어 이후 엔딩 변경을 위한 값

    //스탑 보정 컨트롤러 값
    boolean keyUp = false;
    boolean keyDown = false;
    boolean keyLeft = false;
    boolean keyRight = false;
    boolean keyShift = false;
    boolean iscloseManual = false;

    int x, y;	//게임 창 중앙에 표시하기 위한 값
    int score;	//스코어

    public Manual(JFrame frame, String title, int x, int y) { 
		super(frame,title, false);	//모달리스 다이얼로그

        for(int i = 0; i < 6; i++) {	//엔딩카드
            ManualImage[i] = new ImageIcon("Image/Card/Manual_Card_"+i+".png");
        }

        this.imageLabel = new JLabel(ManualImage[0]);	//메뉴얼 이미지 초기화

        //스타트 버튼 이미지
        StartBtn[0] = new ImageIcon("Image/Ui/UI Start Btn On.png");
        StartBtn[1] = new ImageIcon("Image/Ui/UI Start Btn It.png");
        StartBtn[2] = new ImageIcon("Image/Ui/UI Start Btn Down.png");
        
        //리턴 버튼 이미지
        ReturnBtn[0] = new ImageIcon("Image/Ui/UI Btn On.png");
        ReturnBtn[1] = new ImageIcon("Image/Ui/UI Btn It.png");
        ReturnBtn[2] = new ImageIcon("Image/Ui/UI Btn Down.png");

        //설정값
        this.x = x;	
        this.y = y;
        setResizable(false);    
        this.setLocation(x + 100, y + 100); //게임창의 위치 x, y 를 받아 중앙에 표시
        setLayout(new FlowLayout());
        setSize(400, 600);
        this.addWindowListener(this);	//창을 x로 껐을 때를 위한 보정

        //버튼 이미지 설정값
        this.okButton = new JButton(StartBtn[0]);
        this.okButton.setRolloverIcon(StartBtn[1]);
        this.okButton.setPressedIcon(StartBtn[2]);
        //버튼 이미지만 표시
        this.okButton.setBorderPainted(false); 
        this.okButton.setFocusPainted(false); 
        this.okButton.setContentAreaFilled(false); 
        
        //창에 추가
        add(this.imageLabel);
		add(this.okButton);	
		
		//버튼 액션 리스너 
		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                iscloseManual = true; //다이얼 로그를 꺼도 게임구동이 안되는 버그를 막기위한 보정 값
                ClickSound();
                setVisible(false);
			}
		});
		//키를 누르고 스탑을 하고 게임창으로 돌아갈때 키값을 다시 주고 받기 위한 함수
        this.okButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
                if(KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    iscloseManual = true;
                    ClickSound();
                    setVisible(false);
                }
                if(KeyEvent.VK_UP == e.getKeyCode()) {
                    keyUp = true;
                }
                if(KeyEvent.VK_DOWN == e.getKeyCode()) {
                    keyDown = true;
                }
                if(KeyEvent.VK_LEFT == e.getKeyCode()) {
                    keyLeft = true;
                }
                if(KeyEvent.VK_RIGHT == e.getKeyCode()) {
                    keyRight = true;
                }
                if(KeyEvent.VK_SHIFT == e.getKeyCode()) {
                    keyShift = true;
                }
			}
            public void keyReleased(KeyEvent e) {
                if(KeyEvent.VK_UP == e.getKeyCode()) {
                    keyUp = false;
                }
                if(KeyEvent.VK_DOWN == e.getKeyCode()) {
                    keyDown = false;
                }
                if(KeyEvent.VK_LEFT == e.getKeyCode()) {
                    keyLeft = false;
                }
                if(KeyEvent.VK_RIGHT == e.getKeyCode()) {
                    keyRight = false;
                }
                if(KeyEvent.VK_SHIFT == e.getKeyCode()) {
                    keyShift = false;
                }
			}
		});
	}
    //게임클리어 UI변경
    public void GameClearUi() {
        if(score >= 300)
            this.imageLabel.setIcon(ManualImage[5]);
        else if(isfirst) {
            this.imageLabel.setIcon(ManualImage[1]);
            this.isfirst = false;
        }     
        else
            this.imageLabel.setIcon(ManualImage[3]);
        this.okButton.setIcon(ReturnBtn[0]);
        this.okButton.setRolloverIcon(ReturnBtn[1]);
        this.okButton.setPressedIcon(ReturnBtn[2]);
    }
    //게임오버 UI변경
    public void GameOverUi() {
        if(getItemNum < 8)
            this.imageLabel.setIcon(ManualImage[2]);
        else
            this.imageLabel.setIcon(ManualImage[4]);

        this.okButton.setIcon(ReturnBtn[0]);
        this.okButton.setRolloverIcon(ReturnBtn[1]);
        this.okButton.setPressedIcon(ReturnBtn[2]);
    }
    //게임스탑 UI변경
    public void GameStopUi() {
        this.imageLabel.setIcon(ManualImage[0]);

        this.okButton.setIcon(StartBtn[0]);
        this.okButton.setRolloverIcon(StartBtn[1]);
        this.okButton.setPressedIcon(StartBtn[2]);
    }

    public void ClickSound() {
        Audio clickSound = new Audio("clickSound"); 
        Thread enemySpawnTh = new Thread(clickSound);
        enemySpawnTh.start();
    }
    //X로 창을 때 이벤트
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {
        iscloseManual = true;
    }
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}