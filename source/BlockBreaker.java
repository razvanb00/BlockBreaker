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

public class BlockBreaker extends PApplet {

float rectH=15;
float rectW=65;
float rectY;
float rectX;
float move=0;
float moveY=-1.5f;
float moveX=-1.5f;
float ellipseX;
float ellipseY;
float ellipseR=6;
int bNum=16;
float bSize;
boolean hit=false;
boolean gameEnded=false;
float bRows=6;
PImage back;
PImage paddle;
PImage gameOver;
PImage tryAgain;
PImage easy;
PImage okay;
PImage hard;
PImage won;
PImage won1;
float finish=0;
boolean gameStarted=false;
Block[] blocks = new Block[1000];

public void setup() {
  

  rectX=(width+rectW)/2;
  rectY=height-20;
  ellipseX=width/2;
  ellipseY=height/2;
  frameRate(60);
  easy =loadImage("easy.png");
  hard =loadImage("hard.png");
  okay =loadImage("okay.png");
  back=loadImage("Background.jpg");
  paddle = loadImage("Paddle.png");
  gameOver=loadImage("game.png");
  tryAgain=loadImage("ta.png");
  won=loadImage("won.png");
  won1=loadImage("won2.png");
  bSize=width/bNum;
  for (int j=0; j<bRows; ++j) {
    for (int i=0; i<bNum; ++i) {
      blocks[j * bNum +i ]=new Block((i*bSize), (j*bSize/3), bSize, j);
      //println((i*bSize)+10, (j*bSize)+20, bSize-10, j * bNum +i);
    }
  }
}


public void draw() {
  if (gameStarted&& gameEnded==false) {
    imageMode(CORNER);
    background(0);
    image(back, 1, 1);
    fill(255);

    update();
    check();
    stroke(0);
    //rect(rectX, rectY, rectW, rectH);
    image (paddle, rectX, rectY, rectW, rectH);
    fill(200, 200, 50);
    stroke(0);
    ellipse(ellipseX, ellipseY, ellipseR*2, ellipseR*2);

    finish=0;
    for (int j=0; j<bRows; ++j) {
      for (int i=0; i<bNum; ++i) {
        if (blocks[j * bNum +i].hitten==false) {
          blocks[j * bNum +i].show();
          blocks[j * bNum +i].checkUp();
          blocks[j * bNum +i].checkDown();
          blocks[j * bNum +i].checkLeft();
          blocks[j * bNum +i].checkRight();
        } else {
          finish++;
          //iubb
        }
      }
    }

    if (finish==bNum*bRows)gameEnded=true;
    //println(finish);
    //println(bNum*bRows);
    if (mori()) {

      moveY=0;
      moveX=0;
      imageMode(CENTER);
      image(tryAgain, width/2, height*4/5, 250, 150);
      image(won, width/2, height/3, 350, 250);
    }
  } else if (gameEnded==true) {
    imageMode(CORNER);
    background (0);
    image(back, 1, 1);
    imageMode(CENTER);
    image(won1, width/2, height/3, 400, 250);
    image(tryAgain, width/2, height*4/5, 250, 150);
    moveY=0;
    moveX=0;
  } else {
    imageMode(CORNER);
    background(0);
    image(back, 1, 1);
    imageMode(CENTER);
    image(easy, width/2, height/4, 120, 50);
    image(okay, width/2, height*2/4, 120, 50);
    image(hard, width/2, height*3/4, 120, 50);
  }
}

public void keyPressed() {
  if (keyCode == LEFT)move=-5;
  else if (keyCode == RIGHT)move=5;
}

public void keyReleased() {
  move=0;
}

public void update() {
  ellipseY=ellipseY+moveY;
  ellipseX=ellipseX+moveX;
  rectX=constrain(rectX+move, 0, width-rectW-1);
}

public void check() {

  if (ellipseX-ellipseR <= 0 || ellipseX+ellipseR >= width)moveX = moveX * (-1) ;
  else if (ellipseY-ellipseR<=0)moveY = moveY * (-1);
  else if (ellipseY+ellipseR>=rectY && ellipseX>=rectX && ellipseX<=rectX+rectW ) {
    if (hit==false) {
      moveY = moveY * (-1);
      hit=true;
    }
  } else hit = false;
}


public boolean mori() {
  boolean ok=false;
  if (ellipseY +ellipseR> height )ok=true;
  return ok;
} 

public void mousePressed() {
  //TRY AGAIN CLICK
  if (mori()) {
    if (mouseX>width/2-125 && mouseX<width/2+125 && mouseY<height*4/5+75 && mouseY>height*4/5-75) {
      ellipseX=width/2;
      ellipseY=height/2;
      moveY=-1.5f;
      moveX=1.5f;
      gameStarted=false;
    }
  } else if (gameStarted==false) {

    //EASY CLICK
    if (mouseX>width/2-60 && mouseX<width/2+60 && mouseY>height/4-25 && mouseY<height/4+25) {
      gameStarted=true;
      bRows=2;
      for (int j=0; j<bRows; ++j)
        for (int i=0; i<bNum; ++i)
          blocks[j * bNum +i].hitten=false;
      ellipseX=width/2;
      ellipseY=height/2;
      moveY=-1.5f;
      moveX=-1.5f;
    }
    //OKAY CLICK
    else if (mouseX>width/2-60 && mouseX<width/2+60 && mouseY>height*2/4-25 && mouseY<height*2/4+25) {
      gameStarted=true;
      bRows=4;
      for (int j=0; j<bRows; ++j)
        for (int i=0; i<bNum; ++i)
          blocks[j * bNum +i].hitten=false;
      ellipseX=width/2;
      ellipseY=height/2;
      moveY=-1.5f;
      moveX=-1.5f;
    }
    //HARD CLICK
    else if (mouseX>width/2-60 && mouseX<width/2+60 && mouseY>height*3/4-25 && mouseY<height*3/4+25) {
      gameStarted=true;
      bRows=6;
      for (int j=0; j<bRows; ++j)
        for (int i=0; i<bNum; ++i)
          blocks[j * bNum +i].hitten=false;
      ellipseX=width/2;
      ellipseY=height/2;
      moveY=-1.5f;
      moveX=-1.5f;
    }
  } else if (gameEnded==true) {
    if (mouseX>width/2-125 && mouseX<width/2+125 && mouseY<height*4/5+75 && mouseY>height*4/5-75) {
      gameStarted=false;
      gameEnded=false;
    }
  } //else {
    //for (int j=0; j<bRows; ++j) 
      //for (int i=0; i<bNum; ++i) 
        //blocks[j * bNum +i].hitten=true;
  //}
}
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
  public void show() {
    if (colorb % 4==1)
      image(red, blockX, blockY, blockSize, blockSize/3);
    if (colorb % 4==2)
      image(blue, blockX, blockY, blockSize, blockSize/3);
    if (colorb % 4==3)
      image(green, blockX, blockY, blockSize, blockSize/3);
    if (colorb % 4==0)
      image(yellow, blockX, blockY, blockSize, blockSize/3);
  }

