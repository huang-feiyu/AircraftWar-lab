package edu.hitsz.tool.factory.prop;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;

public class BloodFactory implements  PropFactory {
    @Override
    public AbstractProp createProp(int locationX, int locationY) {
        return new BloodProp(locationX, locationY, 0, 8);
    }
}
