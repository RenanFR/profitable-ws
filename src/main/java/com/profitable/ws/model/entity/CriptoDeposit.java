package com.profitable.ws.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriptoDeposit extends Deposit {
	private String code;
	private String hash;
}
