package Autobot;
import robocode.*;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Trabalho de SII/NP2 - UNIP 2012
 um robô feito por:
 - Raul de Caires Ferreira
 */
 
public class Sentufumo extends AdvancedRobot
{	
    private static final double GUN_TURNING_RATE = 40.0 / 180.0 * Math.PI;
    private static final double START_HIT_TIME = 20; 
    private static final double TOL = 0.5;           // tolerancia 
    private static final double MAX_ITERATIONS = 10; // maximo de iterações por turno
    double distancia;
	
	public void run() {
    	setColors(Color.black, Color.red, Color.white, Color.black, Color.red); //mudar as cores do robo (corpo, canhao, radar, bala, radar)
		setAdjustGunForRobotTurn(true); //destrava o canhão do corpo do robô
		while(true) { //variável de loop, para evitar que o robo pare de executar os comandos.
				turnGunRight(360); // o robo gira o canhao em 360º para a direita
		}
	}
	public void onScannedRobot(ScannedRobotEvent Inimigo) {
		
		distancia = Inimigo.getDistance(); // analisa distancia do inimigo e armazena na variável "distancia"
		double angulo = Inimigo.getBearing(); // analisa o angulo do inimigo com relação ao nosso robo
		double anguloRad = Inimigo.getBearingRadians(); // analisa o angulo (em radianos) do inimigo com relação ao nosso robo
		double DirIni = Inimigo.getHeadingRadians() - this.getHeadingRadians(); // analisa a direção do inimigo
		double VelIni = Inimigo.getVelocity(); // analisa a velocidade do inimigo
		double TempoEstimado = amountToTurn(anguloRad) / GUN_TURNING_RATE;
        double hit_time = START_HIT_TIME; 
        double actual_time = -1.0; 
        double anguloTiro;
        int count = 0;
		double potenciatiro = 1.0;
			if (distancia < 100) { // se a distancia do inimigo for menor que 100, utiliza tiro de potencia 3
      			potenciatiro = 3.0;
				setBulletColor(Color.yellow);				
				setTurnRight(normalRelativeAngleDegrees(90 - angulo)); // gira o robo numa direção perpendicular à direção do inimigo
				setBack(60 - distancia);
					if(pertoParede()) { // caso esteja próximo a parede, fazer movimento contrário para nao colidir com a mesma
							setBack(-1*(60 - distancia));
					} else {
							setBack(60 - distancia);
					}
			}
			else if (distancia < 250) { // se a distancia do inimigo estiver entre 100 e 250, utiliza tiro de potencia 2
				potenciatiro = 2.0;
				setBulletColor(Color.green);
				setAhead(distancia - 99); // aproxima do inimigo a uma distancia de 99
				setTurnRight(angulo);
			} else { 
				setBulletColor(Color.white);
				setAhead(distancia -249); // aproxima-se do inimigo a uma distancia de 249
				setTurnRight(angulo);
			}
			if (Inimigo.getEnergy() < 16.0) { // se o inimigo tiver menos de 16 de energia, utilizará um tiro com potencia suficiente para matá-lo. Evita desperdício de energia.
      				potenciatiro = Inimigo.getEnergy() / 4;
					setBulletColor(Color.red);	
			}

		double bulletSpeed = getBulletSpeed(potenciatiro); // chama o método que calcula a velocidade do tiro
                do { 
                        if (count != 0) 
                                hit_time = actual_time;

                        count++;

                        Point enemy = new Point(distancia * Math.sin(anguloRad), distancia * Math.cos(anguloRad));  // Localizaçao do inimigo

                        // analisa o movimento inimigo
                        enemy.x += hit_time * VelIni * Math.sin(DirIni); 
                        enemy.y += hit_time * VelIni * Math.cos(DirIni);

                        enemy.y -= TempoEstimado * this.getVelocity();

                        double fire_length = enemy.distanceFromOrigin(); 
                        double fire_time = fire_length / bulletSpeed; 
                        anguloTiro = Math.atan(enemy.x / enemy.y); 
                        if (enemy.y < 0) 
                                anguloTiro += Math.PI; 
                        TempoEstimado = amountToTurn(anguloTiro) / GUN_TURNING_RATE; 
                        actual_time = fire_time + TempoEstimado; 
                } 
                while (Math.abs(actual_time - hit_time) > TOL && count < MAX_ITERATIONS);
		turnGunRightRadians(amountToTurn(anguloTiro)); // gira o canhao na posição exata de tiro preciso
		setFire(potenciatiro); // define a potencia do tiro
		pontaria(angulo); // chama o método pontaria, passando o valor de "angulo"... foi colocado esse metodo para reajustar a posiçao do radar no inimigo.. mantendo o foco nele
		}

    private double amountToTurn(double heading) { 
            double res = heading + getHeadingRadians() - getGunHeadingRadians(); 
            while (res > Math.PI) res -= 2 * Math.PI; 
            while (res < -Math.PI) res += 2 * Math.PI; 
            return res; 
    }
    private double getBulletSpeed(double potenciatiro) { // calcula a velocidade do tiro de acordo com a sua potência.
            return 20.0 - 3.0 * potenciatiro; 
    }
	public void pontaria(double angulo) { // calcula o angulo que o canhão precisa girar para manter o alvo na mira
		double Ang=getHeading()+angulo-getGunHeading(); // o angulo e calculado através da soma do angulo absoluto do nosso robo com o 
		if (!(Ang > -180 && Ang <= 180)) {				// angulo do inimigo em relação ao nosso, subtraido do angulo no qual se encontra o canhao do nosso robo
			while (Ang <= -180) { 
				Ang += 360;
			}
			while (Ang > 180) {
				Ang -= 360;
			}
		}
	setTurnGunRight(Ang); // gira o canhao com base no valor de Ang
	} 
	public boolean pertoParede() { // calcula se o robo se encontra próximo da parede
		return (getX() < 10 || getX() > getBattleFieldWidth() - 10 || // se a posição x ou y estiverem a menos de 10 de distancia das extremidades, retorna True
				getY() < 10 || getY() > getBattleFieldHeight() - 10);
	}

}
class Point {

        public double x, y;

        public Point(double x, double y) { 
                this.x = x; 
                this.y = y; 
        }

        public double distanceFromOrigin() { //calcula a distância com relação a origem da arena
                return Math.sqrt((x * x) + (y * y)); //teorema de pitágoras
        } 
}						