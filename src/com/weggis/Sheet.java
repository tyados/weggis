package com.weggis;

import java.util.ArrayList;
import java.util.List;

public class Sheet {
	List<DancerRow> dancers = new ArrayList<>();

	Sheet addDancer(DancerRow row) {
		dancers.add(row);
		return this;
	}

	static class UniqueUserId {
		int userId;
		String fullName;

		public UniqueUserId(int userId, String fullName) {
			this.userId = userId;
			this.fullName = fullName;
		}

		static UniqueUserId from(DancerRow row) {
			return new UniqueUserId(row.userId, row.fullName);
		}
	}
}
