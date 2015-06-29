package com.company;

public class MessageBroker {
    private boolean toContinue;
    private volatile int pubsThread = 5;

    public MessageBroker(boolean toContinue) {
        this.toContinue = toContinue;
    }

    synchronized public boolean getToContinue() {
        return toContinue;
    }

    synchronized public boolean toStop(String message) {
        if (message.equals("quit"))
            pubsThread--;
        if (pubsThread <= 0) {
            toContinue = false;
            return true;
        }
        else
            return false;
    }
}