  public void checkDown() {
    if (ellipseY-ellipseR<=blockY+blockSize/3 && ellipseX>=blockX && ellipseX<=blockX+blockSize && ellipseY>=blockY+blockSize/3 ) {
      moveY = moveY * (-1.02f);
      moveX*=1.01f;
      hitten=true;
    }
    // if (  (ellipseY-ellipseR<=blockY+blockSize/3)  &&  (  (ellipseX-ellipseR<=blockX+blockSize)  ||  (ellipseX+ellipseR>=blockX)  )  )moveY = moveY * (-1);
  }
  public void checkUp() {
    if (ellipseY+ellipseR>=blockY && ellipseX>=blockX && ellipseX<=blockX+blockSize && ellipseY<=blockY ) {
      moveY = moveY * (-1.02f);
      moveX*=1.01f;
      hitten=true;
    }
  }
  public void checkLeft() {
    if (ellipseX+ellipseR>=blockX && ellipseY>=blockY && ellipseY<=blockY+blockSize/3 && ellipseX<blockX ) {
      moveX = moveX * (-1.02f);
      hitten=true;
    }
  }
  public void checkRight() {
    if (ellipseX-ellipseR<=blockX+blockSize && ellipseY>=blockY && ellipseY<=blockY+blockSize/3 && ellipseX>blockX+blockSize) {
      moveX = moveX * (-1.02f);
      hitten=true;
    }
  }
}
  public void settings() {  size (641, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BlockBreaker" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
