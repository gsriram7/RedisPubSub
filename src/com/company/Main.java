package com.company;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main {

    public static final String CHANNEL_NAME = "commonChannel";

    public static void main(String[] args) throws Exception {

        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        final JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        final Jedis subscriberJedis = jedisPool.getResource();
        MessageBroker messageBroker = new MessageBroker(true);
        final Subscriber subscriber = new Subscriber(messageBroker);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Subscribing to \"commonChannel\". This thread will be blocked.");
                    subscriberJedis.subscribe(subscriber, CHANNEL_NAME);
                    System.out.println("Subscription ended.");
                } catch (Exception e) {
                    System.out.println("Subscribing failed. " + e);
                }
            }
        });
        thread.start();

        final Jedis publisherJedis = jedisPool.getResource();

        Publisher publisher = new Publisher(CHANNEL_NAME);
        publisher.publish();

        while (messageBroker.getToContinue()) {
        }
        thread.join();

        jedisPool.returnResource(subscriberJedis);
        jedisPool.returnResource(publisherJedis);
    }
}