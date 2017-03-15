package me.euzebe.mele.generatedraw;

import java.util.Map;

import lombok.Data;
import me.euzebe.mele.usecase.generatedraw.DrawGenerationResult;

@Data
public class DrawResponse {
	private String drawID;
	private Map<String, String> assignments;
	private String drawResult;

	public DrawResponse withDrawID(String id) {
		setDrawID(id);
		return this;
	}

	public DrawResponse withAssignments(Map<String, String> hashMap) {
		setAssignments(hashMap);
		return this;
	}

	public DrawResponse withResult(DrawGenerationResult generationResult) {
		drawResult = generationResult == null //
		? DrawGenerationResult.OK //
				: generationResult.getMessage();
		return this;
	}
}
