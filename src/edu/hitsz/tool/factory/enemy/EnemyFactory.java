package edu.hitsz.tool.factory.enemy;

import edu.hitsz.aircraft.AbstractAircraft;

public interface EnemyFactory {
    AbstractAircraft CreateEnemy(int locationX, int locationY, int speedX, int speedY, int hp);
}