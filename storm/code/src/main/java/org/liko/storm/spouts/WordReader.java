package org.liko.storm.spouts;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class WordReader implements IRichSpout {
    private SpoutOutputCollector collector;
    private FileReader fileReader;
    private boolean completed = false;
    private TopologyContext context;

    @Override
    public boolean isDistributed() {
        return false;
    }

    /**
     * 我们将创建一个文件并维持一个collector对象
     */
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        try {
            this.context = topologyContext;
            this.fileReader = new FileReader(map.get("wordsFile").toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.collector = spoutOutputCollector;
    }

    @Override
    public void close() {

    }

    /**
     * 这个方法做的唯一的一件事情就是分发文件中的文本行
     */
    @Override
    public void nextTuple() {
        // 这个方法会不断的被调用, 知道整个文件都读完了, 我们将等待返回.
        if (completed) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        String str;
        // 创建Reader
        BufferedReader reader = new BufferedReader(fileReader);
        try {
            // 读所有的文本行
            while ((str = reader.readLine()) != null) {
                // 按行发布一个新值
                this.collector.emit(new Values(str), str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            completed = true;
        }
    }

    @Override
    public void ack(Object msgId) {
        System.out.println("OK:" + msgId);
    }

    @Override
    public void fail(Object msgId) {
        System.out.println("FAIL:" + msgId);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("line"));
    }
}
