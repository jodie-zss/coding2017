package LRU;

/**
 * 用双向链表实现LRU算法
 * @author liuxin
 *
 */
public class LRUPageFrame {
	
	private static class Node {
		
		Node prev;
		Node next;
		int pageNum;


		Node(int pageNum){
			this.pageNum=pageNum;
		}
	}

	private int capacity;
	private int count;

	
	private Node first;// 链表头
	private Node last;// 链表尾

	
	public LRUPageFrame(int capacity) {
		
		this.capacity = capacity;
		count=0;
	}

	
	public void access(int pageNum) {
		Node node=findNode(pageNum);

		if(node==null){//原来没有 就要加上
			addFirst(new Node(pageNum));
		}else {//原来有 改变顺序 提到第一个
			pullFirst(node);
		}
	
	}

	private void pullFirst(Node node) {
		if(node==first){//本来就是第一个了
			return;
		}

		//前连后
		node.prev.next=node.next;
		//后连前
		if(node!=last){
			node.next.prev=node.prev;
		}else {
			//去尾
			Node newLast=last.prev;
			newLast.next=null;
			last=newLast;
		}

		//放最前面
		first.prev=node;
		node.next=first;

		first=node;
		node.prev=null;

	}

	private void addFirst(Node node) {
		count++;
		//本来是空的
		if(count==1){  //头尾都是它
			first=node;
			last=node;
			return;
		}

		//加头
		first.prev=node;
		node.next=first;
		first=node;

		if(count>capacity){
			//去尾
			Node newLast=last.prev;
			newLast.next=null;
			last=newLast;
			count--;
		}
	}


	private Node findNode(int pageNum) {
		Node node=first;
		while (node!=null){
			if(node.pageNum==pageNum){
				return node;
			}
			node=node.next;
		};

		return null;
	}


	public String toString(){
		StringBuilder buffer = new StringBuilder();
		Node node = first;
		while(node != null){
			buffer.append(node.pageNum);			
			
			node = node.next;
			if(node != null){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
}
