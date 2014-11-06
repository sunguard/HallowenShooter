/* Stage variables */
float stage_0_opacity = 0;
float stage_0_easing = 0.15;


/* drawing code for each stage */
void drawStage(int getStage){
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
void onClickStage(int getStage){
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