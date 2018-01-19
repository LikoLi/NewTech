package org.liko.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import org.liko.storm.bolts.WordCounter;
import org.liko.storm.bolts.WordNormalizer;
import org.liko.storm.group.ModuleGrouping;
import org.liko.storm.spouts.WordReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class TopologyMain {
    public static void main(String[] args) throws InterruptedException, IOException {
        URL resource = TopologyMain.class.getClassLoader().getResource(".");
        String path = resource.getPath() + "words.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        System.out.println(br.readLine());
        //定义拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());
        builder.setBolt("word-normalizer", new WordNormalizer()).customGrouping("word-reader", new ModuleGrouping());
        builder.setBolt("word-counter", new WordCounter(), 2).fieldsGrouping("word-normalizer", new Fields("word"));

        //配置
        Config config = new Config();
        config.put("wordsFile", path);
        config.setDebug(true);

        // 运行拓扑
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topologie", config, builder.createTopology());

        Thread.sleep(8000);
        cluster.killTopology("Getting-Started-Topologie");
    }
}
