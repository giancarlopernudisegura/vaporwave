import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Vaporwave extends PApplet {

int cols, rows;
int scale = 50;
int w = 1920 * 5;
int h = 700 * 3;
int colorCornerX;
int colorCornerY = 80;
int colorPaletteDim = 50;
int currentColour = 0xff01f6ff;
float mouse = 0;
float mouseRelease = 0;
float [][] zHeight;
float camera;
Grid g = new Grid();
setting s = new setting();
Slider wireframeWeight = new Slider(1);
Slider wireframeScale = new Slider(4); 
Slider wireframeHeight = new Slider(150);
colorSelect cyan = new colorSelect(0xff01f6ff);
colorSelect purple = new colorSelect(0xffEC50F7);
colorSelect pink = new colorSelect(0xffF750C0);
colorSelect orange = new colorSelect(0xffF2A01B);

public void setup()
{
  
  
  cols = w / scale;

  wireframeWeight.slider("Thickness:", 10, 80, 200);
  wireframeScale.slider("SCALE:", 10, 120, 200);
  wireframeHeight.slider("Height:", 10, 160, 600);  rows = h / scale;
  zHeight = new float [cols][rows];
  camera = 0;
  cursor(CROSS);
}

public void draw()
{
  background(0);
  g.renderTerrain();
  s.renderTable();
  wireframeWeight.slider("Thickness:", 10, 80, 200);
  wireframeScale.slider("SCALE:", 10, 120, 200);
  wireframeHeight.slider("Height:", 10, 160, 400);
  colorCornerX = width/2;
  cyan.render(colorCornerX, colorCornerY, colorPaletteDim);
  purple.render(colorCornerX + colorPaletteDim, colorCornerY, colorPaletteDim);
  pink.render(colorCornerX + colorPaletteDim * 2, colorCornerY, colorPaletteDim);
  orange.render(colorCornerX + colorPaletteDim * 3, colorCornerY, colorPaletteDim);
}

public void mousePressed()
{
  s.screenshot();
  wireframeWeight.movement(10);
  wireframeScale.movement(10);
  wireframeHeight.movement(1);
  cyan.select();
  purple.select();
  pink.select();
  orange.select();
}

public void mouseDragged()
{
  s.screenshot();
  wireframeWeight.movement(10);
  wireframeScale.movement(10);
  wireframeHeight.movement(1);
  if (mouseY > 300)
  {
    mouse += mouseX - pmouseX;
  }
}

public void mouseReleased()
{
  mouseRelease = mouseX/2 - w/2;
}
class colorSelect
{
  int xPos;
  int yPos;
  int dimension;
  int colour;

  colorSelect(int c)
  {
    colour = c;
  }

  public void render(int x, int y, int d)
  {
    xPos = x;
    yPos = y;
    dimension = d;
    noStroke();
    fill(colour);
    rect(xPos, yPos, dimension, dimension);
    stroke(0);
  }

  public void select()
  {
    if (mouseX > xPos && mouseX < xPos + dimension && mouseY > yPos && mouseY < yPos + dimension)
    {
      currentColour = colour;
    }
  }
}
class Grid
{
  public void renderTerrain()
  {
    pushMatrix();
    hint(ENABLE_DEPTH_TEST);
    camera -= 0.01f;
    int minHeight = -(wireframeHeight.getValue());
    int maxHeight = -1 * minHeight;
    float yoff = camera;
    for (int y = 0; y < rows; y++)
    {
      float xoff = 0;
      for (int x = 0; x < cols; x++)
      {
        zHeight[x][y] = map(noise(xoff, yoff), 0, 1, minHeight, maxHeight);
        xoff +=0.5f;
      }
      yoff +=0.5f;
    }
    fill(0);
    stroke(currentColour);
    strokeWeight(wireframeWeight.getValue());
    translate(0, h/3);
    rotateX(1.5f);
    translate(mouse - w/2, -1900);
    scale(wireframeScale.getValue()/4.0f);
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
class setting
{
  int xScr;
  int yScr = 150;
  //GButton scaler = new GButton(this, 100, 10, 200, 20, "Click me");

  public void renderTable()
  {
    hint(DISABLE_DEPTH_TEST);
    fill(100);
    noStroke();
    textAlign(LEFT);
    rect(0, 0, width, 200);
    fill(255);
    text(mouseX+","+mouseY, 1, 11);
    //stroke(250, 0, 0);
    //line(mouseX, 0, mouseX, height);
    //line(0, mouseY, width, mouseY);
    xScr = width/2;
    rect(xScr, yScr, 100, 30);
    fill(0);
    textAlign(CENTER, CENTER);
    text("Screenshot", (xScr*2+100)/2, yScr + 15);
  }

  public void screenshot()
  {
    if (mouseX > xScr && mouseX < (xScr + 100) && mouseY > yScr && mouseY < (yScr + 30))
    {
      PImage screenshotImage = createImage(66, 66, RGB);
      screenshotImage.loadPixels();
      screenshotImage = get(0, 250, width, height - 250);
      screenshotImage.updatePixels();
      screenshotImage.save("/screenshots/screen"+"-"+year()+"-"+month()+"-"+day()+"-"+hour()+minute()+second()+".png");
      text("screenshot taken", width/2, 100);
      //save("screenshot"+"-"+year()+"-"+month()+"-"+day()+"-"+hour()+minute()+second()+".png");
    }
  }
}
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
  public void slider(String text, int x, int y, int w)
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
  public void movement(int i)
  {
    if (mouseX >= xPos && mouseX <= xPos + sliderWidth && mouseY >= yPos - 10 && mouseY <= yPos + 10)
    {
      increment = i;
      value = (mouseX - xPos) / increment;
    }
  }
  public int getValue()
  {
    return value;
  }
}
  public void settings() {  size(1920, 700, P3D);  smooth(8); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Vaporwave" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
