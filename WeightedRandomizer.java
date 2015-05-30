package src;
/** This class allows for flexible weighted randomization
 * 
 * @author Nate Young
 * 
 */
import java.util.ArrayList;
import java.util.List;
public class WeightedRandomizer {
	private List<Double> weights;
	
	/**Constructs a new WeightedRandomizer instance given a list of weights
	 * 
	 * @param weights The list of weights for the various choices that the randomizer will choose between.
	 */
	public WeightedRandomizer(List<Double> weights) {
		super();
		this.weights = weights;
	}
	
	/**Constructs a new WeightedRandomizer instance given an arbitrary number of <code>double</code> weights
	 * 
	 * @param weights The weights for the various choices that the randomizer will choose between.
	 */
	public WeightedRandomizer(double... weights){
		this.weights = new ArrayList<Double>();
		for(double w : weights){
			this.weights.add(w);
		}
	}
	
	/**
	 * Default Constructor: creates a new empty WeightedRandomizer instance
	 */
	public WeightedRandomizer(){
		this.weights = new ArrayList<Double>();
	}
	
	/**
	 * Appends a new weight to the list of weights
	 * @param weight The weight to add
	 * @return This WeightedRandomizer (for chaining purposes)
	 */
	public WeightedRandomizer add(double weight){
		this.weights.add(weight);
		return this;
	}
	
	/**
	 * Returns the number of indices/weights in this WeightedRandomizer instance
	 * @return The number of indices/weights
	 */
	public int size(){
		return this.weights.size();
	}
	/**
	 * Returns a weight at a specific index
	 * @param index The index to use
	 * @return The weight at the provided index
	 */
	public double get(int index){
		return this.weights.get(index);
	}
	
	/**
	 * Sets the weight at the given index to the given value
	 * @param index The index of the weight to change
	 * @param weight The weight to change the index to
	 */
	public void set(int index, double weight){
		this.weights.set(index, weight);
	}
	
	/**
	 * Returns the sum of all the weights of the different indices
	 * @return The sum of all of the weights
	 */
	public double sum(){
		double sum = 0;
		for(double w: this.weights){
			sum += w;
		}
		return sum;
	}
	
	/**
	 * Picks a random index out of however many this instance contains, taking weights into account.
	 * @return A random index (0...size()) after taking weights into account
	 */
	public int pickIndex(){
		double sum = 0, rand = Math.random()*this.sum();
		int i=0;
		while(sum<rand){
			sum += this.weights.get(i);
			i++;
		}
		return i;
	}
	
	public static void main(String[] args){
		WeightedRandomizer wr = new WeightedRandomizer();
		wr.add(.5);
		wr.add(5);
		wr.add(3);
		System.out.println("sum: "+wr.sum());
		System.out.println("size: "+wr.size());
		for(int i=0; i<10; i++){
			System.out.println("random index: "+wr.pickIndex());
		}

	}
}
