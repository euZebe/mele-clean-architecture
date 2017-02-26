package me.euzebe.mele.usecase.generatedraw;

import javaslang.control.Option;

public interface IGenerateDraws {
    public Option<Draw> generateDraw(String... participantsName);
}
