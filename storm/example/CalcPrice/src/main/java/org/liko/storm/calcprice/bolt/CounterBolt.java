package org.liko.storm.calcprice.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import java.util.Map;

public class CounterBolt extends BaseRichBolt {
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        print("CounterBolt : prepare");
    }

    @Override
    public void execute(Tuple tuple) {
        String string = tuple.getString(0);
        System.out.println("===> execute : " + string);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("liko"));
    }

    private void print(String msg) {
        System.out.println("-------------------Liko output start--------------------");
        System.out.println("====> " + msg);
        System.out.println("-------------------Liko output end--------------------");
    }
}
