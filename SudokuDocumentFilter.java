// Sudoku Project
// November 2017
// Andy Vainauskas

//
// SudokuDocumentFilter restricts the allowed input
// to the textFields to be only integers 1-9 with a length of 1
//
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SudokuDocumentFilter extends DocumentFilter {
    private Pattern values = Pattern.compile("[1-9]+");

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }

        // Restricts the length of the textField value to 1
        if(fb.getDocument().getLength() + str.length() > 1)
        {
            return;
        }

        // Only allows integers 1-9 to be entered, or the empty string (for clearing the textField)
        if (values.matcher(str).matches() || str.equals("")) {
            super.insertString(fb, offs, str, a);
        }
    }

    @Override
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException
    {
        fb.remove(offset, length);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs)
            throws BadLocationException {
        if (str == null) {
            return;
        }

        // Restricts the length of the textField value to 1
        if(fb.getDocument().getLength() + str.length() > 1)
        {
            return;
        }

        // Only allows integers 1-9 to be entered, or the empty string (for clearing the textField)
        if (values.matcher(str).matches() || str.equals("")) {
            fb.replace(offset, length, str, attrs);
        }
    }
}