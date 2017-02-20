package me.euzebe.mele.usecase.generatedraw;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;

import javaslang.collection.List;
import javaslang.collection.Stream;
import javaslang.control.Option;
import lombok.Getter;

public class Draw {

    @Getter
    private List<Participant> participants;

    @Getter
    private String id;

    private Draw() {
        id = UUID.randomUUID().toString();
    }

    private Draw(String... participantsName) {
        this();
        this.participants = Stream.of(participantsName) //
                .map(name -> new Participant(name)) //
                .toList();

    }

    /**
     * Generate a Draw if participants are valid
     *
     * @param participantsName - name of each participant to the draw
     * @return an empty Option if <code>participantsName</code> is invalid, an Option containing the draw otherwise.
     */
    public static Option<Draw> generateWith(String... participantsName) {
        return validate(participantsName) //
                ? Option.of(new Draw(participantsName)) //
                : Option.none();
    }

    /**
     *
     * @param participantsName
     * @return <code>false</code> if participantsName is empty or contains duplicates, <code>true</code> otherwise.
     */
    static boolean validate(String... participantsName) {
        return ArrayUtils.isNotEmpty(participantsName) //
                && inputHasNoDuplicate(participantsName);
    }

    private static boolean inputHasNoDuplicate(String... participantsName) {
        return participantsName.length == List.of(participantsName) //
                .map(name -> name.toLowerCase()) //
                .distinct() //
                .size();
    }

    @Override
    public String toString() {
        String foldedParticipants = participants //
                .map(p -> "\t" + p.getAssignmentToString()) //
                .intersperse(",\n") //
                .fold("", String::concat);

        return new StringBuilder() //
                .append("Draw ").append(id) //
                .append("[\n") //
                .append(foldedParticipants) //
                .append("\n]") //
                .toString();
    }
}
