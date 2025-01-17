package graphpattern;

import java.util.Arrays;

import org.apache.commons.lang.builder.HashCodeBuilder;

import input.StreamEdge;
import struct.Triplet;
import topkgraphpattern.Pattern;
import topkgraphpattern.SubgraphType;

public class ThreeNodeGraphPattern implements Comparable<ThreeNodeGraphPattern>, Pattern {
	int label1;
	int label2;
	int label3;
	boolean isWedge;

	public ThreeNodeGraphPattern(Triplet t) {
		if (t.numEdges == 2) {
			isWedge = true;
			StreamEdge edgeA = t.edgeA;
			int sLabel = edgeA.getSrcLabel();
			int tLabel = edgeA.getDstLabel();

			StreamEdge edgeB = t.edgeB;
			int xLabel = edgeB.getSrcLabel();
			int yLabel = edgeB.getDstLabel();

			if (sLabel == xLabel) {
				label1 = sLabel;
				if (tLabel < yLabel) {
					label2 = tLabel;
					label3 = yLabel;
				} else {
					label2 = yLabel;
					label3 = tLabel;
				}
			} else if (sLabel == yLabel) {
				label1 = sLabel;
				if (tLabel < xLabel) {
					label2 = tLabel;
					label3 = xLabel;
				} else {
					label2 = xLabel;
					label3 = tLabel;
				}
			} else if (tLabel == xLabel) {
				label1 = tLabel;
				if (sLabel < yLabel) {
					label2 = sLabel;
					label3 = yLabel;
				} else {
					label2 = yLabel;
					label3 = sLabel;
				}
			} else if (tLabel == yLabel) {
				label1 = tLabel;
				if (sLabel < xLabel) {
					label2 = sLabel;
					label3 = xLabel;

				} else {
					label2 = xLabel;
					label3 = sLabel;
				}
			}

		} else {
			isWedge = false;
			int[] arr = new int[3];

			arr[0] = t.nodeA.getVertexLabel();
			arr[1] = t.nodeB.getVertexLabel();
			arr[2] = t.nodeC.getVertexLabel();

			Arrays.sort(arr);
			label1 = arr[0];
			label2 = arr[1];
			label3 = arr[2];
		}
	}

	@Override
	public int hashCode() {
		int hashCode = new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
		// if deriving: appendSuper(super.hashCode()).
				append(this.label1).append(this.label2).append(this.label3).append(this.isWedge).toHashCode();
		return hashCode;
	}

	public boolean isWedge() {
		return this.isWedge;
	}

	@Override
	public boolean equals(Object o) {
		ThreeNodeGraphPattern b = (ThreeNodeGraphPattern) o;
		return (this.compareTo(b) == 0);

	}

	public String toString() {
		if (this.isWedge)
			return this.label1 + " " + this.label2 + " " + this.label3 + " " + "wedge";
		else
			return this.label1 + " " + this.label2 + " " + this.label3 + " " + "triangle";
	}

	public int compareTo(ThreeNodeGraphPattern o) {
		if (this.isWedge != o.isWedge) {
			if (this.isWedge)
				return -1;
			else
				return 1;
		}
		if (this.label1 < o.label1) {
			return -1;
		} else if (this.label1 == o.label1) {
			if (this.label2 < o.label2) {
				return -1;
			} else if (this.label2 == o.label2) {
				return this.label3 - o.label3;
			} else
				return 1;
		} else
			return 1;
	}

	@Override
	public SubgraphType getType() {
		if (this.isWedge) {
			return SubgraphType.WEDGE;
		} else {
			return SubgraphType.TRIANGLE;
		}
	}
}
