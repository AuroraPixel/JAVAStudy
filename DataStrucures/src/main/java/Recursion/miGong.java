package Recursion;

public class miGong {
    public static void main(String[] args) {
        //创建8行7列地图
        int[][] map = new int[8][7];
        //设置围墙
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        map[3][1] = 1;
        map[3][2] = 1;
        map[2][5] = 1;
        map[3][4] = 1;

        run(map, 1, 1);

        //数据展示
        for (int[] ints : map) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    /**
     * 1.[6,5]为终点停止
     * 2.0：表示未走过 2：通路为可以走 3：已走过走不通，但是走不通
     * 3.行走策略右下左上
     *
     * @param map 地图
     * @param y   行
     * @param x   列
     */
    private static boolean run(int[][] map, int y, int x) {
        //1.[6,5]为终点停止
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[y][x] == 0) {
                map[y][x] = 2;
                //开始行走右
                if (run(map, y, x + 1)) {
                    return true;
                } else if (run(map, y + 1, x)) {
                    //开始行走下
                    return true;
                } else if (run(map, y, x - 1)) {
                    //开始行走左
                    return true;
                } else if (run(map, y - 1, x)) {
                    //开始行走上
                    return true;
                } else {
                    //走不通
                    map[y][x] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
