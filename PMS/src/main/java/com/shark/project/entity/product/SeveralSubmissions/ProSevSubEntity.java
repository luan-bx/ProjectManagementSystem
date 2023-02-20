package com.shark.project.entity.product.SeveralSubmissions;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProSevSubEntity {

	private int id;
	private int proSevSubId;
	private Timestamp created;
	private Timestamp updated;
}