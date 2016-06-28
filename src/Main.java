import com.sun.javafx.geom.Vec2d;

/**
 * PSO02
 * Created by alberto from Instituto Tecnologico de Tuxtla Gutierrez ITTG
 * At Instituto de Investigaciones en Matematicas Aplicadas y en Sistemas IIMAS
 * Mexico, DF. 27/06/16, 12:02 PM
 */
public class Main {
    public static void main(String[] args) {
        //System.out.println(Utilities.getRandomDouble(1.5, 1.9));

        PSOController psoController = new PSOController(10.0, 10.0, new Vec2d(0.0, 0.0));
        psoController.generateParticles();
        int i = 0;
        while( i < 100 ) {
            psoController.searchNeighbors();
            psoController.searchBestGlobal();
            psoController.updateVelocity();
            psoController.updatePosition();
            psoController.exportParticleToExternalText("gen" + i);
            i++;
        }
        psoController.printParticleInfo();

        /*Particle particle = new Particle(-5, 5, -5, 5, new Vec2d(0,0));
        System.out.println(particle.isInRange(-1, 1, -1, 1));
        particle.printParticleInfo();*/
    }
}
