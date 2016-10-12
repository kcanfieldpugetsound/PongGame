This Java game project was my Intro to Computer Science final project. The project is my version of the Atari game PONG.

features:
 • play Pong against another human OR the computer
 • change difficulty levels (adjust paddle size and ball speed)
 • customize paddle colors
 • option to play default computer beep when the ball collides with a paddle


How the CPU player works works:
	The CPU player sits idle until the ball crosses an invisible vertical line to the right of the net. When the ball is to the right of this line, the CPU player tracks the ball's vertical position and goes up or down to meet the ball. If the paddle and ball are too far apart, then the paddle won't be able to catch up with the ball. The reason I chose to not have the CPU player track the ball the entire time is that the CPU player would then be practically unbeatable. By adjusting the position of the invisible line, the CPU player's actions can be made more or less human.


LEGAL NOTICE: Pong is a registered trademark of Atari Interactive Inc. I do not claim copyrights or any legal ownership of the PONG name or trademark. This project was intended purely for academic and entertainment purposes and is not intended for sale.
