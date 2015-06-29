package com.company;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

    private MessageBroker messageBroker;

    public Subscriber(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void onMessage(String channel, String message) {
        System.out.println(message);
        System.out.println("Message received. Channel: " + channel + ", Msg: " + message);
        if ("quit".equals(message)) {
            unsubscribe();
            System.out.println("Unsubing");
            messageBroker.setToContinue(false);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("Unsubing222");
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }
}
