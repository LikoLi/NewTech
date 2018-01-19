package org.liko.storm.calcprice.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class BondPriceSpout extends BaseRichSpout {
    private BufferedReader bufferedReader;
    private SpoutOutputCollector collector;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        try {
            print("Open BondPriceSpount...");
            String path = (String) map.get("path");
            bufferedReader = new BufferedReader(new FileReader(path));
            collector = spoutOutputCollector;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {
        try {
            String line;
            while ((line = bufferedReader.readLine()) == null) {
                Thread.sleep(1000);
            }
            print("BondPriceSpout : nextTuple : line : " + line);
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                collector.emit(new Values(String.valueOf(c)));
                System.out.println("===> EMIT : " + String.valueOf(c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
