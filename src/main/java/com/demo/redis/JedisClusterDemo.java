package com.demo.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class JedisClusterDemo {

    public static void simpleExample() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        //Jedis Cluster will attempt to discover cluster nodes automatically
        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 30001));
        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        jc.set("foo", "bar111");
        System.out.println(jc.get("foo"));
    }

    public static void main(String[] args) {
        simpleExample();
    }
}
