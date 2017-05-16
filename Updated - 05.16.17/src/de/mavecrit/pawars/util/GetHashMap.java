package de.mavecrit.pawars.util;

import java.util.HashMap;
import java.util.Map.Entry;

public class GetHashMap {
	public static Integer getVotedMap(HashMap<Integer, Integer> map) {
		Integer highestMap = null;
		int highestVote = 0;
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue() > highestVote) {
				highestMap = entry.getKey();
				highestVote = entry.getValue();
			}
		}
		return highestMap;
	}
}
