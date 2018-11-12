public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> dequeWord = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            dequeWord.addLast(word.charAt(i));
        }
        return dequeWord;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> dq = this.wordToDeque(word);
        while (!dq.isEmpty()) {
            if (dq.size() == 1) {
                return true;
            } else if (dq.removeFirst() != dq.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dq = this.wordToDeque(word);
        while (!dq.isEmpty()) {
            if (dq.size() == 1) {
                return true;
            } else if (!cc.equalChars(dq.removeFirst(), dq.removeLast())) {
                return false;
            }
        }
        return true;

    }
}
