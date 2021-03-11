class Block {
  float blockX;
  float blockY;
  float blockSize;
  float colorb;
  PImage red=loadImage("red.png");
  PImage blue=loadImage("blue.png");
  PImage green=loadImage("green.png");
  PImage yellow=loadImage("yellow.png");
  boolean hitten=true;

  Block(float x, float y, float s, float c ) {
    blockX=x;
    blockY=y;
    blockSize=s;
    colorb=c;
  }  
  void show() {
    if (colorb % 4==1)
      image(red, blockX, blockY, blockSize, blockSize/3);
    if (colorb % 4==2)
      image(blue, blockX, blockY, blockSize, blockSize/3);
    if (colorb % 4==3)
      image(green, blockX, blockY, blockSize, blockSize/3);
    if (colorb % 4==0)
      image(yellow, blockX, blockY, blockSize, blockSize/3);
  }

  void checkDown() {
    if (ellipseY-ellipseR<=blockY+blockSize/3 && ellipseX>=blockX && ellipseX<=blockX+blockSize && ellipseY>=blockY+blockSize/3 ) {
      moveY = moveY * (-1.02);
      moveX*=1.01;
      hitten=true;
    }
    // if (  (ellipseY-ellipseR<=blockY+blockSize/3)  &&  (  (ellipseX-ellipseR<=blockX+blockSize)  ||  (ellipseX+ellipseR>=blockX)  )  )moveY = moveY * (-1);
  }
  void checkUp() {
    if (ellipseY+ellipseR>=blockY && ellipseX>=blockX && ellipseX<=blockX+blockSize && ellipseY<=blockY ) {
      moveY = moveY * (-1.02);
      moveX*=1.01;
      hitten=true;
    }
  }
  void checkLeft() {
    if (ellipseX+ellipseR>=blockX && ellipseY>=blockY && ellipseY<=blockY+blockSize/3 && ellipseX<blockX ) {
      moveX = moveX * (-1.02);
      hitten=true;
    }
  }
  void checkRight() {
    if (ellipseX-ellipseR<=blockX+blockSize && ellipseY>=blockY && ellipseY<=blockY+blockSize/3 && ellipseX>blockX+blockSize) {
      moveX = moveX * (-1.02);
      hitten=true;
    }
  }
}
