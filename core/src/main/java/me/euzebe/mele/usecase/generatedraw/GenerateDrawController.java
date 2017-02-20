package me.euzebe.mele.usecase.generatedraw;

import javaslang.control.Option;

public class GenerateDrawController implements GenerateDraw {

    @Override
    public Option<Draw> generateDraw(String... participantsName) {
        System.out.println("generating draw...");
        return Draw.generateWith(participantsName);
    }

}
