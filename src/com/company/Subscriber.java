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
        if (messageBroker.toStop(message)) {
            unsubscribe();
            System.out.println("Unsubing");
        }
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("Unsubing222");
    }
}
