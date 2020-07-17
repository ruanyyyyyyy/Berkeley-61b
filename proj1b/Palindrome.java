public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> dq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            char a = word.charAt(i);
            dq.addLast(a);
        }
        return dq;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> dq1 = wordToDeque(word);
        int num = dq1.size();
        if (num == 0 || num == 1) {
            return true;
        }
        for (int i = 0; i < num / 2; i += 1) {
            if (!dq1.removeFirst().equals(dq1.removeLast())) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dq1 = wordToDeque(word);
        int num = dq1.size();
        if (num == 0 || num == 1) {
            return true;
        }
        for (int i = 0; i < num / 2; i += 1) {
            if (!cc.equalChars(dq1.removeFirst(), dq1.removeLast())) {
                return false;
            }
        }
        return true;
    }

}
