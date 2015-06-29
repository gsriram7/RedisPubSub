package com.company;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Publisher {

    private final Jedis publisherJedis;

    private final String channel;
    private Thread[] threads = new Thread[5];

    public Publisher(String channel) {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        final JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        this.publisherJedis = jedisPool.getResource();
        this.channel = channel;
    }

    public void publish() {

        System.out.println("Type your message (quit for terminate)");
        for (Thread thread : threads) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i <= 2; i++) {
                        publisherJedis.publish(channel, "Message " + i);
                    }
                    publisherJedis.publish(channel, "quit");
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
