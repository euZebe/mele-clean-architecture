package me.euzebe.mele.usecase.generatedraw;

import javaslang.collection.List;
import javaslang.collection.Seq;

public interface Draw {

	public String getId();

	public Seq<Participant> getParticipants();

	public DrawGenerationResult getGenerationResult();

	public static final Draw EMPTY = new Draw() {
		@Override
		public List<Participant> getParticipants() {
			return List.empty();
		}

		@Override
		public String getId() {
			return null;
		}

        @Override
        public DrawGenerationResult getGenerationResult() {
            return null;
        }
	};

}
