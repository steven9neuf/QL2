package main;


import modele.Player;

public class enemy_rating {
	private static int enemy0_rate = 120;
	private static int enemy1_rate = 60;
	private int[] enemy_rate = { enemy0_rate, enemy1_rate };
	private int[] enemy_life = { 2, 3 };

	public int[] getEnemy_rate() {
		return enemy_rate;
	}

	public int[] getEnemy_life() {
		return enemy_life;
	}

	/**
	* @throws IOException  
	*/
	public void upgradeEnemies(Player thisPlayer) {
		if (enemy_rate[0] - 15 >= WindowGame.minimum_enemy_rate)
			enemy_rate[0] -= 15;
		else
			enemy_rate[0] = WindowGame.minimum_enemy_rate;
		if (enemy_rate[1] - 10 >= WindowGame.minimum_enemy_rate)
			enemy_rate[1] -= 10;
		else
			enemy_rate[1] = WindowGame.minimum_enemy_rate;
		if (thisPlayer.getLevel() == WindowGame.max_level) {
			enemy_life[0] += 1;
			enemy_life[1] += 1;
		}
	}
}