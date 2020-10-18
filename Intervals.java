import java.util.*;

public class Intervals {

    public static String intervalConstruction(String[] args) {

        if (args.length > 3) {
            return "Illegal number of elements in input array";
        }

        String intervalName = args[0];
        String noteName = args[1]; // note name with possible accidentals
        String expectedNote;       // expected note without possible accidentals
        String result = "";        // method result - note name with possible accidentals
        List<String> listNotesStr = new ArrayList<>(); // list for breaking of the group of notes in the scale on the list of strings
        int semitones = 0;      // for calculating semitones
        int remainderSemitones; // semitones remainder to determine the method result
        String sharpName = "#";
        String flatName = "b";
        int sharpSemitones;
        int flatSemitones;
        String direction;
        String descending = "dsc"; // descending interval
        int degreesQuantity;
        int firstNoteIndex;  // indexes in notesScale for the purposes of the method
        int finalNoteIndex;
        StringBuilder stringOfNotes = new StringBuilder(); // for determining the expected group of notes in the scale for the purposes of method

        if (args.length > 2 && args[2].equals(descending)) {
            direction = "dsc";
        } else {
            direction = "asc";
        }

        Map<String, Integer> listOfIntervals = new HashMap<>(); // value is the semitones quantity in interval
        listOfIntervals.put("m2", 1);
        listOfIntervals.put("M2", 2);
        listOfIntervals.put("m3", 3);
        listOfIntervals.put("M3", 4);
        listOfIntervals.put("P4", 5);
        listOfIntervals.put("P5", 7);
        listOfIntervals.put("m6", 8);
        listOfIntervals.put("M6", 9);
        listOfIntervals.put("m7", 10);
        listOfIntervals.put("M7", 11);
        listOfIntervals.put("P8", 12);

        Map<String, Integer> semitonesWithNote = new HashMap<>(); // value is the semitones quantity of note to the right
        semitonesWithNote.put("D", 2);
        semitonesWithNote.put("G", 2);
        semitonesWithNote.put("A", 2);
        if (direction.equals(descending)) {    // with considering reverse of the scale
            semitonesWithNote.put("C", 1);
            semitonesWithNote.put("E", 2);
            semitonesWithNote.put("F", 1);
            semitonesWithNote.put("B", 2);
        } else {
            semitonesWithNote.put("C", 2);
            semitonesWithNote.put("E", 1);
            semitonesWithNote.put("F", 2);
            semitonesWithNote.put("B", 1);
        }

        StringBuilder notesScale = new StringBuilder("CDEFGABCDEFGAB");
        if (direction.equals(descending)) {
            notesScale.reverse();
        }

        if (direction.equals(descending)) {  // determination of the number of semitones in accidentals
            sharpSemitones = 1;
            flatSemitones = -1;
        } else {
            sharpSemitones = -1;
            flatSemitones = 1;
        }

        if (noteName.length() > 1) {  // calculating semitones in the incoming note (if there are accidentals)
            if (String.valueOf(noteName.charAt(1)).equals(sharpName)) {
                semitones = sharpSemitones;
            } else {
                semitones = flatSemitones;
            }
        }

        firstNoteIndex = notesScale.indexOf(String.valueOf(noteName.charAt(0)));
        degreesQuantity = Integer.parseInt(intervalName.substring(1));
        finalNoteIndex = firstNoteIndex - 1 + degreesQuantity;
        expectedNote = String.valueOf(notesScale.charAt(finalNoteIndex));
        stringOfNotes.append(notesScale.substring(firstNoteIndex, finalNoteIndex));

        // breaking of notes on list of strings
        char[] arrNotesCh = new char[stringOfNotes.length()];
        stringOfNotes.getChars(0, stringOfNotes.length(), arrNotesCh, 0);
        for (char ch : arrNotesCh) {
            listNotesStr.add(String.valueOf(ch));
        }
        // calculating of semitones (including only accidental of incoming note)
        for (String st : listNotesStr) {
            semitones += semitonesWithNote.get(st);
        }

        remainderSemitones = semitones - listOfIntervals.get(intervalName);
        // changing remainder of semitones if the interval is descending
        if (direction.equals(descending)) {
            if (remainderSemitones > 0) {
                remainderSemitones = -remainderSemitones;
            } else {
                remainderSemitones = Math.abs(remainderSemitones);
            }
        }

        switch (remainderSemitones) {
            case 2:
                result = expectedNote.concat(flatName).concat(flatName);
                break;
            case 1:
                result = expectedNote.concat(flatName);
                break;
            case 0:
                result = expectedNote;
                break;
            case -1:
                result = expectedNote.concat(sharpName);
                break;
            case -2:
                result = expectedNote.concat(sharpName).concat(sharpName);
                break;
        }
        return result;
    }

