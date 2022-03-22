package edu.hitsz.tool.factory.prop;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;

public class BloodFactory implements  PropFactory {
    @Override
    public AbstractProp CreateProp(int locationX, int locationY, int speedX, int speedY) {
        return new BloodProp(locationX, locationY, speedX, speedY);
    }
}