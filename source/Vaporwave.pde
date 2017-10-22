int cols, rows;
int scale = 50;
int w = 1920 * 5;
int h = 700 * 3;
int colorCornerX;
int colorCornerY = 80;
int colorPaletteDim = 50;
color currentColour = #01f6ff;
float mouse = 0;
float mouseRelease = 0;
float [][] zHeight;
float camera;
Grid g = new Grid();
setting s = new setting();
Slider wireframeWeight = new Slider(1);
Slider wireframeScale = new Slider(4); 
Slider wireframeHeight = new Slider(150);
colorSelect cyan = new colorSelect(#01f6ff);
colorSelect purple = new colorSelect(#EC50F7);
colorSelect pink = new colorSelect(#F750C0);
colorSelect orange = new colorSelect(#F2A01B);

void setup()
{
  size(1920, 700, P3D);
  smooth(8);
  cols = w / scale;

  wireframeWeight.slider("Thickness:", 10, 80, 200);
  wireframeScale.slider("SCALE:", 10, 120, 200);
  wireframeHeight.slider("Height:", 10, 160, 600);  rows = h / scale;
  zHeight = new float [cols][rows];
  camera = 0;
  cursor(CROSS);
}

void draw()
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

void mousePressed()
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

void mouseDragged()
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

void mouseReleased()
{
  mouseRelease = mouseX/2 - w/2;
}