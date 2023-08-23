package ArrayList;

public class index {
    public static void main(String[] args) {
        MyArrayList<String> stringMyArrayList = new MyArrayList<>(0);
        for (int i = 0; i < 11; i++) {
            stringMyArrayList.add(i+"");
        }
        Object[] elementData = stringMyArrayList.getElementData();
        for (Object elementDatum : elementData) {
            System.out.println(elementDatum);
        }
    }
}