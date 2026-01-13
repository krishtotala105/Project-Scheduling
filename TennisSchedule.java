import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;
import java.util.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class TennisSchedule {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		
        
		//start date
		
		
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate startDate = null;

        while (startDate == null) {
            System.out.print("Please enter a start date (format MM-dd-yyyy): ");
            String input = scanner.nextLine();

            startDate = LocalDate.parse(input, formatter);
            System.out.println();
            
        }
        
        
        
        //end date
        
        
        
        LocalDate endDate = null;

        while (endDate == null) {
            System.out.print("Please enter an end date (format MM-dd-yyyy): ");
            String input = scanner.nextLine();

            endDate = LocalDate.parse(input, formatter);
            System.out.println();
        }
        
        
        
        //weeks 
        
        
        
        List<String> weekStartDates = getWeekStartDates(startDate, endDate, formatter);
        System.out.println("\nWeek start dates between " + startDate.format(formatter) + " and " + endDate.format(formatter) + ":\n");
        
        for (String i : weekStartDates) {
        	System.out.println(i);
        }
        
        
        
        //Breaks
        
        
        
        System.out.print("\nAre there any vacation days ('y' or 'n'): ");
    	String cond = scanner.nextLine();
        while (cond.equals("y")) {
        	System.out.print("\nEnter which day is a vacation day (format MM-dd-yyyy): ");
        	String input = scanner.nextLine();

            String breaks = LocalDate.parse(input, formatter).format(formatter);
            
        	
            if (weekStartDates.contains(breaks)) {
            	weekStartDates.remove(breaks);
            	System.out.println("\nDay successfully removed from schedule.");
            }
            
            else System.out.println("\nDay doesn't exist in schedule.");
        	
        	System.out.print("\nAre there any more vacation days ('y' or 'n'): ");
        	cond = scanner.nextLine();
        	
        }
            
        
        
        //Players
        
        
        
        final int numPlayers = 18;
        int[] players = new int[numPlayers];
        
        for (int i = 0; i <= players.length - 1; i++) {
        	players[i] = i + 1;
        }
        
        
        
        //Schedule Building
        
        
        
        int counter = 1;
        int subCount;
        
        ArrayList<Integer> playReport = new ArrayList<>();

        for (int i = 0; i < weekStartDates.size(); i++) {
            System.out.print("\n" + weekStartDates.get(i) + ": ");

            for (int j = 0; j < 8; j++) {
                System.out.print(" ||| PLAYER " + counter);
                
                playReport.add(counter);
                
                counter++;
                if (counter > 18) {
                    counter = 1;
                }
            }
            subCount = counter;
            
            for (int j = 0; j < numPlayers - 8; j++) {
            	
            	if (i <= 1 || j <= 1) System.out.print(" ||| SUB " + subCount);
            	
            	else if (j >= 2 && j < numPlayers - 12) {
            		
            		if (subCount + 4 > 18) System.out.print(" ||| SUB " + (subCount + 4 - numPlayers));
            		
            		else System.out.print(" ||| SUB " + (subCount + 4));
            	}
            	
            	else {
            		if (subCount - 4 < 1) System.out.print(" ||| SUB " + (subCount - 4 + numPlayers));
            		
            		else System.out.print(" ||| SUB " + (subCount - 4));
            	}
            	
            	
            	subCount++;
            	if (subCount > 18) {
                    subCount = 1;
                }
            }
            
            System.out.println(" ||| \n");   
        }
        
        
        
        //Report
        
        
        
        System.out.println("\n\n\nREPORT: \n");
        for (int i = 1; i <= numPlayers; i++) {
        	System.out.println("PLAYER " + i + ": " + Collections.frequency(playReport, i) + " games.\n");
        }
         
	}
	
	
	
	//get week start dates helper
	
	
	
	public static List<String> getWeekStartDates(LocalDate start, LocalDate end, DateTimeFormatter formatter) {

	    List<String> dates = new ArrayList<>();

	    LocalDate current = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

	    while (!current.isAfter(end)) {
	        dates.add(current.format(formatter));
	        current = current.plusWeeks(1);
	    }

	    return dates;
	}

}
