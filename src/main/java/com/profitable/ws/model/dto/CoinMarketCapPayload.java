package com.profitable.ws.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CoinMarketCapPayloadDeserializer.class)
public class CoinMarketCapPayload<D> {
	
	public static class Status {
		private LocalDateTime timestamp;
		
		@JsonProperty("error_code")
		private Long errorCode;
		
		@JsonProperty("error_message")
		private String errorMessage;
		
		private int elapsed;
		
		@JsonProperty("credit_count")
		private int creditCount;

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public Long getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(Long errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public int getElapsed() {
			return elapsed;
		}

		public void setElapsed(int elapsed) {
			this.elapsed = elapsed;
		}

		public int getCreditCount() {
			return creditCount;
		}

		public void setCreditCount(int creditCount) {
			this.creditCount = creditCount;
		}
		
	}
	
	private List<D> data;
	
	private Status status;

	public List<D> getData() {
		return data;
	}

	public void setData(List<D> data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
