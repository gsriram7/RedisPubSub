package com.company;

public class MessageBroker {
    private boolean toContinue;

    public MessageBroker(boolean toContinue) {
        this.toContinue = toContinue;
    }

    synchronized public void setToContinue(boolean toContinue) {
        this.toContinue = toContinue;
    }

    synchronized public boolean getToContinue() {
        return toContinue;
    }
}
