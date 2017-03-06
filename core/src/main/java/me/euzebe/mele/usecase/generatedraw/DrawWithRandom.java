package me.euzebe.mele.usecase.generatedraw;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import javaslang.Tuple2;
import javaslang.collection.LinkedHashMap;
import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.control.Option;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Among;
import org.jacop.core.IntVar;
import org.jacop.core.IntervalDomain;
import org.jacop.core.Store;

public class DrawWithRandom implements Draw {

	@Getter
	private LinkedHashMap<Integer, Participant> participantsByIndex = LinkedHashMap.empty();

	private HashMap<String, Integer> participantsByName = new HashMap<>();

	@Getter
	private String id;

	private HashSet<Tuple2<Integer, Integer>> byIndexConstraints = new HashSet<>();

	private DrawWithRandom() {
		this(UUID.randomUUID().toString());
	}

	private DrawWithRandom(String uuid) {
		this.id = uuid;
	}

	private DrawWithRandom(Seq<String> participantsNames, Seq<NotAllowedConstraint> constraints) {
		this();
		int index = 0;
		for (String name : participantsNames) {
			participantsByIndex.put(index, new Participant(name));
			participantsByName.put(name, index);
			++index;
		}

		constraints.forEach(c -> byIndexConstraints.add(new Tuple2<Integer, Integer>( //
				participantsByName.get(c.getOwner()), //
				participantsByName.get(c.getNotToBeAssigned())) //
				));

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
		? Option.of(new DrawWithRandom(names, constraints)) //
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
		Store store = new Store();
		IntVar[] vars = IntStream.range(0, participantsByIndex.size()) //
				.mapToObj(i -> {
					return new IntVar(store, participantsByIndex.get(i).get().getName(), 0, participantsByIndex.size() - 1);
				}) //
				.toArray(IntVar[]::new);

		// no one is assigned twice
		store.impose(new Alldifferent(vars));

		// add "not allowed" constraints
		byIndexConstraints.forEach(constraint -> {
			addNotAllowedConstraint(store, byIndexConstraints.size(), vars[constraint._1], constraint._2);
		});

		Random random = new Random();
		this.participantsByIndex.forEach(p -> {
			int randomIndex = random.nextInt(this.participantsByIndex.size());
			p._2.setAssigned(this.participantsByIndex.get(randomIndex).get().getName());
		});
	}

	private void addNotAllowedConstraint(Store store, int participantsSize, IntVar var, int blocked) {
		IntervalDomain kSet = new IntervalDomain();
		for (int i = 0; i < participantsSize; i++) {
			if (i != blocked) {
				kSet.addDom(new IntervalDomain(i, i));
			}
		}
		store.impose(new Among(new IntVar[] { var }, kSet, new IntVar(store, 1, 1)));
	}

	@Override
	public String toString() {
		String foldedParticipants = participantsByIndex //
				.map(p -> "\t" + p._2.getAssignmentToString()) //
				.intersperse(",\n") //
				.fold("", String::concat);

		return new StringBuilder() //
				.append("Draw ").append(id) //
				.append("[\n") //
				.append(foldedParticipants) //
				.append("\n]") //
				.toString();
	}

	@Override
	public Seq<Participant> getParticipants() {
		return participantsByIndex.toStream() //
				.map(tuple -> tuple._2);
	}
}
