package narytree;

import java.util.ArrayList;
import java.util.List;

public class N_AryTree<E>{
    public E data;
    public List<N_AryTree<E>> children;

    public N_AryTree(){
        super();
        this.children= new ArrayList<>();
    }
    
    public N_AryTree(E data){
        this();
        setData(data);
    }
    
    public N_AryTree(E data, List<N_AryTree<E>> children){
        this();
        setData(data);
        setChildren(children);
    }
    
    public void setData(E data){
        this.data=data;
    }
    
    public List<N_AryTree<E>> getChildren(){
        return this.children;
    }
    
    public int getNumberOfChildren(){
        return getChildren().size();
    }
    
    public boolean hasChildren(){
        return (getNumberOfChildren()>0);
    }
    
    public void setChildren(List<N_AryTree<E>> children){
        this.children=children;
    }
    
    public void addChild(N_AryTree<E> child){
        this.children.add(child);
    }
    
    public void addChildAt(int index, N_AryTree<E> child) throws IndexOutOfBoundsException{
         this.children.add(index,child);
    }
    
    public void removeChildren(){
        this.children=new ArrayList<>();
    }
    
    public void removeChildAt(int index, N_AryTree<E> child) throws IndexOutOfBoundsException{
         this.children.remove(index);
    }
    
    public N_AryTree<E> getChildAt (int index) throws IndexOutOfBoundsException{
         return this.children.get(index);
    }
    
    public E getData(){
        return this.data;
    }
    
    public boolean equals(N_AryTree<E> node){
            return node.getData().equals(getData());
    }
       
    @Override
    public String toString(){
        return getData().toString();
    }
    
    public void print(String prefix, boolean isTail, N_AryTree tree) {
      System.out.println(prefix + (isTail ? "└── " : "├── ") + tree.getData());
      for (int i = 0; i < tree.getNumberOfChildren() - 1; i++) {
          print(prefix + (isTail ? "    " : "│   "), false, tree.getChildAt(i));
      }
      if (tree.getNumberOfChildren() > 0) {
          print(prefix + (isTail ? "    " : "│   "), true, tree.getChildAt(tree.getNumberOfChildren() - 1));
      }
    }
}