package TimeComplexity.Demo;

public class cut_rope {
    public static void main(String[] args) {
        System.out.println(cutMultiplied(8));
    }

    /**
     * 1.长度为a的绳子剪出n段绳子，求每一段绳子长度的相乘取最大值。
     * 2.每一段可以剪去 1(最差) 或 2(其次) 或 3(最优)
     * 3.a<=3时 return 1*(n-1)
     * 4.a>3时 a=n*3+b (n=3整数倍 b为小于3的余数)
     *   那么 b=0时  return 3^n
     *   那么 b=1时  return 3^(n-1)*2*2
     *   那么 b=2时  return 3^n*2
     * 5.时间复杂度O(1)
     */
    public static long cutMultiplied(int a) {
        if (a <= 3) return a - 1;
        int n = a / 3;
        int b = a % 3;
        if (b == 0) return (long) Math.pow(3, n);
        if (b == 1) return (long) Math.pow(3, n - 1) * 4;
        return (long) Math.pow(3, n) * 2;
    }
}
