package me.euzebe.mele.infrastructure;

import java.util.ArrayList;
import java.util.List;

import me.euzebe.mele.spi.DrawsCatalog;
import me.euzebe.mele.usecase.generatedraw.Draw;

public class DrawMemoryRepository implements DrawsCatalog {

    List<Draw> draws = new ArrayList<>();

    @Override
    public Draw add(Draw draw) {
        draws.add(draw);
        return draw;
    }

    @Override
    public Draw[] getAll() {
        Draw[] allDrawsAsArray = new Draw[draws.size()];
        return draws.toArray(allDrawsAsArray);
    }

}
