package TimeComplexity;

import java.util.*;

public class GroupAnagrams {
    public static void main(String[] args) {
        System.out.println(2^3);
    }

    public static int longestConsecutive(int[] nums) {
        Set<Character> charSet = new LinkedHashSet<>();
        for(int num:nums){
            String numStr = num+"";
            char[] chars = numStr.toCharArray();
            for(char cha:chars){
                charSet.add(cha);
            }
        }
        System.out.println(charSet);
        return charSet.size();
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<String> input = new ArrayList<>(Arrays.asList(strs));
        List<List<String>> result = new ArrayList<>();
        while(input.size()>0){
            for(int i=0;i<input.size();i++){
                char[] charArray = input.get(i).toCharArray();
                Set<String> anagram = new LinkedHashSet<>();
                getAnagram(charArray,0,charArray.length-1,anagram);
                List<String> resa = new ArrayList<>();
                for(String b:anagram){
                    if(input.contains(b)){
                        resa.add(b);
                        input.remove(b);
                    }
                }
                result.add(resa);
            }
        }

        return result;

    }

    /**
     * 字符串组合排列
     *
     * @param chars 字符串
     */
    public static void getAnagram(char[] chars, int s, int l, Set<String> res) {
        if (s == l)
            res.add(new String(chars));
        else
            for (int i = s; i <= l; i++) {
                //1.交换
                swap(chars, s, i);
                //2.递归
                getAnagram(chars, s + 1, l, res);
                //2.回溯
                swap(chars, s, i);
            }
    }

    public static void swap(char[] chars, int s, int l) {
        char temp = chars[s];
        chars[s] = chars[l];
        chars[l] = temp;
    }
}
