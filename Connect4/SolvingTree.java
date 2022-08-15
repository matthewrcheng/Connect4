import java.util.ArrayList;

public class SolvingTree {
	ArrayList<SolvingTree> children = new ArrayList<SolvingTree>();
	int index;
	
	public SolvingTree(int index) {
		this.index = index;
	}
	
	public void addChild(SolvingTree child) {
		children.add(child);
	}
}
