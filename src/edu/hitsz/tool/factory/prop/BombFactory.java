package edu.hitsz.tool.factory.prop;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;

public class BombFactory implements PropFactory {
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
