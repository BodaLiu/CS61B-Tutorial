public class LinkedListDeque <T>{
    private class Node{
        public T item;
        public Node next;
        public Node before;
        public Node (T i,Node n,Node b){
            item=i;
            next=n;
            before=b;
        }
    }
    private Node sentinel;
    private int size;
    public LinkedListDeque(){
        sentinel=new Node(null,null,null);
        sentinel.before=sentinel;
        sentinel.next=sentinel;
        size=0;
    }
    public void addFirst(T x){
        sentinel.next=new Node(x,sentinel.next,sentinel);
        size++;
    }
    public void addLast(T x){
        sentinel.before=new Node(x,sentinel,sentinel.before);
        size++;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        Node i=sentinel;
        while(i.next!=sentinel){
            i=i.next;
            System.out.println(i.item+" ");
        }
    }
    public T removeFirst(){
        if(size==0){
            return null;
        }
        Node i=sentinel.next;
        sentinel.next=sentinel.next.next;
        size--;
        return i.item;
    }
    public T removeLast(){
        if(size==0){
            return null;
        }
        Node i=sentinel.before;
        sentinel.before=sentinel.before.before;
        size--;
        return i.item;
    }
    public T get(int index){
        Node i=sentinel;
        if(index<0||index>=size)
        {
            return null;
        }
        for(int j=0;j<=index;j++){
            i=i.next;
        }
        return i.item;
    }
    private T getRecursiveHelper(Node n,int index){
        if(index==0){
            return n.item;
        }
        return getRecursiveHelper(n.next,index-1);
    }
    public T getRecursive(int index)
    {
         return getRecursiveHelper(sentinel.next,index);
    }
}
