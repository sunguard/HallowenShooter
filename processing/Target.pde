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
	void draw(){
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
	void move(){
		prop[0] = mouseX;
		prop[1] = mouseY;
	}

	void resize(float m){
		prop[2] = orig[0] * m;
		prop[3] = orig[1] * m;
	}

	void resize(float w, float h){
		prop[2] = w;
		prop[3] = h;
	}
}