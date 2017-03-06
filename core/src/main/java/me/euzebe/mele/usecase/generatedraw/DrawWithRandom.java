package me.euzebe.mele.usecase.generatedraw;

import java.util.Random;
import java.util.UUID;

import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.control.Option;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

public class DrawWithRandom implements Draw {

	@Getter
	private Seq<Participant> participants = List.empty();

	@Getter
	private String id;

	private DrawWithRandom() {
		this(UUID.randomUUID().toString());
	}

	private DrawWithRandom(String uuid) {
		this.id = uuid;
	}

	private DrawWithRandom(Seq<String> participantsName) {
		this();
		this.participants = participantsName //
				.map(name -> new Participant(name));

		defineAssignments();
	}

	/**
	 * Generate a Draw if participants are valid
	 *
	 * @param participantsName
	 *            - name of each participant to the draw
	 * @return an empty Option if <code>participantsName</code> is invalid, an
	 *         Option containing the draw otherwise.
	 */
	public static Option<Draw> generateWith(String... participantsName) {
		List<String> names = List.of(participantsName) //
				.toList();

		return generateWith(names, List.empty());
	}

	public static Option<Draw> generateWith(Seq<String> names, Seq<NotAllowedConstraint> constraints) {
		return validate(names, constraints) //
		? Option.of(new DrawWithRandom(names)) //
				: Option.none();
	}

	/**
	 *
	 * @param names
	 * @return <code>false</code> if participantsName is empty or contains
	 *         duplicates, <code>true</code> otherwise.
	 */
	static boolean validate(Seq<String> names) {
		return !names.isEmpty() //
				&& names.filter(StringUtils::isNotEmpty).isDefined() //
				&& inputHasNoDuplicate(names);
	}

	static boolean validate(Seq<String> names, Seq<NotAllowedConstraint> constraints) {
		return validate(names) && constraintsHaveOnlyExistingNames(constraints, names);
	}

	static boolean constraintsHaveOnlyExistingNames(Seq<NotAllowedConstraint> constraints, Seq<String> names) {
		return constraints.isEmpty() //
				|| constraints.filter( //
						constraint -> names.contains(constraint.getOwner()) //
								&& names.contains(constraint.getNotToBeAssigned()) //
				).isDefined();
	}

	private static boolean inputHasNoDuplicate(Seq<String> names) {
		return names.size() == names //
				.map(name -> name.toLowerCase()) //
				.distinct() //
				.size();
	}

	private void defineAssignments() {
		Random random = new Random();
		this.participants.forEach(p -> {
			int randomIndex = random.nextInt(this.participants.size());
			p.setAssigned(this.participants.get(randomIndex).getName());
		});
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
