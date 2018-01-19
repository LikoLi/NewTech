package org.liko.storm.calcprice;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import org.liko.storm.calcprice.bolt.CounterBolt;
import org.liko.storm.calcprice.spout.BondPriceSpout;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new BondPriceSpout());
        builder.setBolt("bolt", new CounterBolt()).shuffleGrouping("spout");

        Config config = new Config();
        config.setDebug(true);
        config.put("path", Main.class.getResource("/words.txt").getPath());

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology", config, builder.createTopology());

    }
}
