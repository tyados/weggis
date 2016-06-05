package com.weggis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SheetReader {

	public static final String SEPARATOR = ",";

	static DateTimeFormatter[] formatters;

	static {
		List<DateTimeFormatter> patterns = new ArrayList<>();
		String basic = "%s/%s/yyyy %s:%s%s";
		String[] days = new String[] { "dd", "d" };
		String[] months = new String[] { "MM", "M" };
		String[] hours = new String[] { "kk", "k" };
		String[] minutes = new String[] { "mm", "m" };
		String[] seconds = new String[] { ":ss", ":s", "" };
		for (int i1 = 0; i1 < days.length; i1++) {
			for (int i2 = 0; i2 < months.length; i2++) {
				for (int i3 = 0; i3 < hours.length; i3++) {
					for (int i4 = 0; i4 < minutes.length; i4++) {
						for (int i5 = 0; i5 < seconds.length; i5++) {
							patterns.add(DateTimeFormatter.ofPattern(String.format(basic, days[i1], months[i2], hours[i3], minutes[i4], seconds[i5])));
						}
					}
				}
			}
		}
		formatters = patterns.toArray(new DateTimeFormatter[patterns.size()]);
	}

	private static final String completedFileName = "C:\\Users\\user\\Downloads\\completed - complete.csv";
	private static final String incompletedFileName = "C:\\Users\\user\\Downloads\\completed - incomplete.csv";

	public static void main(String[] args) throws IOException {
		Sheet completed = readSheet(completedFileName);
		System.out.println(completed.dancers.size());
		Sheet incomplete = readSheet(incompletedFileName);
		System.out.println(incomplete.dancers.size());
		List<SimpleEntry<Integer, DancerRow>> list = incomplete.dancers.stream().collect(Collectors.groupingBy(DancerRow::getUserId))
				.entrySet().stream().map(x -> new AbstractMap.SimpleEntry<Integer, DancerRow>(x.getKey(), x.getValue().get(0)))
				.collect(Collectors.toList());
	}

	public static Sheet readSheet(String fileName) throws IOException {
		Sheet sheet = new Sheet();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("OrderRef")) {
					System.out.println("we're done!");
					break;
				}
				String[] splits = line.split(SEPARATOR);
				sheet.addDancer(new DancerRow(
						splits[0],
						parseDateTime(splits[1]),
						Integer.parseInt(splits[2]),
						Integer.parseInt(splits[3]),
						OrderStatus.valueOf(splits[6].toUpperCase()),
						Integer.parseInt(splits[7]),
						splits[8],
						splits[9]));
			}
		}
		return sheet;
	}

	static LocalDateTime parseDateTime(String string) {
		for (int i = 0; i < formatters.length; i++) {
			try {
				return LocalDateTime.parse(string, formatters[i]);
			} catch (DateTimeParseException ex) {
			}
		}
		throw new RuntimeException("sheeeeet nothing found for string: " + string);
	}

}
