package RikiFormi;

public class OrderedTriple<T>{

    public T element1;
    public T element2;
    public T element3;

    public OrderedTriple(T _element1, T _element2, T _element3){
	element1 = _element1;
	element2 = _element2;
	element3 = _element3;
    }

    public T getFirst(){
	return element1;
    }
    
    public T getSecond(){
	return element2;
    }
    
    public T getThird(){
	return element3;
    }
}
