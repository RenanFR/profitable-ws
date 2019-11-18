package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.profitable.ws.model.dto.DepositStatusDeserializer;
import com.profitable.ws.model.dto.MinerFeeTypeDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Withdraw {
	
	private String code;
	
	@JsonProperty("origin_address")
	private String originAddress;
	
	@JsonProperty("destination_address")
	private String destinationAddress;
	
	private BigDecimal amount;
	
	@JsonProperty("miner_fee")
	private BigDecimal minerFee;
	
	@JsonProperty("miner_fee_type")
	@JsonDeserialize(using = MinerFeeTypeDeserializer.class)
	private MinerFeeType minerFeeType;
	
	@JsonProperty("tax_index")
	private BigDecimal taxIndex;
	
	@JsonProperty("tax_index_calculated")
	private BigDecimal taxIndexCalculated;
	
	@JsonProperty("tax_amount")
	private BigDecimal taxAmount;
	
	@JsonDeserialize(using = DepositStatusDeserializer.class)
	private DepositStatus status;
	
	@JsonProperty("create_date")
	private LocalDateTime createDate;
	
	@JsonProperty("update_date")
	private LocalDateTime updateDate;
	
	@JsonProperty("transaction_id")
	private String transactionId;
	
	private String link;

}
