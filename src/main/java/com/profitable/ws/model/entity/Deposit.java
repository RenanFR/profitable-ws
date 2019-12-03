package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.profitable.ws.model.dto.DepositStatusDeserializer;

public class Deposit {
	
	protected BigDecimal amount;
	
	@JsonDeserialize(using = DepositStatusDeserializer.class)
	protected DepositStatus status;
	
	@JsonProperty("create_date")
	protected LocalDateTime createDate;
	
	@JsonProperty("confirmation_date")
	protected LocalDateTime confirmationDate;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public DepositStatus getStatus() {
		return status;
	}

	public void setStatus(DepositStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(LocalDateTime confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	
}
