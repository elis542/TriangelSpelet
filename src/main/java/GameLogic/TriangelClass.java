package GameLogic;

public class TriangelClass {
    private int[] values = new int[3]; //Left, right, top. Could maybe be an Enum instead...
    private int spin = 0; //1 spin equals one turn to clockwise, max is 2 then back to 0
    private int[] location = new int[2]; //x, y
    private boolean placed = false;

    public TriangelClass(int[] values) {
        this.values = values;
    }

    public int[] getValues() { return values; };
}
