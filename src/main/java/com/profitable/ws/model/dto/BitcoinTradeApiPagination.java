package com.profitable.ws.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BitcoinTradeApiPagination {
	
	@JsonProperty("total_pages")
	private Integer totalPages;
	
	@JsonProperty("current_page")
	private Integer currentPage;
	
	@JsonProperty("page_size")
	private Integer pageSize;
	
	@JsonProperty("registers_count")
	private Integer registersCount;

}
