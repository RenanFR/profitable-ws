package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.profitable.ws.model.dto.OrderStatusJsonDeserializer;
import com.profitable.ws.model.dto.OrderSubtypeJsonDeserializer;
import com.profitable.ws.model.dto.OrderTypeJsonDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
	
	private String id;
	
	private String code;
	
	@JsonProperty("type")
	@JsonDeserialize(using = OrderTypeJsonDeserializer.class)
	private OrderType type;
	
	@JsonProperty("subtype")
	@JsonDeserialize(using = OrderSubtypeJsonDeserializer.class)
	private OrderSubtype subtype;
	
	@JsonProperty("requested_amount")
	private BigDecimal requestedAmount;
	
	@JsonProperty("remaining_amount")
	private BigDecimal remainingAmount;
	
	@JsonProperty("unit_price")
	private BigDecimal unitPrice;
	
	@JsonProperty("status")
	@JsonDeserialize(using = OrderStatusJsonDeserializer.class)
	private OrderStatus status;
	
	@JsonProperty("pair_code")
	private String pair;
	
	@JsonProperty("total_price")
	private BigDecimal totalPrice;
	
	@JsonProperty("request_price")
	private BigDecimal requestPrice;
	
	private BigDecimal amount;
	
	@JsonProperty("user_code")
	private String userCode;
	
	@JsonProperty("executed_amount")
	private BigDecimal executedAmount;
	
	@JsonProperty("remaining_price")
	private BigDecimal remainingPrice;
	
	@JsonProperty("create_date")
	private LocalDateTime createDate;
	
	@JsonProperty("update_date")
	private LocalDateTime updateDate;
	
	private Exchange exchange;
	
	private boolean executed;
	
	private List<OrderFee> fees;
	
}
