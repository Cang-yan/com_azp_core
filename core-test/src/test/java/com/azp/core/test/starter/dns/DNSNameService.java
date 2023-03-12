package com.azp.core.test.starter.dns;

import sun.net.spi.nameservice.NameService;
import sun.net.spi.nameservice.NameServiceDescriptor;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class DNSNameService implements NameServiceDescriptor, NameService {

    private sun.net.spi.nameservice.dns.DNSNameService dnsNameService;

    @Override
    public NameService createNameService() throws Exception {
        dnsNameService =  new sun.net.spi.nameservice.dns.DNSNameService();
        return this;
    }

    @Override
    public String getProviderName() {
        return "local-dns-provider";
    }

    @Override
    public String getType() {
        return "dns";
    }

    @Override
    public InetAddress[] lookupAllHostAddr(String host) throws UnknownHostException {
        return "dev.center.horsecoder.com".equals(host) ||
                "dev.gateway.horsecoder.com".equals(host) ||
                "dev.database.horsecoder.com".equals(host) ?
                InetAddress.getAllByName("127.0.0.1") :
                dnsNameService.lookupAllHostAddr(host);
    }

    @Override
    public String getHostByAddr(byte[] bytes) throws UnknownHostException {
        return dnsNameService.getHostByAddr(bytes);
    }
}
