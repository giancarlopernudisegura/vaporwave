class colorSelect
{
  int xPos;
  int yPos;
  int dimension;
  color colour;

  colorSelect(color c)
  {
    colour = c;
  }

  void render(int x, int y, int d)
  {
    xPos = x;
    yPos = y;
    dimension = d;
    noStroke();
    fill(colour);
    rect(xPos, yPos, dimension, dimension);
    stroke(0);
  }

  void select()
  {
    if (mouseX > xPos && mouseX < xPos + dimension && mouseY > yPos && mouseY < yPos + dimension)
    {
      currentColour = colour;
    }
  }
}