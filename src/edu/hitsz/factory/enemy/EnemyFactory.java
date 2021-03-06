package edu.hitsz.factory.enemy;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * @author Huang
 */
public interface EnemyFactory {
    /**
     * 创建敌机
     */
    AbstractAircraft createEnemy();
}
