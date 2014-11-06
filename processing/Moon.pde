class Moon{
	float[] moveState = new float[5];
	int moveMax = 4;

	/* create Moon() Object */
	Moon(){
		moveState[0] = width / 2; //initial position X
		moveState[1] = height / 2; //initial position Y
		moveState[2] = -1 + int(random(2)) * 2; //initial direction for moving
		moveState[3] = 0; //moving speed;
		moveState[4] = 0; //for limiting moving bound;
	}

	/* display moonImage */
	void draw(){
		pushMatrix();

		imageMode(CENTER);
		image(moonImage, moveState[0], moveState[1]);

		popMatrix();
	}

	/* move moonImage with speed per frame */
	void move(){
		float moveVar = moveState[2] * moveState[3];
		moveState[4] += moveVar;
		if(moveState[4] < moveMax && moveState[4] > -moveMax){
			moveState[0] += moveVar;
		}else{
			moveState[2] *= -1;
		}
	}
}