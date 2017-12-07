// Sudoku Project
// November 2017
// Andy Vainauskas

//
// SudokuTimer is used to track the amount of time the user takes to solve the puzzle.
// The timerPanel is incremented every second with the current time taken on the puzzle for far.
// Once the user successfully solves the puzzle, the timer is stopped and its value is checked against
// the previous high score to determine if it was faster. If it is faster, the score is updated with the
// new faster value
//
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class SudokuTimer {
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private DecimalFormat dFormat = new DecimalFormat("00");
    private JPanel timerPanel = new JPanel();
    private JTextField timerText = new JTextField(5);

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            doTask();
        }
    };

    SudokuTimer() {
        timerPanel.add(timerText);
        timerText.setText("00:00:00");
        timerText.setEditable(false);
    }

    // Accessor methods
    public int getSeconds() {
        return seconds;
    }
    public int getMinutes() {
        return minutes;
    }
    public int getHours() {
        return hours;
    }

    // Starts the timer
    public void start() {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    // Pauses the timer (cancels the timer, and if it is resumed, a new timer and timerTask are created)
    public void pause() {
        timer.cancel();
    }

    // Creates a new timer and timerTask to resume counting
    public void resume() {
        this.task = new TimerTask() {
            @Override
            public void run() {
                doTask();
            }
        };
        this.timer = new Timer();
        this.timer.schedule(task, 1000, 1000);
    }

    // Updates the seconds, minutes, and hours each second
    public void doTask() {
        seconds++;
        if (seconds == 60) {
            minutes++;
            seconds = 0;
        }

        if (minutes == 60) {
            hours++;
            minutes = 0;
            seconds = 0;
        }

        if (hours == 24) {
            hours = 0;
            minutes = 0;
            seconds = 0;
        }
        timerText.setText(getTime());
    }

    public String getTime() {
        return dFormat.format(hours) + ":" +
                dFormat.format(minutes) + ":" +
                dFormat.format(seconds);
    }

    public JPanel getTimerPanel() {
        return timerPanel;
    }
}