    public static String intervalIdentification(String[] args) {

        String noteNameStart = args[0];  //  the first note name in the interval with possible accidentals
        String noteNameFinish = args[1]; //  the second note name in the interval with possible accidentals

        String[] correctIntervalDescription = new String[]{"Cbb", "Cb", "C", "C#", "C##", "Dbb", "Db", "D", "D#",
                "D##", "Ebb", "Eb", "E", "E#", "E##", "Fbb", "Fb", "F", "F#", "F##", "Gbb", "Gb", "G", "G#", "G##", "Abb",
                "Ab", "A", "A#", "A##", "Bbb", "Bb", "B", "B#", "B##"};
        Arrays.sort(correctIntervalDescription);
        if (Arrays.binarySearch(correctIntervalDescription, noteNameStart) < 0 || Arrays.binarySearch(correctIntervalDescription, noteNameFinish) < 0) {
            return "Cannot identify the interval";
        }

        String noteStart = String.valueOf(noteNameStart.charAt(0));   // only note name without possible accidentals
        String noteFinish = String.valueOf(noteNameFinish.charAt(0)); // only note name without possible accidentals
        StringBuilder stringOfNotes = new StringBuilder(); // for determining the expected group of notes in the scale for the purposes of method
        List<String> listNotesStr = new ArrayList<>(); // list for breaking of the group of notes in the scale on the list of strings
        int semitones = 0;      // for calculating semitones
        int semitonesStartNote = 0;
        int semitonesFinishNote = 0;
        String sharpName = "#";
        int sharpSemitonesStart; // semitones in the accidentals of the first note in the interval
        int flatSemitonesStart;
        int sharpSemitonesFinish;    // semitones in the accidentals of the second note in the interval
        int flatSemitonesFinish;
        String direction;
        String descending = "dsc"; // descending interval

        if (args.length > 2 && args[2].equals(descending)) {
            direction = "dsc";
        } else {
            direction = "asc";
        }

        Map<Integer, String> listOfIntervals = new HashMap<>(); // key is the semitones quantity in interval
        listOfIntervals.put(1, "m2");
        listOfIntervals.put(2, "M2");
        listOfIntervals.put(3, "m3");
        listOfIntervals.put(4, "M3");
        listOfIntervals.put(5, "P4");
        listOfIntervals.put(7, "P5");
        listOfIntervals.put(8, "m6");
        listOfIntervals.put(9, "M6");
        listOfIntervals.put(10, "m7");
        listOfIntervals.put(11, "M7");
        listOfIntervals.put(12, "P8");

        Map<String, Integer> semitonesWithNote = new HashMap<>(); // value is the semitones quantity of note to the right
        semitonesWithNote.put("D", 2);
        semitonesWithNote.put("G", 2);
        semitonesWithNote.put("A", 2);
        if (direction.equals(descending)) { // with considering reverse of the scale
            semitonesWithNote.put("C", 1);
            semitonesWithNote.put("E", 2);
            semitonesWithNote.put("F", 1);
            semitonesWithNote.put("B", 2);
        } else {
            semitonesWithNote.put("C", 2);
            semitonesWithNote.put("E", 1);
            semitonesWithNote.put("F", 2);
            semitonesWithNote.put("B", 1);
        }

        // determination of semitones in accidentals for the 1 and 2 notes of the interval depending on the direction
        if (direction.equals(descending)) {
            sharpSemitonesStart = 1;
            flatSemitonesStart = -1;
            sharpSemitonesFinish = -1;
            flatSemitonesFinish = 1;
        } else {
            sharpSemitonesStart = -1;
            flatSemitonesStart = 1;
            sharpSemitonesFinish = 1;
            flatSemitonesFinish = -1;
        }

        StringBuilder notesScale = new StringBuilder("CDEFGABCDEFGAB");
        if (direction.equals(descending)) {
            notesScale.reverse();
        }

        int firstNoteIndex = notesScale.indexOf(noteStart);
        int finalNoteIndex = notesScale.indexOf(noteFinish);
        if (finalNoteIndex < firstNoteIndex) {
            finalNoteIndex = finalNoteIndex + 7;
        }

        stringOfNotes.append(notesScale.substring(firstNoteIndex, finalNoteIndex)); // the final expected group of notes in the scale

        // calculating semitones in accidentals for the first note of the interval
        if (noteNameStart.length() > 1) {
            if (String.valueOf(noteNameStart.charAt(1)).equals(sharpName)) {
                semitonesStartNote = sharpSemitonesStart;
            } else {
                semitonesStartNote = flatSemitonesStart;
            }
            if (noteNameStart.length() > 2) {
                semitonesStartNote = semitonesStartNote * 2;
            }
        }
        // calculating semitones in accidentals for the second note of the interval
        if (noteNameFinish.length() > 1) {
            if (String.valueOf(noteNameFinish.charAt(1)).equals(sharpName)) {
                semitonesFinishNote = sharpSemitonesFinish;
            } else {
                semitonesFinishNote = flatSemitonesFinish;
            }
            if (noteNameFinish.length() > 2) {
                semitonesFinishNote = semitonesFinishNote * 2;
            }
        }

        // breaking of notes on list of strings
        char[] arrNotesCh = new char[stringOfNotes.length()];
        stringOfNotes.getChars(0, stringOfNotes.length(), arrNotesCh, 0);
        for (char ch : arrNotesCh) {
            listNotesStr.add(String.valueOf(ch));
        }
        for (String st : listNotesStr) {
            semitones += semitonesWithNote.get(st);
        }

        return listOfIntervals.get(semitones + semitonesStartNote + semitonesFinishNote);
    }
}

