//It's take a long time to get item from list, so we try array.
public class AList <T>{
    private int size;
    private T[] items;
    public AList(){
        items=(T[])new Object[100];
        size=0;
    }
    private void resize(int capacity){
            T[]a=(T[])new Object[capacity];
            System.arraycopy(items,0,a,0,size);
            items=a;
    }
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = x;
        size++;
    }
    public T getLast(){
        return items[size-1];
    }
    public T get(int i){
        return items[i];
    }
    public T removeLast(){
        T x=getLast();
        size--;
        return x;
    }

}
