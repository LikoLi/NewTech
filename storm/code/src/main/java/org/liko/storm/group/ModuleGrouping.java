package org.liko.storm.group;

import backtype.storm.grouping.CustomStreamGrouping;
import backtype.storm.tuple.Tuple;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModuleGrouping implements CustomStreamGrouping, Serializable {
    int numTasks = 0;
    @Override
    public void prepare(int i) {
        this.numTasks = i;
    }

    @Override
    public List<Integer> taskIndices(Tuple tuple) {
        List<Integer> boltIds = new ArrayList<>();
        String value = tuple.getString(0);
        if (StringUtils.isNotEmpty(value)) {
            boltIds.add(value.charAt(0) % numTasks);
        } else {
            boltIds.add(0);
        }
        return boltIds;
    }
}
