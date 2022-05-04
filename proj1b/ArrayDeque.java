public class ArrayDeque<T> implements Deque<T>{
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
            System.arraycopy(items,plus(behind),newItems,newFront,front-plus(behind));
        }
        else{
            System.arraycopy(items,plus(behind),newItems,newFront, items.length-plus(behind));
            System.arraycopy(items,0,newItems,newFront+items.length-plus(behind),front);
        }
        behind=capacity/2-1;
        front=newFront+size;
        if(front==capacity){
            front=0;
        }
        items=newItems;
    }
    private void checkSize(){
        if(size == items.length){
            resize(size*2);
        }
        if(4*size <= items.length&&items.length>8){
            resize(items.length/2);
        }
    }
    @Override
    public void addFirst(T a){
        items[front]=a;
        size++;
        front=plus(front);
        checkSize();
    }
    @Override
    public void addLast(T a){
        items[behind]=a;
        size++;
        behind=minus(behind);
        checkSize();

    }
    @Override
    public boolean isEmpty(){
        return size == 0;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        int a=front;
        while(minus(a) != behind){
            a=minus(a);
            System.out.println(items[a]+" ");
        }
    }
    @Override
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
    @Override
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
    @Override
    public T get(int index){
        return(items[(items.length+front-index-1)% items.length]);

    }

    public static void main(String[] args) {
        ArrayDeque<Integer> array = new ArrayDeque<>();
      for(int i = 1; i <= 100; i++){
          array.addFirst(i);
      }
      for(int i = 1;i <= 60; i++){
          array.removeFirst();
      }
      array.printDeque();
    }

}
