package edu.hitsz.tool.factory.enemy;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * @author Huang
 */
public interface EnemyFactory {
    AbstractAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp);
}
