import com.sun.javafx.geom.Vec2d;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;
import java.util.stream.Collectors;



/**
 * PSO02
 * Created by alberto from Instituto Tecnologico de Tuxtla Gutierrez ITTG
 * At Instituto de Investigaciones en Matematicas Aplicadas y en Sistemas IIMAS
 * Mexico, DF. 27/06/16, 11:27 AM
 */
public class PSOController {

    private Vector<Particle> particleSwarm;


    private Vec2d target; //Temporal

    private double dimX;
    private double dimY;

    public PSOController(double x, double y, Vec2d target) {
        this.dimX = x;
        this.dimY = y;

        particleSwarm = new Vector<>();

        this.target = target;
    }

    public void generateParticles() {
        for (int i = 0; i < Constants.SWARM_SIZE; i++) {
            Particle newParticle = new Particle(-dimX, dimX, -dimY, dimY, target);
            particleSwarm.add(newParticle);
        }
    }

    public void searchBestGlobal() {
        Vec2d best = new Vec2d();
        double bestFitness = 0.0;

        for (Particle particle : particleSwarm) {
            double distance = particle.getPosition().distance(target);
            if (distance == 0.0) {
                best = particle.getPosition();
            } else {
                double fitness = 1 / distance;

                if (fitness >= bestFitness) {
                    bestFitness = fitness;
                    best = particle.getPosition();
                }
            }
        }

        for (Particle particle : particleSwarm) {
            particle.setBestGlobal(best);
        }
    }

    public void searchNeighbors() {
        for (Particle particle : particleSwarm) {
            Vector<Particle> neighbors = new Vector<>();
            double x = particle.getPositionX();
            double y = particle.getPositionY();

            neighbors.addAll(particleSwarm.stream().filter(particle1 -> particle1.
                    isInRange(x - Constants.GAP, x + Constants.GAP, y - Constants.GAP, y + Constants.GAP)).
                    collect(Collectors.toList()));
            particle.setBestLocal(getBestLocal(neighbors));
        }
    }

    private Vec2d getBestLocal (Vector<Particle> neighbors) {
        double maxFitness = 0.0;
        Vec2d best = new Vec2d();

        for (Particle particle1 : neighbors) {
            double distance = particle1.getPosition().distance(target);
            if (distance == 0.0) {
                best = particle1.getPosition();
            } else {
                double fitness = 1 / distance;

                if (fitness >= maxFitness) {
                    maxFitness = fitness;
                    best = particle1.getPosition();
                }
            }
        }
        return best;
    }

    public void updateVelocity() {
        //TODO update velocity

        for (Particle particle : particleSwarm) {
            System.out.println("Actual Velocity: " + particle.getVelocity().toString());

            double globalX = particle.getBestGlobal().x;
            double globalY = particle.getBestGlobal().y;

            double localX = particle.getBestLocal().x;
            double localY = particle.getBestLocal().y;

            double velX = particle.getVelocityX();
            double velY = particle.getVelocityY();

            double velXW = velX * Constants.W;
            double velYW = velY * Constants.W;

            double C1 = Utilities.getRandomDouble(Constants.MIN_C, Constants.MAX_C);
            double C2 = Utilities.getRandomDouble(Constants.MIN_C, Constants.MAX_C);

            System.out.println("\nGlobal x = " + globalX + ", Global y = " + globalY);
            System.out.println("Local x = " + localX + ", Local y = " + localY);
            System.out.println("Vel x = " + velXW + ", Vel y = " + velYW);
            System.out.println("Position x = " + particle.getPositionX() + ", Position y = " + particle.getPositionY());
            System.out.println("C1 = " + C1 + ", C2 = " + C2 + "\n");

            double gPosX = globalX - particle.getPositionX();
            double gPosY = globalY - particle.getPositionY();

            System.out.println("GPosX = " + gPosX + ", GPosY = " + gPosY);

            double lPosX = localX - particle.getPositionX();
            double lPosY = localY - particle.getPositionY();

            System.out.println("LPosX = " + lPosX + ", LPosY = " + lPosY);

            double c1Posx = C1 * gPosX;
            double c2Posx = C2 * lPosX;

            System.out.println("C1*gPosX = " + c1Posx + ", C2*lPosX = " + c2Posx);

            double c1Posy = C1 * gPosY;
            double c2Posy = C2 * lPosY;

            System.out.println("C1*gPosY = " + c1Posy + ", C2*lPosY = " + c2Posy);

            double newVelX = Utilities.round(velXW + c1Posx + c2Posx, Constants.ROUND);

            double newVelY = Utilities.round(velYW + c1Posy + c2Posy, Constants.ROUND);

            System.out.println("New x = " + newVelX + ", New y = " + newVelY);

            particle.setVelocity(new Vec2d(newVelX, newVelY));
            System.out.println("New velocity: " + particle.getVelocity().toString() + "\n");
        }
    }

    public void updatePosition() {
        //TODO update position

        for (Particle particle : particleSwarm) {
            System.out.println("Actual Position: " + particle.getPosition().toString());
            double newPosX = Utilities.round(particle.getPositionX() + particle.getVelocityX(), Constants.ROUND);
            double newPosY = Utilities.round(particle.getPositionY() + particle.getVelocityY(), Constants.ROUND);

            particle.setPosition(new Vec2d(newPosX, newPosY));

            System.out.println("New Position: " + particle.getPosition().toString() + "\n");
        }

    }

    public void printParticleInfo() {
        particleSwarm.forEach(Particle::printParticleInfo);
    }

    public void exportParticleToExternalText(String genInfo) {
        //String[] particleInfo = new String[]{};
        ArrayList<String> particleInfo = new ArrayList();
        for (Particle tParticle : particleSwarm) {
            String info = tParticle.getPositionX() + " " + tParticle.getPositionY();
            particleInfo.add(info);
        }

        String fpath = "/Users/alberto/PSOTest/data_" + genInfo +".txt";

        Path filePath = Paths.get(fpath);
        try {
            Files.write(filePath, particleInfo, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}