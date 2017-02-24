package me.euzebe.mele.usecase.generatedraw;

import static org.assertj.core.api.Assertions.assertThat;
import javaslang.collection.List;
import javaslang.control.Option;

import org.junit.Test;

public class DrawTest {

    @Test
    public void should_return_true_when_all_values_are_distinct() {
        assertThat(DrawWithRandom.validate(List.of("Niobé", "Ezechiel", "Eusèbe"))).isTrue();
    }

    @Test
    public void should_return_false_when_the_input_list_is_empty() {
        assertThat(DrawWithRandom.validate(List.empty())).isFalse();
    }

    @Test
    public void should_return_false_when_there_is_a_duplicate_in_the_input() {
        assertThat(DrawWithRandom.validate(List.of("Niobé", "Ezechiel", "Eusèbe", "Niobé"))).isFalse();
    }

    @Test
    public void test_toString() {
        Option<Draw> draw = DrawWithRandom.generateWith("Niobé", "Ernest");
        System.out.println(draw.get());
        assertThat(draw.isDefined()).isTrue();
		assertThat(draw.get().getId()).isNotNull();
		assertThat(draw.get().getParticipants().size()).isEqualTo(2);
    }

	@Test
	public void check_EMPTY_draw() {
		assertThat(Draw.EMPTY.getId()).isNull();
		assertThat(Draw.EMPTY.getParticipants()).isNull();
	}
}
