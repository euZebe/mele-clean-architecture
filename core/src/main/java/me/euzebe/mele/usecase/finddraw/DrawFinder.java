package me.euzebe.mele.usecase.finddraw;

import me.euzebe.mele.spi.DrawsCatalog;
import me.euzebe.mele.usecase.generatedraw.Draw;

public class DrawFinder implements ListDraws {

    private DrawsCatalog catalog;

    public DrawFinder(DrawsCatalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public Draw[] getAll() {
        return catalog.getAll();
    }

}
