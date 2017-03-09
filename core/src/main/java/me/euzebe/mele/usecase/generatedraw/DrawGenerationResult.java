package me.euzebe.mele.usecase.generatedraw;

import lombok.Getter;

public class DrawGenerationResult {

    @Getter
    private boolean solutionFound;
    @Getter
    private String message;

    DrawGenerationResult(boolean solutionFound, String message) {
        this.solutionFound = solutionFound;
        this.message = message;
    }


}
