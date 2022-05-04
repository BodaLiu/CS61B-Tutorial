public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> result = new ArrayDeque<>();
        for(int i = 0; i < word.length(); i++){
            char temp = word.charAt(i);
            result.addLast(temp);
        }
        return result;
    }
    public boolean isPalindrome(String word){
        Deque<Character> helper = wordToDeque(word);
        while(helper.size()>1 ){
            if(helper.removeFirst() != helper.removeLast()){
                return false;
            }
        }
        return true;
    }
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> helper = wordToDeque(word);
        while(helper.size()>1 ){
            if(!cc.equalChars(helper.removeFirst(), helper.removeLast())){
                return false;
            }
        }
        return true;
    }
}
