package com.company;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Subs {

    private final String channelName;
    private final Jedis subscriberJedis;
    private MessageBroker messageBroker;
    private final Subscriber subscriber;
    private Thread[] threads = new Thread[5];

    public Subs(String channelName) {
        this.channelName = channelName;
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        final JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        subscriberJedis = jedisPool.getResource();
        messageBroker = new MessageBroker(true);
        subscriber = new Subscriber(messageBroker);
    }

    public void subscriber() {
        for (Thread thread : threads) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Subscribing to \"commonChannel\". This thread will be blocked.");
                        subscriberJedis.subscribe(subscriber, channelName);
                        System.out.println("Subscription ended.");
                    } catch (Exception e) {
                        System.out.println("Subscribing failed. " + e);
                    }
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
