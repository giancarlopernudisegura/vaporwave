class Grid
{
  void renderTerrain()
  {
    pushMatrix();
    hint(ENABLE_DEPTH_TEST);
    camera -= 0.01;
    int minHeight = -(wireframeHeight.getValue());
    int maxHeight = -1 * minHeight;
    float yoff = camera;
    for (int y = 0; y < rows; y++)
    {
      float xoff = 0;
      for (int x = 0; x < cols; x++)
      {
        zHeight[x][y] = map(noise(xoff, yoff), 0, 1, minHeight, maxHeight);
        xoff +=0.5;
      }
      yoff +=0.5;
    }
    fill(0);
    stroke(currentColour);
    strokeWeight(wireframeWeight.getValue());
    translate(0, h/3);
    rotateX(1.5);
    translate(mouse - w/2, -1900);
    scale(wireframeScale.getValue()/4.0);
    for (int y = 0; y < rows - 1; y++)
    {
      beginShape(TRIANGLE_STRIP);
      for (int x = 0; x < cols; x++)
      {
        vertex(x * scale, y * scale, zHeight[x][y]);
        vertex(x * scale, (y + 1) * scale, zHeight[x][y+1]);
      }
      endShape();
    }
    popMatrix();
  }
}