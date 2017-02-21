package me.euzebe.mele.entrypoints;

import lombok.Data;

@Data
public class DrawRequest {
	private String[] participants;
	private Constraint[] constraints;
}
