package model.datastructures;

/**
 * Created by aos on 31.10.2016.
 */
public class Queue<ContentType> {

    private QueueNode head;
    private QueueNode tail;

    /* ----------- Anfang der inneren Klasse ----------- */
    private class QueueNode{
        private ContentType content = null;
        private QueueNode nextNode = null;

        public QueueNode(ContentType content){
            this.content = content;
        }

        public QueueNode getNext(){
            return nextNode;
        }

        public void setNext(QueueNode next){
            this.nextNode = next;
        }

        public ContentType getContent(){
            return content;
        }
    }
    /* ----------- Ende der inneren Klasse ----------- */

    public Queue(){
        head = null;
        tail = null;
    }

    public void enqueue(ContentType content){
        QueueNode newNode = new QueueNode(content);
        if(isEmpty()){
            head = newNode;
            tail = newNode;
        }else {
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    public void dequeue(){
        if(head != tail){
           head = head.getNext();
        }else{
            head = null;
            tail = null;
        }
    }

    public ContentType front(){
        if(!isEmpty()){
            return head.getContent();
        }
        return null;
    }

    public boolean isEmpty(){
        return head == null;
    }
}
