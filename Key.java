public class Key<I,E> {
    private I index;
    private E value;
    public Key(I index,E value){
        this.index = index;
        this.value = value;
    }
    public Key(E value){
        this.value = value;
    }

    public I getIndex() {
        return index;
    }

    public void setIndex(I index) {
        this.index = index;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
    public String toString() {
        return "The index: "+index+" holds the value: "+value;
    }
}
