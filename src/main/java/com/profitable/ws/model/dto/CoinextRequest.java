package com.profitable.ws.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CoinextRequest {
	
	private int OMSId;
	
	private int InstrumentId;
	
	private long FromDate;
	
	private long Interval;
	
	private long IncludeLastCount;
	
	private long Depth;
	
	private long AccountId;
	
}
