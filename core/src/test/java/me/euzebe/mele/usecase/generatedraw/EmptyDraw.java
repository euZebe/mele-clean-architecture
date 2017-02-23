package me.euzebe.mele.usecase.generatedraw;

import javaslang.collection.List;

public class EmptyDraw implements Draw {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public List<Participant> getParticipants() {
        return List.empty();
    }

}