import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Iterator; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class start_v2 extends PApplet {



final int PARTICLE_START_FORCE = 100;
final int PARTILE_MAX_VEL = 20; ///7;//4;
final int PARTICLE_MAX_ACC = 10; // Max particle acceleration
final int SPAWN_COUNT = 2; // Number of particles to spawn at once
final float LIFESPAN_DECREMENT = 2.0f;
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

public void setup()
{
  
  background(0);
  frameRate(20);
}

public void draw() 
{
  // Update our particle system each frame
  system.update();
}

public void mousePressed()
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

public void keyPressed() 
{
  
  switch (key) {
    case 'r':
      background(0);
      break;
    default:  
      displayColour = !displayColour;  
  }
}
class ColourGenerator
{
  final static float MIN_SPEED = 0.2f;
  final static float MAX_SPEED = 0.7f;
  float R, G, B;
  float Rspeed, Gspeed, Bspeed;
  
  ColourGenerator()
  {
    init();  
  }
  
  public void init()
  {
    // Starting colour
    R = random(255);
    G = random(255);
    B = random(255);
    
    // Starting transition speed
    Rspeed = (random(1) > 0.5f ? 1 : -1) * random(MIN_SPEED, MAX_SPEED);
    Gspeed = (random(1) > 0.5f ? 1 : -1) * random(MIN_SPEED, MAX_SPEED);
    Bspeed = (random(1) > 0.5f ? 1 : -1) * random(MIN_SPEED, MAX_SPEED);
  }
  
  public void update()
  {
    // Use transition to alter original colour (Keep within RGB bounds)
    Rspeed = ((R += Rspeed) > 255 || (R < 0)) ? -Rspeed : Rspeed;
    Gspeed = ((G += Gspeed) > 255 || (G < 0)) ? -Gspeed : Gspeed;
    Bspeed = ((B += Bspeed) > 255 || (B < 0)) ? -Bspeed : Bspeed;
  }
  
}
class Particle
{
  PVector loc;
  PVector vel;
  PVector acc;

  
  int size = START_SIZE;
  float angle;
  float lifespan;

  //ColourGenerator colour = new ColourGenerator();
  
  Particle(PVector loc2) 
  {
    loc = new PVector(loc2.x, loc2.y);
    vel = new PVector(0, 0);
    acc = new PVector(0, 0);
    lifespan = 255.0f;
  }
  
  public void update()
  {
    // Move in random direction with random speed
    angle += random(0, TWO_PI);
    float magnitude = random(0, PARTICLE_START_FORCE); //3
    
    // Work out force 
    acc.x += cos(angle) * magnitude;
    acc.y += sin(angle) * magnitude;
    
    // limit result
    acc.limit(PARTICLE_MAX_ACC);
    
    // Add to current velocity
    vel.add(acc);
    vel.limit(PARTILE_MAX_VEL);
    
    // Appy result to current location
    loc.add(vel);
    
    // Wrap around screen
    // if (loc.x > width)
    //   loc.x -= width;
    //  if (loc.x < 0)
    //    loc.x += width;
    //  if(loc.y > height)
    //    loc.y -= height;
    //  if(loc.y < 0)
    //    loc.y += height;
    lifespan -= 2.0f;
    if(mousePressed != true) {
        size -= SHRINK_RATE;
    }

    if(lifespan <= 0) {
        background(0);
    }
    
  }
  
  public void display() 
  {
     if (displayColour) {
       //colour = new ColourGenerator();
         colour.update();
        fill(colour.R, colour.G, colour.B, lifespan);
        stroke(colour.R, colour.G, colour.B, lifespan);
     } else {
         fill(255);
     }
     ellipse(loc.x, loc.y, size, size);
  }
  
  public boolean isDead()
  {
    if (size < 0) {
      return true;
    } else {
      return false;
    }
  }
}
class ParticleSystem
{
  ArrayList<Particle> particles = new ArrayList<Particle>();
  int count = 0;
  
  ParticleSystem() { }
    
  public void addParticle(PVector loc)
  {
    count++;
    if (particles.size() + SPAWN_COUNT < MAX_PARTICLES) {
      // for (int i = 0; i < SPAWN_COUNT; i++) {
        particles.add(new Particle(loc));
      // }
    }
  }
  
  public void update()
  {
    // Use an iterator to loop through active particles
    Iterator<Particle> i = particles.iterator();
    
    while(i.hasNext()) {
      // Get next particle
      Particle p = i.next();
      
      // update position and lifespan
      p.update();
      // Remove particle if dead
      if (p.isDead()) {
        i.remove();
        count--;
      } else {
        p.display();
      }
    }
  }
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "start_v2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
