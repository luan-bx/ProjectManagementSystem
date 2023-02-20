package com.shark.project.entity.design;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EleEngineerEntity {

	private int id;
	private String name;
	private Timestamp created;
	private Timestamp updated;
}
