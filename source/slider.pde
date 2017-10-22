class Slider
{
  int xPos;
  int yPos;
  int sliderWidth;
  int increment;
  int value;
  Slider(int initialValue)
  {
    value = initialValue;
  }
  void slider(String text, int x, int y, int w)
  {
    xPos = x;
    yPos = y;
    sliderWidth = w;
    textAlign(LEFT);
    fill(255);
    text(text, xPos, yPos - 10);
    stroke(10);
    strokeWeight(5);
    line(xPos, yPos, sliderWidth, yPos);
    fill(250);
    noStroke();
    rect(xPos + (value * increment), yPos - 5, 5, 10);
  }
  void movement(int i)
  {
    if (mouseX >= xPos && mouseX <= xPos + sliderWidth && mouseY >= yPos - 10 && mouseY <= yPos + 10)
    {
      increment = i;
      value = (mouseX - xPos) / increment;
    }
  }
  int getValue()
  {
    return value;
  }
}