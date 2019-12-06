package com.profitable.ws.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Entity
@Table(name = "tbl_order")
public class Order {
	
	@Id
	private String id;
	
	private String code;
	
	@JsonProperty("type")
	@JsonDeserialize(using = OrderTypeJsonDeserializer.class)
	@Enumerated(EnumType.STRING)
	private OrderType type;
	
	@JsonProperty("subtype")
	@JsonDeserialize(using = OrderSubtypeJsonDeserializer.class)
	@Enumerated(EnumType.STRING)
	private OrderSubtype subtype;
	
	@JsonProperty("requested_amount")
	@Column(name = "requested_amount")
	private BigDecimal requestedAmount;
	
	@JsonProperty("remaining_amount")
	@Column(name = "remaining_amount")
	private BigDecimal remainingAmount;
	
	@JsonProperty("unit_price")
	@Column(name = "unit_price")
	private BigDecimal unitPrice;
	
	@JsonProperty("status")
	@JsonDeserialize(using = OrderStatusJsonDeserializer.class)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@JsonProperty("pair_code")
	private String pair;
	
	@JsonProperty("total_price")
	@Column(name = "total_price")
	private BigDecimal totalPrice;
	
	@JsonProperty("request_price")
	@Column(name = "request_price")
	private BigDecimal requestPrice;
	
	private BigDecimal amount;
	
	@JsonProperty("user_code")
	@Column(name = "user_code")
	private String userCode;
	
	@JsonProperty("executed_amount")
	@Column(name = "executed_amount")
	private BigDecimal executedAmount;
	
	@JsonProperty("remaining_price")
	@Column(name = "remaining_price")
	private BigDecimal remainingPrice;
	
	@JsonProperty("create_date")
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@JsonProperty("update_date")
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	
	@Transient
	private Exchange exchange;
	
	private boolean executed;
	
	@Transient
	private List<OrderFee> fees;
	
}
