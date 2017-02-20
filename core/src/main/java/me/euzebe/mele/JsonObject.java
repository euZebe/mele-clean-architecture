package me.euzebe.mele;

import javaslang.collection.List;

public interface JsonObject {
	public String toJson();

	default String wrapAttribute(String attribute) {
		return new StringBuilder() //
				.append("\"") //
				.append(attribute) //
				.append("\"") //
				.toString();
	}

	default String wrapElement(String element) {
		return new StringBuilder() //
				.append("{") //
				.append(element) //
				.append("}") //
				.toString();
	}

	default String wrapArray(List<? extends JsonObject> participants) {
		return participants
				.map(obj -> obj.toJson()) //
				.intersperse(", ") //
				.prepend("[").append("]") //
				.fold("", String::concat);
	}
}
