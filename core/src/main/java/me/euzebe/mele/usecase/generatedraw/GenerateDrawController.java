package me.euzebe.mele.usecase.generatedraw;

import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.control.Option;
import me.euzebe.mele.spi.DrawsCatalog;

public class GenerateDrawController implements IGenerateDraws {

    private DrawsCatalog drawsCatalog;

    public GenerateDrawController(DrawsCatalog drawsCatalog) {
        this.drawsCatalog = drawsCatalog;
    }

	@Override
	public Option<Draw> generateDraw(Seq<String> names, Seq<NotAllowedConstraint> constraints) {
		Option<Draw> optionalDraw = DrawWithRandom.generateWithoutSelfAssignment(names, constraints);

		optionalDraw.peek(drawsCatalog::add);

		return optionalDraw;
	}

    @Override
    public Option<Draw> generateDraw(String... participantsName) {
		return this.generateDraw(List.of(participantsName), List.empty());
    }

}
