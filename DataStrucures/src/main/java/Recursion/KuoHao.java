package Recursion;

import java.util.*;

public class KuoHao {
    public static void main(String[] args) {
        List<String> strings = generateParenthesis(3);
        System.out.println(strings);
    }

    private static List<String> result = new ArrayList<>();

    private static String[] container;

    private static String[] chars;

    public static List<String> generateParenthesis(int n) {
        //制造容器
        makeContainer(n);
        //返回当前的排列组合
        chars = new String[2*n-2];
        //运行
        run(0);
        return result;
    }

    private static void makeContainer(int n) {
        container = new String[2* n];
        for (int i = 0; i < 2* n; i=i+2) {
            container[i]="(";
            container[i+1]=")";
        }
    }

    public static void run(int n){
        if(n==container.length-2){
            StringBuilder stringBuilder = new StringBuilder();
            for (String aChar : chars) {
                stringBuilder.append(aChar);
            }
            if(stringBuilder.toString()!=""){
                result.add("("+stringBuilder.toString()+")");
            }
            return;
        }
        for (int i = 1; i < container.length-1; i++) {
            chars[n]=container[i];
            run(n+1);
            //container[i]=chars[n];
        }
    }
}
