package me.euzebe.mele.usecase.generatedraw;

import javaslang.collection.List;
import javaslang.control.Option;

public class JsonMapper {
    public String toJson(Draw draw) {
        String content = List
                .of("id:" + wrapAttribute(draw.getId()), //
                        "participants:" + wrapParticipants(draw.getParticipants())) //
                .intersperse(", ") //
                .fold("", String::concat);

        return wrapElement(content);
    }

    String wrapAttribute(String attribute) {
        return new StringBuilder() //
                .append("\"") //
                .append(attribute) //
                .append("\"") //
                .toString();
    }

    String wrapElement(String element) {
        return new StringBuilder() //
                .append("{") //
                .append(element) //
                .append("}") //
                .toString();
    }

    String wrapParticipants(List<? extends Participant> participants) {
        return participants.map(p -> toJson(p)) //
                .intersperse(", ") //
                .prepend("[").append("]") //
                .fold("", String::concat);
    }


    public String toJson(Participant participant) {
        String assignedJson = Option.of(participant.getAssigned()) //
				.flatMap(a -> Option.of(", assigned:" + wrapAttribute(a))) //
                .getOrElse("")
        ;

        return List.of("name:" + wrapAttribute(participant.getName()), assignedJson) //
                .prepend("{").append("}") //
                .fold("", String::concat) //
        ;
    }
}
