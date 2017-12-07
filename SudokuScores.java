// Sudoku Project
// November 2017
// Andy Vainauskas

//
// SudokuScores manages the high scores for the various puzzle
// difficulty levels. This class is saved to a file using serialization
// so that high scores can be maintained.
//
import java.io.Serializable;
import java.text.DecimalFormat;

public class SudokuScores implements Serializable {
    private DecimalFormat dFormat = new DecimalFormat("00");
    private Time basicRecord;
    private Time easyRecord;
    private Time mediumRecord;
    private Time hardRecord;
    private Time expertRecord;

    SudokuScores() {
        basicRecord = null;
        easyRecord = null;
        mediumRecord = null;
        hardRecord = null;
        expertRecord = null;
    }

    // Class to abstract the record times
    class Time implements Serializable {

        int seconds, minutes, hours;

        public Time(int seconds, int minutes, int hours) {
            super();
            this.seconds = seconds;
            this.minutes = minutes;
            this.hours = hours;
        }

        @Override
        public String toString() {
            return (dFormat.format(hours)
                    + ":" + dFormat.format(minutes)
                    + ":" + dFormat.format(seconds));
        }
    }

    // Returns a string that contains all of the high scores
    public String getScores() {
        StringBuilder result = new StringBuilder("");
        result.append("Best Times: \n");
        result.append("------------ \n");

        if (basicRecord == null) {
            result.append("Basic: No record \n");
        } else {
            result.append("Basic: " + basicRecord + "\n");
        }

        if (easyRecord == null) {
            result.append("Easy: No record \n");
        } else {
            result.append("Easy: " + easyRecord + "\n");
        }

        if (mediumRecord == null) {
            result.append("Medium: No record \n");
        } else {
            result.append("Medium: " + mediumRecord + "\n");
        }

        if (hardRecord == null) {
            result.append("Hard: No record \n");
        } else {
            result.append("Hard: " + hardRecord + "\n");
        }

        if (expertRecord == null) {
            result.append("Expert: No record \n");
        } else {
            result.append("Expert: " + expertRecord + "\n");
        }

        return result.toString();
    }

    // Updates the high score time if it is faster than the record based on the difficulty
    public void updateHighScore(String difficulty, SudokuTimer timer) {
        if (difficulty.equals("basic")) {
            if (isFaster(basicRecord, timer)) {
                Time newRecord = new Time(timer.getSeconds(), timer.getMinutes(), timer.getHours());
                basicRecord = newRecord;
            }
        }

        if (difficulty.equals("easy")) {
            if (isFaster(easyRecord, timer)) {
                Time newRecord = new Time(timer.getSeconds(), timer.getMinutes(), timer.getHours());
                basicRecord = newRecord;
            }
        }

        if (difficulty.equals("medium")) {
            if (isFaster(mediumRecord, timer)) {
                Time newRecord = new Time(timer.getSeconds(), timer.getMinutes(), timer.getHours());
                basicRecord = newRecord;
            }
        }

        if (difficulty.equals("hard")) {
            if (isFaster(hardRecord, timer)) {
                Time newRecord = new Time(timer.getSeconds(), timer.getMinutes(), timer.getHours());
                basicRecord = newRecord;
            }
        }

        if (difficulty.equals("expert")) {
            if (isFaster(expertRecord, timer)) {
                Time newRecord = new Time(timer.getSeconds(), timer.getMinutes(), timer.getHours());
                basicRecord = newRecord;
            }
        }
    }

    // Checks to see if a Time is faster than the current record
    public boolean isFaster(Time record, SudokuTimer timer) {
        if (record == null)
            return true;

        if (timer.getHours() < record.hours)
            return true;

        if (timer.getMinutes() < record.minutes)
            return true;

        if (timer.getSeconds() < record.seconds)
            return true;

        return false;
    }

    // Resets all of the high scores
    public void reset() {
        basicRecord = null;
        easyRecord = null;
        mediumRecord = null;
        hardRecord = null;
        expertRecord = null;
    }
}