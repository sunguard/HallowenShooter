class Hill{
	/* create Hill() Object */
	Hill(){
	}

	/* display hillImages */
	void draw(){
		pushMatrix();

		imageMode(CENTER);
		for(int i = 0; i < hillImages.length; i++){
			image(hillImages[i], width / 2, height / 2);
		}

		popMatrix();
	}
}