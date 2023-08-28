package Stack;

import java.util.Stack;

public class Calculator {
    /**
     * 数字栈
     */
    private Stack<Integer> numstack;

    /**
     * 符号栈
     */
    private Stack<Character> operaStack;

    /**
     * 计算表达式
     */
    private String expression;

    /**
     * 表达式指针
     */
    private int index;

    /**
     * 字符串的长度
     */
    private int numLength;

    public Calculator(String expression) {
        this.numstack = new Stack<>();
        this.operaStack = new Stack<>();
        this.expression = expression;
    }

    public int run(){
        //结果
        int result = 0;
        //扫描表达式
        while(true){
            char input = expression.substring(index, index + 1).charAt(0);
            //判断是否为运算符
            if(isOpera(input)){
                //数字的长度重置
                numLength=0;
                //如果运算符栈为空入栈
                if(operaStack.isEmpty()){
                    operaStack.push(input);
                }else{
                    //如果不为空判
                    //判断当前符号的等级是否大于栈顶符号的等级直接入栈
                    if(judgeOpera(input)>judgeOpera(operaStack.peek())){
                        operaStack.push(input);
                    }else{
                        //从数字栈中取出两个数字，符号栈取出一个符号进行运算，将结果再入数字栈
                        Integer num1 = numstack.pop();
                        Integer num2 = numstack.pop();
                        Character pop = operaStack.pop();
                        //进行计算
                        int cal = cal(num2, num1, pop);
                        //入数字栈
                        numstack.push(cal);
                        //符号入栈
                        operaStack.push(input);
                    }
                }
            }else{
                //如果为数字
                //存入数字栈中
                if(numLength==0){
                    numstack.push(input-48);
                }else{
                    int pop = (int) (numstack.pop()*Math.pow(10,numLength));
                    numstack.push(pop+(input-48));
                }
                numLength=1;
            }
            index++;
            if(index==expression.length()){
                break;
            }
        }

        //计算栈内所有结果
        while (true){
            Integer num1 = numstack.pop();
            if(numstack.isEmpty()){
                result = num1;
                break;
            }
            Integer num2 = numstack.pop();
            Character opera = operaStack.pop();
            numstack.push(cal(num2,num1,opera));
        }

        return result;
    }

    /**
     * 计算方法
     *
     * @param num1  数字1
     * @param num2  数字2
     * @param opera 计算符号
     * @return 结果值
     */
    private int cal(int num1, int num2, int opera) {
        switch (opera) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                throw new RuntimeException("运算符符号有误!");
        }
    }

    /**
     * 判断预算符号的等级
     *
     * @param opera 运算符号
     * @return 运算符号的等级
     */
    private int judgeOpera(int opera) {
        if (opera == '+' || opera == '-') {
            return 1;
        } else if (opera == '*' || opera == '/') {
            return 2;
        } else {
            throw new RuntimeException("运算符符号有误!");
        }
    }

    /**
     * 判断是否为运算符号
     *
     * @param input 输入的字符串
     * @return true:是运算符 false:不是运算符
     */
    private boolean isOpera(int input) {
        return input == '+' || input == '-' || input == '*' || input == '/';
    }


}
