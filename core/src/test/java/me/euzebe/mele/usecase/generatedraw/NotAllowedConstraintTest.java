package me.euzebe.mele.usecase.generatedraw;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NotAllowedConstraintTest {

    private static final String OWNER = "Julia";
    private static final String ASSIGNEE = "Niobé";

    @Test
    public void test() {
        NotAllowedConstraint constraint = new NotAllowedConstraint(ASSIGNEE, OWNER);
        String string = constraint.toString();
        assertThat(string).contains(ASSIGNEE);
        assertThat(string).contains(OWNER);
    }

}
