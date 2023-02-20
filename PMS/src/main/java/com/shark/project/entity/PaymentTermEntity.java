package com.shark.project.entity;

import java.sql.Timestamp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTermEntity {
	
	private int id;
	private String name;
	private Timestamp created;
	private Timestamp updated;
}
