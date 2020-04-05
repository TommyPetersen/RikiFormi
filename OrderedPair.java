package RikiFormi;

public class OrderedPair<T>{

    public T element1;
    public T element2;

    public OrderedPair(){
	element1 = element2 = null;
    }

    public OrderedPair(T _element1, T _element2){
	element1 = _element1;
	element2 = _element2;
    }
    
    public T getFirst(){
	return element1;
    }
    
    public T getSecond(){
	return element2;
    }
}
