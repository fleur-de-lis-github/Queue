/*Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
For example,
MovingAverage m = new MovingAverage(3);
m.next(1) = 1
m.next(10) = (1 + 10) / 2
m.next(3) = (1 + 10 + 3) / 3
m.next(5) = (10 + 3 + 5) / 3 */

public class MovingAverage {

    /** Initialize your data structure here. */
    Queue<Integer> data = new LinkedList<>();
    int size;
    int sum;
    public MovingAverage(int size) {
        this.size = size;
        this.sum = 0;
    }

    public double next(int val) {
        if(data.size() >= size){
            data.offer(val);
            int head = data.poll();
            sum = sum - head + val;

            return sum/size;            
        }else{
            sum += val;
            data.offer(val);
            return sum / data.size();
        }
    }
}

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */
