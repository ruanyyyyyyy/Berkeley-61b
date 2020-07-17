public class OffByN implements CharacterComparator {
    private int offnum;
    public OffByN(int N) {
        offnum = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return ((x - y) == offnum || (y - x) == offnum);
    }
}
