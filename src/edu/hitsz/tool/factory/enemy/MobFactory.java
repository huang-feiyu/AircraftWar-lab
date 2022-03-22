package edu.hitsz.tool.factory.enemy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

public class MobFactory implements EnemyFactory {

    @Override
    public AbstractAircraft CreateEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }
}