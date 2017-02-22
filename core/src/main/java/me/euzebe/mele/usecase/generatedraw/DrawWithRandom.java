package me.euzebe.mele.usecase.generatedraw;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import javaslang.collection.List;
import javaslang.collection.Stream;
import javaslang.control.Option;
import lombok.Getter;

public class DrawWithRandom implements Draw {

	public static final DrawWithRandom EMPTY = new DrawWithRandom("emptyDraw");

	@Getter
	private List<Participant> participants;

	@Getter
	private String id;

	private DrawWithRandom() {
		this(UUID.randomUUID().toString());
	}

	private DrawWithRandom(String uuid) {
		this.id = uuid;
	}

	private DrawWithRandom(List<String> participantsName) {
		this();
		this.participants = participantsName //
				.map(name -> new Participant(name));

		defineAssignments();
	}

	private DrawWithRandom(String... participantsName) {
		this(List.of(participantsName));
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
		List<String> names = Stream.of(participantsName) //
				.filter(StringUtils::isNotEmpty) //
				.toList();

		return validate(names) //
		? Option.of(new DrawWithRandom(names)) //
				: Option.none();
	}

	/**
	 *
	 * @param names
	 * @return <code>false</code> if participantsName is empty or contains
	 *         duplicates, <code>true</code> otherwise.
	 */
	static boolean validate(List<String> names) {
		return !names.isEmpty() //
				&& inputHasNoDuplicate(names);
	}

	private static boolean inputHasNoDuplicate(List<String> names) {
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
