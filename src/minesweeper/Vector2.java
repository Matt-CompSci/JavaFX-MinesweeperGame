package minesweeper;

public class Vector2
{
	private int x, y;

	public Vector2(int newX, int newY)
	{
		x = newX;
		y = newY;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Vector2 add(Vector2 v)
	{
		return new Vector2(x + v.getX(), y + v.getY());
	}

}
