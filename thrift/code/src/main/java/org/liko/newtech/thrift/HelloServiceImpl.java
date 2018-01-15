package org.liko.newtech.thrift;

import org.apache.thrift.TException;

public class HelloServiceImpl implements Hello.Iface {
    @Override
    public String helloString(String para) throws TException {
        return "Hello World!";
    }

    @Override
    public int helloInt(int para) throws TException {
        return 0;
    }

    @Override
    public boolean helloBoolean(boolean para) throws TException {
        return para;
    }

    @Override
    public void helloVoid() throws TException {

    }

    @Override
    public String helloNull() throws TException {
        return null;
    }
}
