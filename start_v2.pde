import java.util.Iterator;

final int PARTICLE_START_FORCE = 100;
final int PARTILE_MAX_VEL = 20; ///7;//4;
final int PARTICLE_MAX_ACC = 10; // Max particle acceleration
final int SPAWN_COUNT = 2; // Number of particles to spawn at once
final float LIFESPAN_DECREMENT = 2.0;
final int START_SIZE = 30;//100;//175;
final float SHRINK_RATE = 1;//2;//5;
final int MAX_PARTICLES = 100;
final int SPAWN_DELAY = 50; //ms

int[] x1 = {320, 335, 350, 360, 372, 378, 380, 378, 372, 360, 350, 335, 320, 305, 290, 280, 272, 262, 260, 262, 268, 280, 290, 305};
int[] y1 = {180, 182, 190, 200, 215, 228, 240, 252, 270, 280, 290, 298, 300, 298, 290, 280, 270, 256, 240, 228, 215, 200, 190, 182};

boolean displayColour = true;
int time = millis();

ParticleSystem system = new ParticleSystem();
ColourGenerator colour = new ColourGenerator();

void setup()
{
  size(640, 480);
  background(0);
  frameRate(20);
}

void draw() 
{
  // Update our particle system each frame
  system.update();
}

void mousePressed()
{
  print("Here");
  background(0);
  if (millis() > time + SPAWN_DELAY) {
    for(int i = 0; i < 24; i++) {
      system.addParticle(new PVector(x1[i], y1[i]));
      time = millis();
    }
  }
}

void keyPressed() 
{
  
  switch (key) {
    case 'r':
      background(0);
      break;
    default:  
      displayColour = !displayColour;  
  }
}