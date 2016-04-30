package com.stupidbird.actor.example.cluster.transform;

import java.io.Serializable;

public interface TransformationMessage {
	@SuppressWarnings("serial")
	public static class TransformationJob implements Serializable {
		private final String text;

		public TransformationJob(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	@SuppressWarnings("serial")
	public static class TransformationResult implements Serializable {
		private final String text;

		public TransformationResult(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return "TransformationResult(" + text + ")";
		}
	}

	@SuppressWarnings("serial")
	public static class JobFailed implements Serializable {
		private final String reason;
		private final TransformationJob job;

		public JobFailed(String reason, TransformationJob job) {
			this.reason = reason;
			this.job = job;
		}

		public String getReason() {
			return reason;
		}

		public TransformationJob getJob() {
			return job;
		}
	}

	public static final String BACKEND_REGISTRATION = "BackendRegistration";
}
