package me.euzebe.mele.usecase.generatedraw;

import javaslang.collection.Seq;
import javaslang.control.Option;

public interface IGenerateDraws {
    public Option<Draw> generateDraw(String... participantsName);

	Option<Draw> generateDraw(Seq<String> names, Seq<NotAllowedConstraint> constraints);
}
