class Monster{
	int type;
	float health;
	float[] prop = new float[4]; //[X, Y, depth, angle]
	//크기 적을 차례...

	/* create Monster() Object */
	Monster(float tempType, float tempX, float tempY, float tempDepth, float tempAngle){
		type = int(tempType);
		prop[0] = tempX;
		prop[1] = tempY;
		prop[2] = tempDepth;
		prop[3] = tempAngle;
		health = 100;
	}

	/* display monsterImages[type] */
	void draw(){
		pushMatrix();

		imageMode(CENTER);
		translate(prop[0], prop[1]);
		rotate(radians(prop[3]));
		image(monsterImages[type], 0, 0);

		popMatrix();
	}

	/* delete monsterImages[type] */
	void remove(){

	}
}