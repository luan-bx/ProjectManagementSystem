package com.shark.project.entity.product.SeveralSubmissions;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OutSevSubEntity {
	private int id;
	private int outSevSubId;
	private Timestamp created;
	private Timestamp updated;
}
