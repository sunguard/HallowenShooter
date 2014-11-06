class Sky{
	float[][] moveState = new float[skyImages.length][5];
	int moveMax = 100;

	/* create Sky() Object */
	Sky(){
		for(int i = 0; i < skyImages.length; i++){
			moveState[i][0] = width / 2; //initial position X
			moveState[i][1] = height / 2; //initial position Y
			moveState[i][2] = -1 + int(random(2)) * 2; //initial direction for moving
			moveState[i][3] = random(0.1, 2); //moving speed
			moveState[i][4] = 0; //for limiting moving bound
		}
	}

	/* display skyImages */
	void draw(){
		pushMatrix();

		imageMode(CENTER);
		for(int i = 0; i < skyImages.length; i++){
			image(skyImages[i], moveState[i][0], moveState[i][1]);
		}

		popMatrix();
	}

	/* move skyImages with speed per frame */
	void move(){
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