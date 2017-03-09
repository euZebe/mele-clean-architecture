package me.euzebe.mele.usecase.generatedraw;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.LinkedHashMap;
import javaslang.collection.Seq;
import javaslang.control.Option;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Among;
import org.jacop.core.Domain;
import org.jacop.core.IntVar;
import org.jacop.core.IntervalDomain;
import org.jacop.core.SmallDenseDomain;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;

public class DrawWithRandom implements Draw {

	private LinkedHashMap<Integer, Participant> participantsByIndex = LinkedHashMap.empty();

	private HashMap<String, Integer> participantsByName = HashMap.empty();

	@Getter
	private String id;

	@Getter
	private DrawGenerationResult generationResult;

	private HashSet<Tuple2<Integer, Integer>> byIndexConstraints = HashSet.empty();

	protected DrawWithRandom() {
		this(UUID.randomUUID().toString());
	}

	private DrawWithRandom(String uuid) {
		this.id = uuid;
	}

	private DrawWithRandom(Seq<String> participantsNames, Seq<NotAllowedConstraint> constraints) {
		this();
		int index = 0;
		for (String name : participantsNames) {
		    participantsByIndex = participantsByIndex.put(index, new Participant(name));
		    participantsByName = participantsByName.put(name, index);
			++index;
		}

		constraints.forEach(c -> {
		    byIndexConstraints = byIndexConstraints.add(new Tuple2<Integer, Integer>( //
            		participantsByName.get(c.getOwner()).get(), //
            		participantsByName.get(c.getNotToBeAssigned()).get()) //
            		);
        });

        defineAssignments();
	}

	public static Option<Draw> generateWith(Seq<String> names, Seq<NotAllowedConstraint> constraints) {
		return validate(names, constraints) //
		? Option.of(new DrawWithRandom(names, constraints)) //
				: Option.none();
	}

    public static Option<Draw> generateWithoutSelfAssignment(Seq<String> names, Seq<NotAllowedConstraint> constraints) {
        Seq<NotAllowedConstraint> constraintsWithoutSelfAssignment = names //
                .map(name -> new NotAllowedConstraint(name, name)) //
                .appendAll(constraints);

        return DrawWithRandom.generateWith(names, constraintsWithoutSelfAssignment);
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
						constraint -> !names.contains(constraint.getOwner()) //
								|| !names.contains(constraint.getNotToBeAssigned()) //
				).isEmpty();
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

		//
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        search.getSolutionListener().searchAll(true);
        search.getSolutionListener().recordSolutions(true);
        SelectChoicePoint<IntVar> select = new InputOrderSelect<IntVar>(store, vars, new IndomainMin<IntVar>());
        // effectue la recherche
        search.labeling(store, select);

        if (search.getSolutionListener().solutionsNo() == 0) {
            generationResult = new DrawGenerationResult(false, "No solution found");
            return;
        }

        int randomIndex = new Random().nextInt(search.getSolutionListener().solutionsNo()) + 1;
        Domain[] solution = search.getSolution(randomIndex);


        for (int index = 0; index < solution.length; index++) {
			int assigneeIndex = ((SmallDenseDomain) solution[index]).min;
			participantsByIndex.get(index).get() //
					.setAssigned(participantsByIndex.get(assigneeIndex).get().getName());
		};
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
				.map(p -> "\t" + p._2.toString()) //
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
