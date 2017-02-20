package me.euzebe.mele.usecase.generatedraw;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import javaslang.control.Option;

public class DrawTest {

    @Test
    public void should_return_true_when_all_values_are_distinct() {
        assertThat(Draw.validate("Niobé", "Ezechiel", "Eusèbe")).isTrue();
    }

    @Test
    public void should_return_false_when_the_input_list_is_empty() {
        assertThat(Draw.validate()).isFalse();
    }

    @Test
    public void should_return_false_when_there_is_a_duplicate_in_the_input() {
        assertThat(Draw.validate("Niobé", "Ezechiel", "Eusèbe", "Niobé")).isFalse();
    }

    @Test
    public void test_toString() {
        Option<Draw> draw = Draw.generateWith("Niobé", "Ernest");
        System.out.println(draw.get());
        assertThat(draw.isDefined()).isTrue();
    }
}
