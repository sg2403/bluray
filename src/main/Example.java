package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example {
	public static void main(String[] args) {
		
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("C:/Users/admin/Desktop/blueray/Assignment_sheet.csv"));
			
			List<String[]> rows = br.lines().skip(1).map(s -> s.split(",")).collect(Collectors.toList());
			Set<String> positionId = new HashSet<String>();
	
			for (String[] row : rows) {
				positionId.add(row[0]);

			}

			Example ex = new Example();
			
			System.out.println("worked for 7 consecutive days\n");
			for (String p1 : positionId) {

				List<String[]> rowsIdWise = rows.stream().filter(a -> a[0].equalsIgnoreCase(p1))
						.collect(Collectors.toList());
				ex.workingSevenConsecutiveDays(rowsIdWise);
			}


			System.out.println("\n\nworked less than 10 hours of time between shifts but greater than 1 hour\n");
			for (String p1 : positionId) {

				List<String[]> rowsIdWise = rows.stream().filter(a -> a[0].equalsIgnoreCase(p1))
						.collect(Collectors.toList());
				ex.workingBetweenShifts(rowsIdWise);
			}

			System.out.println("\n\nworked for more than 14 hours in a single shift\n");

			for (String p1 : positionId) {
				List<String[]> rowsIdWise = rows.stream().filter(a -> a[0].equalsIgnoreCase(p1))
						.collect(Collectors.toList());
				ex.workingSingleShifts(rowsIdWise);

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	public void workingSevenConsecutiveDays(List<String[]> sc) {

		String[] line1 = sc.get(0);
		int counter = 1;
		int count = 0;
		String position1 = line1[0];
		String name = line1[7];
		while (counter < sc.size()) {

			String[] line2 = sc.get(counter);
			String dt1 = line1[2];
			String[] date1 = dt1.split(" ");
			Date dates1 = null;
			String dt2 = line2[2];
			String[] date2 = dt2.split(" ");
			Date dates2 = null;

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			try {
				if (!date1[0].isEmpty()) {
					dates1 = (Date) formatter.parse(date1[0]);
				} else {
					line1 = line2;
					counter++;
					continue;
				}

				if (!date2[0].isEmpty()) {
					dates2 = (Date) formatter.parse(date2[0]);
				} else {
					counter++;
					continue;
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

			long diff = dates2.getTime() - dates1.getTime();

			if (diff == 86400000) {
				count++;
			}

			line1 = line2;
			counter++;
		}

		if (count >= 7) {
			System.out.println("Position = " + position1 + " 	 name = " + name);
		}
	}

	public void workingBetweenShifts(List<String[]> sc) {

		String[] line1 = sc.get(0);
		int counter = 1;
		int count = 0;
		String position1 = line1[0];
		String name = line1[7];
		while (counter < sc.size()) {

			String[] line2 = sc.get(counter);
			String dt1 = line1[3];
			Date dates1 = null;
			String dt2 = line2[2];
			Date dates2 = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");

			try {
				if (!dt1.isEmpty()) {
					dates1 = (Date) formatter.parse(dt1);
				} else {
					line1 = line2;
					counter++;
					continue;
				}

				if (!dt2.isEmpty()) {
					dates2 = (Date) formatter.parse(dt2);
				} else {
					counter++;
					continue;
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

			long diff = dates2.getTime() - dates1.getTime();

			if (diff > 3600000 && diff < 36000000) {
				count++;
			}

			line1 = line2;
			counter++;
		}

		if (count > 0) {
			System.out.println("Position = " + position1 + " 	 name = " + name);
		}

	}

	public void workingSingleShifts(List<String[]> sc) {

		int counter = 0;
		int count = 0;
		String position1 = null;
		String name = null;
		while (counter < sc.size()) {
			String[] line1 = sc.get(counter);

			position1 = line1[0];
			name = line1[7];
			String dt1 = line1[2];
			Date dates1 = null;
			String dt2 = line1[3];
			Date dates2 = null;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");

			try {
				if (!dt1.isEmpty()) {
					dates1 = (Date) formatter.parse(dt1);
				} else {
					counter++;
					continue;
				}

				if (!dt2.isEmpty()) {
					dates2 = (Date) formatter.parse(dt2);
				} else {
					counter++;
					continue;
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

			long diff = dates2.getTime() - dates1.getTime();

			if (diff > 50400000) {
				count++;
			}
			counter++;
		}

		if (count > 0) {
			System.out.println("Position = " + position1 + " 	 name = " + name);
		}

	}
}
