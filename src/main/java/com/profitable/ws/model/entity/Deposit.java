package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.profitable.ws.model.dto.DepositStatusDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deposit {
	
	protected BigDecimal amount;
	
	@JsonDeserialize(using = DepositStatusDeserializer.class)
	protected DepositStatus status;
	
	@JsonProperty("create_date")
	protected LocalDateTime createDate;
	
	@JsonProperty("confirmation_date")
	protected LocalDateTime confirmationDate;
	
}
