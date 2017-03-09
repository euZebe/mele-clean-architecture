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
        Option<Draw> draw = DrawWithRandom.generateWith(List.of("Niobé", "Ernest"), List.empty());
        assertThat(draw.isDefined()).isTrue();
        assertThat(draw.get().getId()).isNotNull();
        assertThat(draw.get().getParticipants().size()).isEqualTo(2);
    }

    @Test
    public void check_EMPTY_draw() {
        assertThat(Draw.EMPTY.getId()).isNull();
        assertThat(Draw.EMPTY.getParticipants()).isNull();
        assertThat(Draw.EMPTY.getGenerationResult()).isNull();
    }

    @Test
    public void should_return_validation_failure_when_duplications_in_participants_names() {
        String aName = "Bidule";
        assertThat(DrawWithRandom.validate(List.of(aName, aName))).isFalse();
    }

    @Test
    public void should_return_false_when_a_nonexistent_name_is_passed_in_a_constraint() {
		Seq<NotAllowedConstraint> constraints = List.of(new NotAllowedConstraint("Niobé", "Ernest"), //
				new NotAllowedConstraint("Ezechiel", "Ernest"));
		Seq<String> names = List.of("Ezechiel", "Titouan");
        assertThat(DrawWithRandom.constraintsHaveOnlyExistingNames(constraints, names)).isFalse();
    }

    @Test
    public void should_return_true_when_all_constraint_names_are_contained_in_participants() {
        Seq<NotAllowedConstraint> constraints = List.of(new NotAllowedConstraint("Niobé", "Ernest"),
                new NotAllowedConstraint("Eusèbe", "Ernest"));
		Seq<String> names = List.of("Niobé", "Eusèbe", "Ernest");
        assertThat(DrawWithRandom.constraintsHaveOnlyExistingNames(constraints, names)).isTrue();
    }

    @Test
    public void should_consider_constraints() {
        Option<Draw> draw = DrawWithRandom.generateWith(List.of("Niobé", "Ernest", "Eusèbe"), //
                List.of(new NotAllowedConstraint("Niobé", "Ernest"), //
                        new NotAllowedConstraint("Ernest", "Eusèbe"), //
                        new NotAllowedConstraint("Eusèbe", "Niobé") //
                ));

        assertThat(draw).isNotEmpty();

		Participant participantNiobe = draw.get().getParticipants() //
				.filter(p -> p.getName().equals("Niobé")).head();
		assertThat(participantNiobe.getAssigned()).isNotEqualTo("Ernest");
		Participant participantErnest = draw.get().getParticipants() //
				.filter(p -> p.getName().equals("Ernest")).head();
		assertThat(participantErnest.getAssigned()).isNotEqualTo("Eusèbe");
		Participant participantEusebe = draw.get().getParticipants() //
				.filter(p -> p.getName().equals("Eusèbe")).head();
		assertThat(participantEusebe.getAssigned()).isNotEqualTo("Niobé");
    }

    @Test
    public void should_not_make_self_assignments() {
        Option<Draw> draw = DrawWithRandom.generateWithoutSelfAssignment(List.of("Niobé", "Ernest", "Eusèbe"), List.empty());

        assertThat(draw).isNotEmpty();
        draw.get().getParticipants().forEach(p -> {
            assertThat(p.getAssigned()).isNotNull();
            assertThat(p.getAssigned()).isNotEqualTo(p.getName());
        });
    }

    @Test
    public void should_return_an_empty_optional_if_no_solution_is_found() {
        Option<Draw> draw = DrawWithRandom.generateWithoutSelfAssignment(List.of("Niobé"), List.empty());
        DrawGenerationResult generationResult = draw.get().getGenerationResult();
        assertThat(generationResult.isSolutionFound()).isFalse();
        assertThat(generationResult.getMessage()).isNotEmpty();
        System.out.println(draw.get());
    }

}
