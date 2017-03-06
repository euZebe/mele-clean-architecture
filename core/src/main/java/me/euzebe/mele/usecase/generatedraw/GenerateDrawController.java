package me.euzebe.mele.usecase.generatedraw;

import javaslang.collection.Seq;
import javaslang.control.Option;
import me.euzebe.mele.spi.DrawsCatalog;

public class GenerateDrawController implements IGenerateDraws {

    private DrawsCatalog drawsCatalog;

    public GenerateDrawController(DrawsCatalog drawsCatalog) {
        this.drawsCatalog = drawsCatalog;
    }
    
	public Option<Draw> generateDraw(Seq<String> names, Seq<NotAllowedConstraint> constraints) {
		Option<Draw> optionalDraw = DrawWithRandom.generateWith(names, constraints);

		optionalDraw.peek(drawsCatalog::add);

		return optionalDraw;
	}

    @Override
    public Option<Draw> generateDraw(String... participantsName) {
        System.out.println("generating draw...");
        Option<Draw> optionalDraw = DrawWithRandom.generateWith(participantsName);

        optionalDraw.peek(drawsCatalog::add);

        return optionalDraw;
    }

}
