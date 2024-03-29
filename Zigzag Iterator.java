/*Given two 1d vectors, implement an iterator to return their elements alternately.
For example, given two 1d vectors:
  v1 = [1, 2]        
  v2 = [3, 4, 5, 6]
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1, 3, 2, 4, 5, 6]. */
  public class ZigzagIterator {               
     List<List<Integer>> lists = new ArrayList<>();                                 
      int p = 0; // which list                 
      int q = 0; // current index in current list;                                   
      int[] indices = new int[2];                                    
      public ZigzagIterator(List<Integer> v1, List<Integer> v2) {                    
       lists.add(v1);   
       lists.add(v2);                  
       indices[0] = indices[1] = 0;                    
      }                                                                              

      public int next() {                                                            
          int val = lists.get(p).get(q);                                             
          indices[p] = q+1;                                                          
          p = 1-p;                                                                   
          q = indices[p];                                                            
          return val;                                                                
      }                                                                              

      public boolean hasNext() {                                                     
          if(lists.get(p).size() > indices[p]) return true;                          
          else{                                                                      
              p = 1-p;                                                               
              q = indices[p];                                                        
              return lists.get(p).size() > q;                                        
          }                                                                          
      }                                                                              
  }                                                                                  

  /**                                                                                
   * Your ZigzagIterator object will be instantiated and called as such:             
   * ZigzagIterator i = new ZigzagIterator(v1, v2);                                  
   * while (i.hasNext()) v[f()] = i.next();                                          
   */
