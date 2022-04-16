public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int front;
    private int behind;
    public ArrayDeque(){
        items= (T[])new Object[8];
        size=0;
        front=4;
        behind=3;
    }
    private int minus(int a){
        if(a==0){
            return (items.length-1);
        }
        else
            return a-1;
    }

    private int plus(int a){
        if(a==items.length-1){
            return 0;
        }
        else
            return (a+1);
    }
    private void resize(int capacity){
        T[]newItems= (T[])new Object[capacity];
        int newFront=capacity/2;
        if(front>behind&&items[front]==null){
            System.arraycopy(items,behind+1,newItems,newFront,front-behind-1);
        }
        else{
            System.arraycopy(items,behind+1,newItems,newFront, items.length-behind-1);
            System.arraycopy(items,0,newItems,newFront+items.length-behind-1,front);
        }
        behind=capacity/2-1;
        front=newFront+size;
        if(front==capacity){
            front=0;
        }
        items=newItems;
    }
    private void checkSize(){
        if(size==items.length){
            resize(size*2);
        }
        if(4*size<=items.length&&items.length>8){
            resize(items.length/2);
        }
    }
    public void addFirst(T a){
        items[front]=a;
        size++;
        front=plus(front);
        checkSize();
    }
    public void addLast(T a){
        items[behind]=a;
        size++;
        behind=minus(behind);
        checkSize();

    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        int a=behind;
        while(plus(a)!=front){
            a=plus(a);
            System.out.println(items[a]+" ");
        }
    }
    public T removeLast(){
        if(size==0){
            return null;
        }
        T a=items[plus(behind)];
        items[plus(behind)]=null;
        size--;
        behind=plus(behind);
        checkSize();
        return a;
    }
    public T removeFirst(){
        if(size==0){
            return null;
        }
        T a=items[minus(front)];
        items[minus(front)]=null;
        size--;
        front=minus(front);
        checkSize();
        return a;
    }
    public T get(int index){
        return(items[behind+index% items.length+1]);

    }
    public static void main(String[] args) {
        ArrayDeque<Integer> que = new ArrayDeque<Integer>();
        for (int i = 0; i <= 10; i++) {
            que.addFirst(i);
        }
        for (int i = 0; i <= 9; i++){
            que.removeLast();
    }
        que.printDeque();
        //System.out.println(que.removeLast());
        //System.out.println(que.removeFirst());
        //System.out.println(0);
    }

}
