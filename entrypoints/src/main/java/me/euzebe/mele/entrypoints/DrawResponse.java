package me.euzebe.mele.entrypoints;

import java.util.Map;

import lombok.Builder;

@Builder
public class DrawResponse {
	private String drawID;
	private Map<String, String> assignments;
}
