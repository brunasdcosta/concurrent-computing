package spark;

import java.io.Serializable;

public class Info implements Serializable {

	private static final long serialVersionUID = 4266673836697293267L;

	private String word;
	private Integer distance;

	public Info(String word, Integer distance) {
		super();
		this.word = word;
		this.distance = distance;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

}