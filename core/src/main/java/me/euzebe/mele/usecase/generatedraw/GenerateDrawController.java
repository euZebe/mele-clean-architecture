package me.euzebe.mele.usecase.generatedraw;

import javaslang.control.Option;
import me.euzebe.mele.spi.DrawsCatalog;

public class GenerateDrawController implements GenerateDraw {

    private DrawsCatalog drawsCatalog;

    public GenerateDrawController(DrawsCatalog drawsCatalog) {
        this.drawsCatalog = drawsCatalog;
    }

    @Override
    public Option<Draw> generateDraw(String... participantsName) {
        System.out.println("generating draw...");
        Option<Draw> optionalDraw = DrawWithRandom.generateWith(participantsName);

        optionalDraw.peek(drawsCatalog::add);

        return optionalDraw;
    }

}
