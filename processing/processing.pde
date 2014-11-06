/* Minim Library */
import ddf.minim.*;
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
Monster[] monsters;

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


void setup(){
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
	for(int i = 0; i < monsterMap.length; i++){
		monsters[i] = new Monster(monsterMap[i][0])
	}

	/* Button setup */
	startButton = new Button(buttonImages[0], 212, 64, 200, 200);
	aboutButton = new Button(buttonImages[1], 212, 64, 500, 200);

	/* Antialiasing setup */
	smooth(8);
}

void draw(){
	target.move();
	target.state = false; //initialize for true statement

	drawStage(stage.currStage);

	target.draw();
}

void mouseClicked(){
	onClickStage(stage.currStage);
}

void keyPressed(){
	rubber += 0.01;
}

void keyReleased(){
	rubber = 1;
}