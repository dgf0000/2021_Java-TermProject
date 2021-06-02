package script;

public class Main {
    public static void main(String[] args){ //게임매니저에게 매개변수로 넘겨주기 위해 생성
        Player player = new Player();
        PlayerController playerController = new PlayerController(player);
        EnemySpawn enemySpawn = new EnemySpawn(player);
        Item item = new Item(player);  
        GameManager gameManager = new GameManager(player, playerController, item, enemySpawn);       
    }
}