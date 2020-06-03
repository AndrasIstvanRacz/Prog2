class Rectangle {
	private int _length;
	private int _breadth;

	public int getArea() {
		throw new UnsupportedOperationException();
	}

	public void setLength(int aLength) {
		this._length = aLength;
	}

	public int getLength() {
		return this._length;
	}

	public void setBreadth(int aBreadth) {
		this._breadth = aBreadth;
	}

	public int getBreadth() {
		return this._breadth;
	}
}