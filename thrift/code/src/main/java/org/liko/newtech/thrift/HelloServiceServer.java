package org.liko.newtech.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

public class HelloServiceServer {
    // 启动 Thrift 服务器
    public static void main(String[] args) {
        try {
            System.out.println("Server starting...");
            TProcessor processor = new Hello.Processor<Hello.Iface>(new HelloServiceImpl());

            //简单的单线程服务模型, 一般用于测试
            TServerSocket serverTransport = new TServerSocket(7911);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(processor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
