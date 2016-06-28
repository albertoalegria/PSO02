import com.sun.javafx.geom.Vec2d;

/**
 * PSO02
 * Created by alberto from Instituto Tecnologico de Tuxtla Gutierrez ITTG
 * At Instituto de Investigaciones en Matematicas Aplicadas y en Sistemas IIMAS
 * Mexico, DF. 27/06/16, 10:17 AM
 */
public class Particle {
    private Vec2d mPosition;
    private Vec2d mVelocity;
    private Vec2d bestLocal;
    private Vec2d bestGlobal;

    private Vec2d target; //Just for test

    public Particle(double lowRangeX, double maxRangeX, double lowRangeY, double maxRangeY, Vec2d target) {
        this.target = target;

        this.mPosition = new Vec2d(Utilities.getRandomDouble(lowRangeX, maxRangeX), Utilities.getRandomDouble(lowRangeY, maxRangeY));

        this.mVelocity = new Vec2d(0.0, 0.0);

        bestLocal = new Vec2d();
        bestGlobal = new Vec2d();
    }

    public boolean isInRange(double x1, double x2, double y1, double y2) {
        return (mPosition.x >= x1 && mPosition.x <= x2) || (mPosition.y >= y1 && mPosition.y <= y2);
    }

    public void printParticleInfo() {
        System.out.println("Position:\t\t" + mPosition.toString());
        //System.out.println("Velocity: " + mVelocity.toString());
        System.out.println("Best Local:\t\t" + bestLocal.toString());
        System.out.println("Best Global:\t" + bestGlobal.toString() + "\n");
    }

    public void setPosition(Vec2d position) {
        this.mPosition = position;
    }

    public void setVelocity(Vec2d velocity) {
        this.mVelocity = velocity;
    }

    public void setBestLocal(Vec2d bestLocal) {
        this.bestLocal = bestLocal;
    }

    public void setBestGlobal(Vec2d bestGlobal) {
        this.bestGlobal = bestGlobal;
    }

    public double getPositionX() {
        return this.mPosition.x;
    }

    public double getPositionY() {
        return this.mPosition.y;
    }

    public double getVelocityX() {
        return this.mVelocity.x;
    }

    public double getVelocityY() {
        return this.mVelocity.y;
    }

    public Vec2d getPosition() {
        return this.mPosition;
    }

    public Vec2d getVelocity() {
        return this.mVelocity;
    }

    public Vec2d getBestLocal() {
        return bestLocal;
    }

    public Vec2d getBestGlobal() {
        return bestGlobal;
    }
}
