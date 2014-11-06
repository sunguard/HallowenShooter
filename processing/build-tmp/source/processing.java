import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class processing extends PApplet {

/* Minim Library */

Minim minim;
AudioPlayer bgm;

/* Image variables - Backgrounds */
PImage startImage; //start_screen.png
PImage[] hillImages = new PImage[7]; //background/layer_a_[0-6].png
PImage[] skyImages = new PImage[6]; //background/layer_b_[0-5].png
PImage moonImage; //background/moon.png

/* Image variables - Obstacles */
//PImage[] obstacleImages = new PImage[];

/* Image variables - Monsters */
PImage[] monsterImages = new PImage[4];

/* Image variables - Candies */
PImage[] candyImages = new PImage[4];

/* Image variables - Buttons */
PImage[][] buttonImages = new PImage[2][2];

/* Image variables - Target */
PImage[] targetImage = new PImage[2];

/* Serial variables */
int lf = 10; //ascii code for 'line feed'

/* Object variables */
Stage stage;
Hill hill;
Moon moon;
Sky sky;
Target target;
Monster[] monsters;// = new Monster[monsterMap.length];

/* Button variables */
Button startButton;
Button aboutButton;

/* Map variables */
float[][] monsterMap = {
	{1, 64, 170, 7, 8},
	{0, 200, 200, 8, 20},
	{3, 150, 90, 4, -16},
	{2, 290, 160, 4, 10},
	{1, 340, 220, 5, -7},
	{3, 440, 130, 3, -30},
	{2, 500, 170, 6, -20},
	{2, 570, 60, 4, 18},
	{0, 630, 130, 6, 9}
};//{type, posX, posY, depth, angle}

/* Test variables */
float rubber = 1;


public void setup(){
	/* canvas setup */
	size(700, 400); //width:700px, height:400px
	noCursor();

	/* BGM setup */
	minim = new Minim(this);
	bgm = minim.loadFile("horror_tale.wav");
	bgm.loop();

	/* Image setup */
	startImage = loadImage("start_screen.png");
	for(int i = 0; i < hillImages.length; i++){
		hillImages[i] = loadImage("background/hill_" + i + ".png");
	}
	for(int i = 0; i < skyImages.length; i++){
		skyImages[i] = loadImage("background/sky_" + i + ".png");
	}
	moonImage = loadImage("background/moon.png");
	for(int i = 0; i < monsterImages.length; i++){
		monsterImages[i] = loadImage("monster/monster_" + i + ".png");
	}
	for(int i = 0; i < candyImages.length; i++){
		candyImages[i] = loadImage("candy/candy_" + i + ".png");
	}
	for(int i = 0; i < buttonImages.length; i++){
		buttonImages[i][0] = loadImage("button/button_" + i + "_nor.png");
		buttonImages[i][1] = loadImage("button/button_" + i + "_sel.png");
	}
	targetImage[0] = loadImage("target_nor.png");
	targetImage[1] = loadImage("target_sel.png");

	/* Object setup */
	stage = new Stage();
	hill = new Hill();
	moon = new Moon();
	sky = new Sky();
	target = new Target();

	/* Button setup */
	startButton = new Button(buttonImages[0], 212, 64, 200, 200);
	aboutButton = new Button(buttonImages[1], 212, 64, 500, 200);

	/* Antialiasing setup */
	smooth(8);
}

public void draw(){
	target.move();
	target.state = false; //initialize for true statement

	drawStage(stage.currStage);

	target.draw();
}

public void mouseClicked(){
	onClickStage(stage.currStage);
}

public void keyPressed(){
	rubber += 0.01f;
}

public void keyReleased(){
	rubber = 1;
}
class Button{
	int[] prop = new int[4];
	boolean state = false;
	PImage[] images = new PImage[2];

	/* create Button() Object */
	Button(PImage[] tempImages, int tempWidth, int tempHeight, int tempX, int tempY){
		images = tempImages;
		prop[0] = tempWidth;
		prop[1] = tempHeight;
		prop[2] = tempX; //center position X of this button
		prop[3] = tempY; //center position Y of this button
	}

	/* display buttonImage */
	public void draw(){
		pushMatrix();

		imageMode(CENTER);
		if(state){
			image(images[1], prop[2], prop[3]);
		}else{
			image(images[0], prop[2], prop[3]);
		}

		popMatrix();
	}

	/* onMouse Event */
	public boolean onMouse(){
		if(mouseX >= prop[2] - prop[0] / 2 && mouseX <= prop[2] + prop[0] / 2 && mouseY >= prop[3] - prop[1] / 2 && mouseY <= prop[3] + prop[1] / 2){
			return true;
		}else{
			return false;
		}
	}

	/* onClick Event */
	public boolean onClick(){
		if(onMouse()){
			return true;
		}else{
			return false;
		}
	}
}

class Hill{
	/* create Hill() Object */
	Hill(){
	}

	/* display hillImages */
	public void draw(){
		pushMatrix();

		imageMode(CENTER);
		for(int i = 0; i < hillImages.length; i++){
			image(hillImages[i], width / 2, height / 2);
		}

		popMatrix();
	}
}
class Monster{
	int type;
	float health;
	float[] prop = new float[4]; //[X, Y, depth, angle]
	//\ud06c\uae30 \uc801\uc744 \ucc28\ub840...

	/* create Monster() Object */
	Monster(float tempX, float tempY, float tempDepth, float tempAngle){
		prop[0] = tempX;
		prop[1] = tempY;
		prop[2] = tempDepth;
		prop[3] = tempAngle;
	}

	/* display monsterImages[type] */
	public void draw(){
		pushMatrix();

		imageMode(CENTER);
		translate(prop[0], prop[1]);
		rotate(radians(prop[3]));
		image(monsterImages[type], 0, 0);

		popMatrix();
	}

	/* delete monsterImages[type] */
	public void remove(){

	}
}
class Moon{
	float[] moveState = new float[5];
	int moveMax = 4;

	/* create Moon() Object */
	Moon(){
		moveState[0] = width / 2; //initial position X
		moveState[1] = height / 2; //initial position Y
		moveState[2] = -1 + PApplet.parseInt(random(2)) * 2; //initial direction for moving
		moveState[3] = 0; //moving speed;
		moveState[4] = 0; //for limiting moving bound;
	}

	/* display moonImage */
	public void draw(){
		pushMatrix();

		imageMode(CENTER);
		image(moonImage, moveState[0], moveState[1]);

		popMatrix();
	}

	/* move moonImage with speed per frame */
	public void move(){
		float moveVar = moveState[2] * moveState[3];
		moveState[4] += moveVar;
		if(moveState[4] < moveMax && moveState[4] > -moveMax){
			moveState[0] += moveVar;
		}else{
			moveState[2] *= -1;
		}
	}
}

class Sky{
	float[][] moveState = new float[skyImages.length][5];
	int moveMax = 100;

	/* create Sky() Object */
	Sky(){
		for(int i = 0; i < skyImages.length; i++){
			moveState[i][0] = width / 2; //initial position X
			moveState[i][1] = height / 2; //initial position Y
			moveState[i][2] = -1 + PApplet.parseInt(random(2)) * 2; //initial direction for moving
			moveState[i][3] = random(0.1f, 2); //moving speed
			moveState[i][4] = 0; //for limiting moving bound
		}
	}

	/* display skyImages */
	public void draw(){
		pushMatrix();

		imageMode(CENTER);
		for(int i = 0; i < skyImages.length; i++){
			image(skyImages[i], moveState[i][0], moveState[i][1]);
		}

		popMatrix();
	}

	/* move skyImages with speed per frame */
	public void move(){
		for(int i = 1; i < skyImages.length; i++){
			float moveVar = moveState[i][2] * moveState[i][3];
			moveState[i][4] += moveVar;
			if(moveState[i][4] < moveMax && moveState[i][4] > -moveMax){
				moveState[i][0] += moveVar;
			}else{
				moveState[i][2] *= -1;
			}
		}
	}
}
class Stage{
	int currStage;
	int[] history = new int[1];

	/* create Stage() Object */
	Stage(){
		currStage = 0;
		history[0] = 0;
	}

	/* move to the X stage */
	public void move(int nextStage){
		currStage = nextStage;
		history = (int[]) append(history, nextStage);
	}

	/* back to the previous stage */
	public void back(){
		currStage = history[history.length - 2];
		history = shorten(history);
	}
}
class Target{
	boolean state = false;
	float[] prop = new float[4];
	float[] orig = new float[2];

	/* create Target() Object */
	Target(){
		prop[0] = 0;
		prop[1] = 0;
		orig[0] = prop[2] = targetImage[0].width;
		orig[1] = prop[3] = targetImage[0].height;
	}

	/* display targetImage */
	public void draw(){
		pushMatrix();

		imageMode(CENTER);
		if(state){
			image(targetImage[1], prop[0], prop[1], prop[2], prop[3]);
		}else{
			image(targetImage[0], prop[0], prop[1], prop[2], prop[3]);	
		}

		popMatrix();
	}

	/* move targetImage */
	public void move(){
		prop[0] = mouseX;
		prop[1] = mouseY;
	}

	public void resize(float m){
		prop[2] = orig[0] * m;
		prop[3] = orig[1] * m;
	}

	public void resize(float w, float h){
		prop[2] = w;
		prop[3] = h;
	}
}
/* Stage variables */
float stage_0_opacity = 0;
float stage_0_easing = 0.15f;


/* drawing code for each stage */
public void drawStage(int getStage){
	switch(getStage){
		case 0:
			target.resize(60, 60);

			background(0);
			tint(255, stage_0_opacity);

			stage_0_opacity += 10 * stage_0_easing;
			if(stage_0_opacity > 255){
				delay(2000);
				stage.move(1);
			}

			imageMode(CENTER);
			image(startImage, width / 2, height / 2);

			break;
		case 1:
			target.resize(60 / rubber, 60 / rubber);

			/* Sky Object */
			sky.move();
			sky.draw();

			/* Moon Object */
			//moon.move();
			moon.draw();

			/* Hill Object */
			hill.draw();

			/* Button Object */
			if(startButton.onMouse()){
				startButton.state = true;
				target.state = true;
			}else{
				startButton.state = false;
			}
			if(aboutButton.onMouse()){
				aboutButton.state = true;
				target.state = true;
			}else{
				aboutButton.state = false;
			}
			startButton.draw();
			aboutButton.draw();

			break;
		case 2:
			/* Sky Object */
			sky.move();
			sky.draw();

			/* Moon Object */
			//moon.move();
			moon.draw();

			/* Hill Object */
			hill.draw();

			break;
		case 3:
			background(255, 0, 0);

			break;
		default:
			background(0);

			break;
	}
}

/* click event for each stage */
public void onClickStage(int getStage){
	switch(getStage){
		case 0:
			break;
		case 1:
			if(startButton.onClick()){stage.move(2);}
			if(aboutButton.onClick()){stage.move(3);}
			break;
		case 2:
			stage.back();
			break;
		case 3:
			stage.back();
			break;
		default:
			stage.move(0);
			break;	
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "processing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
