package org.liko.newtech.thrift;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class HelloServiceClient {
    /**
     * 调用thrift服务
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 设置调用服务地址为本地, 端口为7911
            TTransport transport = new TSocket("localhost", 7911);
            transport.open();
            // 设置传输协议为 TBinaryProtocal
            TProtocol protocol = new TBinaryProtocol(transport);
            Hello.Client client = new Hello.Client(protocol);
            // 调用服务的helloVoid方法
//            String string = client.helloString("123");
//            System.out.println(string);
            client.helloNull();
            transport.close();
        } catch (TException e) {
            System.out.println(((TApplicationException) e).getType());
            e.printStackTrace();
        }
    }
}
