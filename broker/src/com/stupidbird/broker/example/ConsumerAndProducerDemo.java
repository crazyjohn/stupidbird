package com.stupidbird.broker.example;

public class ConsumerAndProducerDemo {

	public static void main(String[] args) {
		boolean isAsync = false;
		String topic = "Sex";
		// producer
		Producer producerThread = new Producer(topic, isAsync);
		producerThread.start();
		// consumer
		Consumer consumerThread = new Consumer(topic);
		consumerThread.start();
	}

}
