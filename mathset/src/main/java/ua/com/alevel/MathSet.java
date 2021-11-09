package ua.com.alevel;

public class MathSet {

    private MyArrayList list;

    public MathSet() {
        list = new MyArrayList();
    }

    public MathSet(int capacity) {
        list = new MyArrayList(capacity);
    }

    public MathSet(Number[] numbers) {
        list = new MyArrayList();
        for (Number num : numbers) {
            add(num);
        }
    }

    public MathSet(Number[]... numbers) {
        list = new MyArrayList();
        for (Number nums[] : numbers) {
            for (Number num : nums) {
                list.add(num);
            }
        }
    }

    public MathSet(MathSet mathSet) {
        list = mathSet.getList();
    }

    public MathSet(MathSet... mathSets) {
        list = new MyArrayList();
        join(mathSets);
    }

    public void add(Number num) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (num == list.get(i)) {
                index = 1;
                break;
            }
        }
        if (index == 0) {
            list.add(num);
        }
    }

    public void add(Number... num) {
        for (Number n : num) {
            add(n);
        }
    }

    public void join(MathSet ms) {
        for (int i = 0; i < ms.getList().size(); i++) {
            add(ms.getByIndex(i));
        }
    }

    public void join(MathSet... ms) {
        for (MathSet mathSet : ms) {
            join(mathSet);
        }
    }

    public void intersection(MathSet ms) {
        MyArrayList interList = new MyArrayList();
        for (int i = 0; i < list.getArray().length; i++) {
            for (int j = 0; j < ms.getList().getArray().length; j++) {
                if (list.get(i) == ms.getByIndex(j)) {
                    interList.add(ms.getByIndex(j));
                }
            }
        }
        list = interList;
    }

    public void intersection(MathSet... ms) {
        MyArrayList interList = new MyArrayList();
        for (MathSet mathSet : ms) {
            for (int i = 0; i < list.getArray().length; i++) {
                for (int j = 0; j < mathSet.getList().getArray().length; j++) {
                    if (list.get(i) == mathSet.getByIndex(j)) {
                        int index = 0;
                        for (int k = 0; k < interList.size(); k++) {
                            if (mathSet.getByIndex(j) == interList.get(k)) {
                                index = 1;
                                break;
                            }
                        }
                        if (index == 0) {
                            interList.add(mathSet.getByIndex(j));
                        }
                    }
                }
            }
            join(mathSet);
        }
        list = interList;
    }

    public void cut(int index) {
        list.remove(index);
    }

    public void cut(int firstIndex, int lastIndex) {
        for (int i = firstIndex; i <= lastIndex; i++) {
            list.remove(0);
        }
    }

    public void clear() {
        int arrayLength = list.getArray().length;
        for (int i = 0; i < arrayLength; i++) {
            list.remove(0);
        }
    }

    public void clear(Number[] numbers) {
        for (int i = 0; i < list.getArray().length; i++) {
            for (Number num : numbers) {
                if (list.get(i) == num) {
                    list.remove(i);
                }
            }
        }
    }

    public Number getMax() {
        Number max = list.get(0);
        for (int i = 0; i < list.getArray().length; i++) {
            if (max.doubleValue() < list.get(i).doubleValue()) {
                max = list.get(i);
            }
        }
        return max;
    }

    public Double getAverage() {
        double average = 0;
        for (int i = 0; i < list.getArray().length; i++) {
            average += list.get(i).doubleValue();
        }
        return average / list.getArray().length;
    }

    public void sortDesc() {
        MyArrayList sortedList = new MyArrayList();
        Number max = list.get(0);
        int index = 0;
        while (list.getArray().length != 0) {
            for (int i = 0; i < list.getArray().length; i++) {
                if (max.doubleValue() < list.get(i).doubleValue()) {
                    max = list.get(i);
                    index = i;
                }
            }
            sortedList.add(max);
            list.remove(index);
            if (list.getArray().length != 0) {
                max = getMin();
            }
        }
        list = sortedList;
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        MyArrayList sortedList = new MyArrayList();
        for (int i = 0; i < firstIndex; i++) {
            sortedList.add(list.get(0));
            list.remove(0);
        }
        Number max = getMin();
        int index = 0;
        int j = 0;
        int numOfI = lastIndex - firstIndex;
        while (j < lastIndex - firstIndex) {
            for (int i = 0; i <= numOfI; i++) {
                if (max.doubleValue() < list.get(i).doubleValue()) {
                    max = list.get(i);
                    index = i;
                }
            }
            numOfI--;
            sortedList.add(max);
            list.remove(index);
            if (list.getArray().length != 0) {
                max = getMin();
            }
            j++;
        }
        for (int i = 0; i < list.getArray().length; i++) {
            sortedList.add(list.get(i));
        }
        list = sortedList;
    }

    public void sortDesc(Number value) {
        MyArrayList sortedList = new MyArrayList();
        int index = 0;
        for (int i = 0; i < list.getArray().length; i++) {
            if (value == list.get(0)) {
                sortedList.add(list.get(0));
                list.remove(0);
                break;
            }
            sortedList.add(list.get(0));
            list.remove(0);
        }
        Number max = getMin();
        while (list.getArray().length != 0) {
            for (int i = 0; i < list.getArray().length; i++) {
                if (max.doubleValue() < list.get(i).doubleValue()) {
                    max = list.get(i);
                    index = i;
                }
            }
            sortedList.add(max);
            list.remove(index);
            if (list.getArray().length != 0) {
                max = getMin();
            }
        }
        list = sortedList;
    }

    public void sortAsc() {
        MyArrayList sortedList = new MyArrayList();
        Number min = getMax();
        int index = 0;
        while (list.getArray().length != 0) {
            for (int i = 0; i < list.getArray().length; i++) {
                if (min.doubleValue() > list.get(i).doubleValue()) {
                    min = list.get(i);
                    index = i;
                }
            }
            sortedList.add(min);
            list.remove(index);
            if (list.getArray().length != 0) {
                min = getMax();
            }
        }
        list = sortedList;
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        MyArrayList sortedList = new MyArrayList();
        for (int i = 0; i < firstIndex; i++) {
            sortedList.add(list.get(0));
            list.remove(0);
        }
        Number min = getMax();
        int index = 0;
        int j = 0;
        int numOfI = lastIndex - firstIndex;
        while (j < lastIndex - firstIndex) {
            for (int i = 0; i <= numOfI; i++) {
                if (min.doubleValue() > list.get(i).doubleValue()) {
                    min = list.get(i);
                    index = i;
                }
            }
            numOfI--;
            sortedList.add(min);
            list.remove(index);
            if (list.getArray().length != 0) {
                min = getMax();
            }
            j++;
        }
        for (int i = 0; i < list.getArray().length; i++) {
            sortedList.add(list.get(i));
        }
        list = sortedList;
    }

    public void sortAsc(Number value) {
        MyArrayList sortedList = new MyArrayList();
        int index = 0;
        for (int i = 0; i < list.getArray().length; i++) {
            if (value == list.get(0)) {
                sortedList.add(list.get(0));
                list.remove(0);
                break;
            }
            sortedList.add(list.get(0));
            list.remove(0);
        }
        Number min = getMax();
        while (list.getArray().length != 0) {
            for (int i = 0; i < list.getArray().length; i++) {
                if (min.doubleValue() > list.get(i).doubleValue()) {
                    min = list.get(i);
                    index = i;
                }
            }
            sortedList.add(min);
            list.remove(index);
            if (list.getArray().length != 0) {
                min = getMax();
            }
        }
        list = sortedList;
    }

    public Number getMin() {
        Number min = list.get(0);
        for (int i = 0; i < list.getArray().length; i++) {
            if (min.doubleValue() > list.get(i).doubleValue()) {
                min = list.get(i);
            }
        }
        return min;
    }

    public Number getMedian() {
        Number median;
        if (list.getArray().length % 2 == 0) {
            median = (list.get(list.getArray().length / 2).doubleValue() + list.get(list.getArray().length / 2 + 1).doubleValue()) / 2;
        } else {
            median = list.get(list.getArray().length / 2).doubleValue();
        }
        return median;
    }

    public Number[] toArray() {
        return list.getArray();
    }

    public Number[] toArray(int firstIndex, int lastIndex) {
        MyArrayList smallList = new MyArrayList();
        for (int i = firstIndex; i <= lastIndex; i++) {
            smallList.add(list.getArray()[i]);
        }
        return smallList.getArray();
    }

    public MyArrayList getList() {
        return list;
    }

    public Number getByIndex(int index) {
        return list.get(index);
    }
}
