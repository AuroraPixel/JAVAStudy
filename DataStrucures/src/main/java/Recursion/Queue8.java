package Recursion;

public class Queue8 {
    private static int[] queue = new int[8];
    private static int count = 0;

    public static void main(String[] args) {
        run(0);
        System.out.println(count);
    }

    public static void run(int n){
        if(n==8){
            count++;
            print();
            return;
        }
        for (int i = 0; i < 8; i++) {
            queue[n]=i;
            if(check(n)){
                run(n+1);
            }
        }
    }

    public static boolean check(int n){

        for (int i = 0; i < n; i++) {
            //检查是否在同一列
            if(queue[i]==queue[n]){
                return false;
            }
            //检查是否在同一斜线
            if(Math.abs(n-i)==Math.abs(queue[n]-queue[i])){
                return false;
            }
        }

        return true;
    }

    public static void print(){
        for (int j : queue) {
            System.out.print(j + " ");
        }
        System.out.println();
    }


}
