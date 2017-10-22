class setting
{
  int xScr;
  int yScr = 150;
  //GButton scaler = new GButton(this, 100, 10, 200, 20, "Click me");

  void renderTable()
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

  void screenshot()
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