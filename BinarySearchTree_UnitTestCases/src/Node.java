

public class Node <K extends Comparable<K>, V> 
{

	Node<K,V> left;
	Node<K,V> right;
	Entry<K,V> entry;
	
	public void insert(K key, V value) 
	{
		if(entry == null)
		{
			entry= new Entry<K,V>(key, value);
			return;
		}
		else
		{
			if(key.compareTo(this.getEntry().getKey()) < 0)
			{
				if(this.left == null)
				{
					this.left = new Node<K,V>(key, value);
				}
				else
				{           //insert() below is a recursive function
					this.left.insert(key, value);  //this insert this is for the node not for BST class
				}
			}
			else if(key.compareTo(this.getEntry().getKey()) > 0)
			{
				if(this.right == null)
				{
					this.right = new Node<K,V>(key, value);
				}
				else
				{
					this.right.insert(key, value);  //this insert this is for the node not for BST class
				}
				
			}
		}	
		
	}
	
	public V get(K key) 
	{
		if(key.equals(entry.getKey()))
		{
			return entry.getValue();
		}
		else
		{
			if(key.compareTo(this.getEntry().getKey()) < 0)
			{
				return this.left.get(key);
				
			}
			else if(key.compareTo(this.getEntry().getKey()) > 0)
			{
				return this.right.get(key);
				
			}
			
		}
		return null;
	}


	public Node(K key, V value) 
	{
		entry =new Entry<K,V> (key, value);
				
	}
	public Entry<K, V> getEntry() {
		return entry;
	}
	public void setEntry(Entry<K, V> entry) {
		this.entry = entry;
	}
	public Node<K, V> getLeft() {
		return left;
	}
	public Node<K, V> getRight() {
		return right;
	}
	public void setLeft(Node<K, V> left) {
		this.left = left;
	}
	public void setRight(Node<K, V> right) {
		this.right = right;
	}	
}
