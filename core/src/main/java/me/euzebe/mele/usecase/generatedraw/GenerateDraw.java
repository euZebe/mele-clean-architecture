package me.euzebe.mele.usecase.generatedraw;

import javaslang.control.Option;

public interface GenerateDraw {
    public Option<Draw> generateDraw(String... participantsName);
}
