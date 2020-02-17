package bsu.rfe.java.group10.lab6.anufriev;

import java.net.InetSocketAddress;

public class Peer {
    private final String name;
    private final InetSocketAddress address;

    public Peer(String name, InetSocketAddress address) {
        this.name = name;
        this.address = address;

    }
    public InetSocketAddress getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
