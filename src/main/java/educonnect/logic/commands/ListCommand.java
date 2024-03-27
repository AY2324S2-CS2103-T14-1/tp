package educonnect.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import educonnect.model.Model;
import educonnect.model.student.Student;

/**
 * Lists all students in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of all students in the address book.\n"
            + "Optional to list all students with their timetables\n"
            + "Example 1: list\nExample 2: list timetable\n";

    public static final String MESSAGE_SUCCESS = "Listed all students";

    public static final String MESSAGE_SUCCESS_TIMETABLE = "Listed all students with timetables";

    private boolean showTimeTable;

    public ListCommand() {
        this.showTimeTable = false;
    }
    public ListCommand(boolean showTimeTable) {
        this.showTimeTable = showTimeTable;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Student> studentList = model.getFilteredStudentList();
        for (Student s : studentList) {
            Student studentAfter = s.copy();
            if (showTimeTable) {
                studentAfter.showTimetable();
            } else {
                studentAfter.hideTimetable();
            }
            model.setStudent(s, studentAfter);
        }
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        String outputMessage = showTimeTable ? MESSAGE_SUCCESS_TIMETABLE : MESSAGE_SUCCESS;
        return new CommandResult(outputMessage);
    }
}
