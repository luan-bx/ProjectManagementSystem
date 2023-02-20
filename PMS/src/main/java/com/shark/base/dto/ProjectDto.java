package com.shark.base.dto;

import java.sql.Timestamp;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

	private String name;
	private String companyName;
	private Timestamp submitTime;
	private String statusName;
}
