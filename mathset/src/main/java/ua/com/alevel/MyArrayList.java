package ua.com.alevel;

public class MyArrayList {

    private Number[] array;

    public MyArrayList() {
        array = new Number[10];
    }

    public MyArrayList(int capacity) {
        array = new Number[capacity];
    }

    public void add(Number number) {
        if (array[array.length - 1] != null) {
            Number[] numArray = new Number[array.length * 3 / 2 + 1];
            Number[] currentArray = array;
            array = numArray;
            for (int i = 0; i < currentArray.length; i++) {
                array[i] = currentArray[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = number;
                break;
            }
        }
    }

    public Number get(int index) {
        return array[index];
    }

    public void remove(int index) {
        Number[] numArray = new Number[array.length - 1];
        Number[] currentArray = array;
        array = numArray;

        for (int i = 0, j = 0; i < array.length; i++, j++) {
            if (j != index) {
                array[i] = currentArray[j];
            } else {
                i--;
            }
        }
    }

    public int size() {
        return array.length;
    }

    public Number[] getArray() {
        Number[] notNullArray = new Number[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                Number[] currentArray = notNullArray;
                notNullArray = new Number[notNullArray.length + 1];
                for (int j = 0; j < currentArray.length; j++) {
                    notNullArray[j] = currentArray[j];
                }
                notNullArray[notNullArray.length - 1] = array[i];
            }
        }
        return notNullArray;
    }
}
