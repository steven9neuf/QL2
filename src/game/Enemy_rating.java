package game;

import modele.Player;

public class Enemy_rating {
	private static int enemy0_rate = 120;
	private static int enemy1_rate = 60;
	private int[] enemyRate = { enemy0_rate, enemy1_rate };
	private int[] enemyLife = { 2, 3 };

	public int[] getEnemyRate() {
		return enemyRate;
	}

	public int[] getEnemyLife() {
		return enemyLife;
	}

	/**
	* @throws IOException  
	*/
	public void upgradeEnemies(Player thisPlayer) {
		if (enemyRate[0] - 15 >= WindowGame.minimum_enemy_rate)
			enemyRate[0] -= 15;
		else
			enemyRate[0] = WindowGame.minimum_enemy_rate;
		if (enemyRate[1] - 10 >= WindowGame.minimum_enemy_rate)
			enemyRate[1] -= 10;
		else
			enemyRate[1] = WindowGame.minimum_enemy_rate;
		if (thisPlayer.getLevel() == WindowGame.max_level) {
			enemyLife[0] += 1;
			enemyLife[1] += 1;
		}
	}
}