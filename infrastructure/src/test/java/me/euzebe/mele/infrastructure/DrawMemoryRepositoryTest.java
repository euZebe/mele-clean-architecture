package me.euzebe.mele.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import javaslang.collection.List;
import me.euzebe.mele.usecase.generatedraw.Draw;
import me.euzebe.mele.usecase.generatedraw.Participant;

public class DrawMemoryRepositoryTest {

    DrawMemoryRepository repository = new DrawMemoryRepository();

    @Test
    public void testAdd() {
        int initialSize = repository.draws.size();
        repository.add(new EmptyDraw());
        assertThat(repository.draws.size()).isEqualTo(initialSize + 1);
    }

    @Test
    public void testGetAll() {
        EmptyDraw aDraw = new EmptyDraw();
        EmptyDraw anotherDraw = new EmptyDraw();
        repository.draws.add(aDraw);
        repository.draws.add(anotherDraw);
        assertThat(repository.getAll()).contains(aDraw, anotherDraw);
        assertThat(repository.getAll().length).isEqualTo(2);
    }


    private class EmptyDraw implements Draw {

        @Override
        public String getId() {
            return null;
        }

        @Override
        public List<Participant> getParticipants() {
            return null;
        }

    }

}
