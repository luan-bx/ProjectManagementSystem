package com.shark.backendSystem.entity;

import java.sql.Timestamp;

import com.shark.project.entity.PaymentTermEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyNameEntity {

	private int id;
	private String companyName1;
	private String companyName2;
	private Timestamp created;
	private Timestamp updated;
}
