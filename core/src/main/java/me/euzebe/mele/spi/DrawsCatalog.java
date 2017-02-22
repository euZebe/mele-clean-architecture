package me.euzebe.mele.spi;

import me.euzebe.mele.usecase.generatedraw.Draw;

public interface DrawsCatalog {
    public Draw add(Draw draw);

    public Draw[] getAll();
}
