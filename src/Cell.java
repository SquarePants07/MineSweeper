/**
 * Created by Anatoliy.Polyakov on 28.08.2019.
 */
public class Cell {
    boolean bomb;
    boolean hidden;
    boolean checked;
    boolean fatal;
    int number;

    public Cell(){
        hidden = true;
        checked = false;
        fatal = false;
        number = 0;
    }
}
