package me.euzebe.mele.usecase.generatedraw;

import static org.assertj.core.api.Assertions.assertThat;
import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.control.Option;

import org.junit.Test;

public class DrawTest {

    @Test
    public void should_return_true_when_all_values_are_distinct() {
        assertThat(DrawWithRandom.validate(List.of("Niobé", "Ezechiel", "Eusèbe"))).isTrue();
    }

	@Test
	public void should_return_false_when_names_are_only_empty_strings() {
		assertThat(DrawWithRandom.validate(List.of("", "", ""))).isFalse();
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
        assertThat(draw.isDefined()).isTrue();
		assertThat(draw.get().getId()).isNotNull();
		assertThat(draw.get().getParticipants().size()).isEqualTo(2);
    }

	@Test
	public void check_EMPTY_draw() {
		assertThat(Draw.EMPTY.getId()).isNull();
		assertThat(Draw.EMPTY.getParticipants()).isNull();
	}

	@Test
	public void should_return_validation_failure_when_duplications_in_participants_names() {
		String aName = "Bidule";
		assertThat(DrawWithRandom.validate(List.of(aName, aName))).isFalse();
	}
	
	@Test
	public void should_return_false_when_a_nonexistent_name_is_passed_in_a_constraint() {
		Seq<NotAllowedConstraint> constraints = List.of(new NotAllowedConstraint("Niobé", "Ernest"), new NotAllowedConstraint("Eusèbe", "Ernest"));
		Seq<String> names = List.of("Ezechiel", "Titouan");
		assertThat(DrawWithRandom.constraintsHaveOnlyExistingNames(constraints, names)).isFalse();
	}

	@Test
	public void should_return_true_when_all_constraint_names_are_contained_in_participants() {
		Seq<NotAllowedConstraint> constraints = List.of(new NotAllowedConstraint("Niobé", "Ernest"), new NotAllowedConstraint("Eusèbe", "Ernest"));
		Seq<String> names = List.of("NIobé", "Eusèbe", "Ernest");
		assertThat(DrawWithRandom.constraintsHaveOnlyExistingNames(constraints, names)).isTrue();
	}
}
