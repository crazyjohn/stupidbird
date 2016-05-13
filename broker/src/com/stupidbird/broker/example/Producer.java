package com.stupidbird.broker.example;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Producer extends Thread {
	private final KafkaProducer<Integer, String> producer;
	private final String topic;
	private final Boolean isAsync;

	public Producer(String topic, Boolean isAsync) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.119:9092");
		props.put("client.id", "DemoProducer");
		props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
		this.topic = topic;
		this.isAsync = isAsync;
	}

	@Override
	public void run() {
		int messageNo = 1;
		while (true) {
			String messageStr = "Message_" + messageNo;
			long startTime = System.currentTimeMillis();
			if (isAsync) {
				producer.send(new ProducerRecord<>(topic, messageNo, messageStr),
						new DemoCallback(startTime, messageNo, messageStr));
			} else {
				try {
					producer.send(new ProducerRecord<>(topic, messageNo, messageStr)).get();
					System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			++messageNo;
		}
	}
}

class DemoCallback implements Callback {
	private final long startTime;
	private final int key;
	private final String message;

	public DemoCallback(long startTime, int key, String message) {
		this.startTime = startTime;
		this.key = key;
		this.message = message;
	}

	@Override
	public void onCompletion(RecordMetadata metadata, Exception e) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			System.out.println("message(" + key + ", " + message + ") sent to partition(" + metadata.partition() + "),"
					+ "offset(" + metadata.offset() + ") in" + elapsedTime + " ms");
		} else {
			e.printStackTrace();
		}
	}

}
